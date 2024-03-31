package wku.smartplant.dto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import wku.smartplant.domain.Member;
import wku.smartplant.domain.MemberType;

@Getter @Setter
public class MemberJoinRequest {

    @NotBlank(message = "회원 이름은 필수 입력값입니다.")
    private String username;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "Example@xxx.com 같은 이메일 형식이어야합니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;

    private MemberType memberType;

    public MemberJoinRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.memberType = MemberType.LOCAL;
    }

    public MemberJoinRequest(String username, String email, String password, MemberType memberType) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.memberType = memberType;
    }

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .email(email)
                .password(password)
                .memberType(memberType)
                .build();
    }
}
