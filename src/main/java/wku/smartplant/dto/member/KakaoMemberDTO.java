package wku.smartplant.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoMemberDTO {
    private Long id;
    private String username;
    private String email;

    @Builder
    public KakaoMemberDTO(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
