package wku.smartplant.dto.member;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberLoginResponse {
    private String email;
    private String username;
    private String token;
}
