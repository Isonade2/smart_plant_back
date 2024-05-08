package wku.smartplant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wku.smartplant.dto.member.*;
import wku.smartplant.jwt.SecurityUtil;
import wku.smartplant.domain.Member;
import wku.smartplant.dto.ResponseDTO;
import wku.smartplant.service.MemberService;

import java.io.IOException;

import static org.springframework.http.HttpStatus.*;
import static wku.smartplant.dto.ResponseEntityBuilder.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Value("${client.url}")
    private String clientId;

    @PostMapping("/join")
    @Operation(summary = "이메일 회원가입",
            description = "이메일 비밀번호로 회원가입" ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "회원가입 성공 시 '가입 성공 xxx@xxx.com 메일함을 확인하여 계정을 활성화시켜주세요.' 라는 메세지 리턴"),
                    @ApiResponse(responseCode = "400", description = "정규식 불일치. 각 불일치한 항목에 관해 \"키\" : \"메세지\" 형식으로 표시됨 <br>content : {<br>\"password\" :<br>\"email\" :<br>\"username:<br>}"),
            })
    public ResponseEntity<ResponseDTO<?>> join(@Valid @RequestBody MemberJoinRequest memberJoinRequest) {
        Member joinedMember = memberService.joinMember(memberJoinRequest);
        return build("가입 성공 " + joinedMember.getEmail() + " 메일함을 확인하여 계정을 활성화시켜주세요.", OK);
    }

    @PostMapping("/login")
    @Operation(summary = "이메일 로그인",
            description = "이메일 비밀번호로 로그인<br>요청 파라미터 memberPlatform은 보내지 않아도됨" ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "이메일 또는 패스워드 불일치 시 메세지만 리턴", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
                    @ApiResponse(responseCode = "406", description = "이메일 활성화를 하지 않았을 경우 메세지만 리턴", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
            })
    public ResponseEntity<ResponseDTO<MemberLoginResponse>> login(@Valid @RequestBody MemberLoginRequest memberLoginRequest) {
        MemberLoginResponse memberLoginResponse = memberService.loginMember(memberLoginRequest);

        //return ResponseEntity.status(OK).body(ResponseDTO.<MemberLoginResponse>builder().message("로그인 성공").content(memberLoginResponse).build());
        return build("로그인 성공", OK, memberLoginResponse);
    }

    @GetMapping("/verify")
    @Operation(summary = "이메일 활성화",
            description = "이메일 활성화 링크를 클릭하면 서버측 /verify 로 이동되고 유저 활성화 후 " +
                    "'프론트/login?activate=true' 로 리다이렉트 시킴. 프론트에서는 activate param을 보고 활성화가 완료됐다는 알림을 로그인 화면에 표시해야됨 ",
            responses = {
                    @ApiResponse(responseCode = "302", description = "'프론트주소/login?activate=true' 성공 시 리턴")
            })
    public void callback(@RequestParam("code") String uuid, HttpServletResponse response) throws IOException {
        try {
            memberService.verifyAndActivateMember(uuid);
        } catch (IllegalStateException | EntityNotFoundException ex) {
            response.sendRedirect(clientId + "/needActivate?expired=true");
        }

        response.sendRedirect(clientId + "/login?activate=true");

    }

    @PostMapping("/activate/resend") //활성화 메일 재요청
    @Operation(summary = "가입 시 필요한 활성화 메일 재전송 요청",
            description = "활성화 메일이 시간이 지나 만료되었거나 메일 도착이 안했을 경우 메일을 재전송함",
            responses = {
                    @ApiResponse(responseCode = "400", description = "가입 이력이 없을 경우, 정규식 에러 시 content : {email : '메세지'} ")
            })
    public ResponseEntity<ResponseDTO<?>> activateReSend(@Valid @RequestBody MemberEmailRequest memberEmailRequest) {
        String email = memberEmailRequest.getEmail();
        memberService.resendActivateMail(email);

        return build(email + " 메일함을 확인하여 계정을 활성화 시켜주세요.", CREATED);

    }
     
    @PostMapping("/password/reset") //비밀번호 초기화 요청
    @Operation(summary = "비밀번호 찾기(변경) 요청",
            description = "요청 이메일로 비밀번호 변경 메일을 보냄",
            responses = {
                    @ApiResponse(responseCode = "201", description = "메일 전송 성공" +
                            "<br>메일에 있는 주소 클릭 시 '클라리언트/password/reset?code={uuid}&email={email}' 으로 리다이렉트 되므로 " +
                            "<br>클라이언트에서는 code, email 파라미터를 저장하여 /password/change 요청 할 때 담아서 보내야 함"),
                    @ApiResponse(responseCode = "400", description = "가입 이력이 없을 경우, 가입은 했는데 활성화를 안했을 경우" +
                            "<br> 정규식 에러 시에는 다음 내용을 content에 포함 content : {email : '메세지'} ")
            })
    public ResponseEntity<ResponseDTO<?>> passwordReset(@Valid @RequestBody MemberEmailRequest memberEmailRequest) {
        String email = memberEmailRequest.getEmail();
        memberService.passwordResetRequest(email);

        return build(email + " 메일함을 확인하여 비밀번호 초기화 진행해주세요.", CREATED);

    }

    @PostMapping("/password/change") //바꿀 비밀번호 받기
    @Operation(summary = "새 비밀번호로 변경",
            description = "요청 시 비밀번호, 비밀번호 확인, email, uuid 필요" +
                    "<br>email과 uuid는 /password/reset 문서 확인",
            responses = {
                    @ApiResponse(responseCode = "200", description = "패스워드 변경이 완료되었습니다. 메세지 리턴"),
                    @ApiResponse(responseCode = "400", description = "uuid와 email이 일치하지 않거나, 비밀번호와 비밀번호 확인란이 일치하지 않을 경우, 만료되었을 경우")
            })
    public ResponseEntity<ResponseDTO<?>> passwordReset(@Valid @RequestBody MemberPasswordChangeRequest memberPasswordChangeRequest) {
        String password = memberPasswordChangeRequest.getPassword();
        String passwordConfirm = memberPasswordChangeRequest.getPasswordConfirm();
        String email = memberPasswordChangeRequest.getEmail();
        String uuid = memberPasswordChangeRequest.getUuid();

        if (!password.equals(passwordConfirm)) {
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인란이 일치하지 않습니다.");
        }

        memberService.changePassword(email, password, uuid);

        return build("패스워드 변경이 완료되었습니다.", OK);

    }

    @GetMapping("/{memberId}")
    public Member findMember(@PathVariable Long memberId) {
        Member findMember = memberService.findMemberById(memberId);
        return findMember;
    }
}
