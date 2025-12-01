package co.kr.grouppurchace.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponse {
    private final String tokenType;
    private final String accessToken;
    private final String refreshToken;
}
