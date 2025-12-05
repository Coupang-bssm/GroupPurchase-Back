package co.kr.grouppurchace.domain.user.service;

import co.kr.grouppurchace.domain.user.dto.LoginRequest;
import co.kr.grouppurchace.domain.user.dto.SignupRequest;
import co.kr.grouppurchace.domain.user.dto.TokenResponse;
import co.kr.grouppurchace.domain.user.dto.UserResponse;
import co.kr.grouppurchace.domain.user.entity.User;
import co.kr.grouppurchace.domain.user.repository.UserRepository;
import co.kr.grouppurchace.global.security.JwtTokenProvider;
import co.kr.grouppurchace.global.exception.ConflictException;
import co.kr.grouppurchace.global.exception.EntityNotFoundException;
import co.kr.grouppurchace.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public UserResponse signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ConflictException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }

        String role = request.getRole();
        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }

        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        userRepository.save(user);
        return new UserResponse(user);
    }

    public TokenResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtTokenProvider.generateAccessToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);

        return TokenResponse.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public UserResponse me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new EntityNotFoundException(ErrorCode.USER_NOT_AUTHENTICATED);
        }
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
        return new UserResponse(user);
    }
}
