package wku.smartplant.dto.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import wku.smartplant.domain.Member;
import wku.smartplant.domain.MemberPlatform;

@Getter @Setter
@Builder
public class MemberJoinRequest {

    @NotBlank(message = "회원 이름은 필수 입력값입니다.")
    private String username;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "Example@xxx.com 같은 이메일 형식이어야합니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;

    @JsonIgnore
    private MemberPlatform memberPlatform = MemberPlatform.LOCAL; //기본은 로컬, 카카오 가입시에만 생성자로 지정
    @JsonIgnore
    private boolean activate = false;

//    public MemberJoinRequest(String username, String email, String password) {
//        this.username = username;
//        this.email = email;
//        this.password = password;
//    }
//
//    public MemberJoinRequest(String username, String email, String password, MemberPlatform memberPlatform, boolean activate) {
//        this.username = username;
//        this.email = email;
//        this.password = password;
//        this.memberPlatform = memberPlatform;
//        this.activate = activate;
//    }

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .email(email)
                .password(password)
                .memberPlatform(memberPlatform)
                .activate(activate)
                .build();
    }
}
