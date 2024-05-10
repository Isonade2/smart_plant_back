package wku.smartplant.dto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class MemberPasswordChangeRequest {

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*\\W)[A-Za-z\\d\\W]{8,}$", message = "최소 8자리 이상이면서, 알파벳, 숫자, 특수문자를 모두 포함해야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인란은 필수 입력값입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*\\W)[A-Za-z\\d\\W]{8,}$", message = "최소 8자리 이상이면서, 알파벳, 숫자, 특수문자를 모두 포함해야 합니다.")
    private String passwordConfirm;

    @Email
    private String email;

    @NotBlank(message = "유효 코드가 안왔습니다.")
    private String uuid;
}
