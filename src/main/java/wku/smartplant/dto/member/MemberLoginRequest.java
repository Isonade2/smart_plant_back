package wku.smartplant.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
public class MemberLoginRequest {
    private String email;
    private String password;

    @Builder
    public MemberLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
