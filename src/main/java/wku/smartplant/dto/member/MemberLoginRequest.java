package wku.smartplant.dto.member;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wku.smartplant.domain.MemberPlatform;

@Getter @Setter
public class MemberLoginRequest {

    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private MemberPlatform memberPlatform;

    @JsonCreator
    public MemberLoginRequest(@JsonProperty String email, @JsonProperty String password) {
        this.email = email;
        this.password = password;
        this.memberPlatform = MemberPlatform.LOCAL;
    }

    @Builder
    public MemberLoginRequest(String email, String password, MemberPlatform memberPlatform) {
        this.email = email;
        this.password = password;
        this.memberPlatform = memberPlatform;
    }
}
