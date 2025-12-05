package co.kr.grouppurchace.domain.user.dto;

import co.kr.grouppurchace.domain.user.entity.User;
import lombok.Getter;

@Getter
public class UserResponse {
    private final Long id;
    private final String email;
    private final String username;
    private final String role;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getDisplayName();
        this.role = user.getRole();
    }
}
