package wku.smartplant.dto.member;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import wku.smartplant.domain.Member;
import wku.smartplant.domain.MemberPlatform;

@Getter @Setter
@ToString
public class MemberJoinRequest {

    @NotBlank(message = "회원 이름은 필수 입력값입니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣]{2,8}$", message = "2글자 이상 8글자 이하의 영어 또는 한글만 사용 가능합니다.")
    private String username;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "Example@xxx.com 같은 이메일 형식이어야합니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "최소 8자리 이상이면서, 알파벳과 숫자를 모두 포함해야 합니다.")
    private String password;

    @JsonIgnore
    private MemberPlatform memberPlatform;

    @JsonIgnore
    private boolean activate;

    @JsonCreator
    public MemberJoinRequest(@JsonProperty String username,
                             @JsonProperty String email,
                             @JsonProperty String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.memberPlatform = MemberPlatform.LOCAL;
        this.activate = false;
    }

    @Builder
    public MemberJoinRequest(String username, String email, String password, MemberPlatform memberPlatform, boolean activate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.memberPlatform = memberPlatform;
        this.activate = activate;
    }

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
