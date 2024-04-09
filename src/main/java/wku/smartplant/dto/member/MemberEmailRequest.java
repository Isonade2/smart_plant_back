package wku.smartplant.dto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class MemberEmailRequest {

    @NotBlank(message = "Email은 비워둘 수 없습니다.")
    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    private String email;
}
