package wku.smartplant.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import wku.smartplant.domain.Member;

@Getter @Setter
public class MemberJoinDto {

    @NotBlank(message = "회원 이름은 필수 입력값입니다.")
    private String username;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "Example@xxx.com 같은 이메일 형식이어야합니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
    }
}
