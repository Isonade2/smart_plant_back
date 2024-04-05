package wku.smartplant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    public ResponseEntity<ResponseDTO<?>> join(@Valid @RequestBody MemberJoinRequest memberJoinRequest) {
        Member joinedMember = memberService.joinMember(memberJoinRequest);
        return build("가입 성공 " + joinedMember.getEmail() + " 메일함을 확인하여 계정을 활성화시켜주세요.", OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<?>> login(@Valid @RequestBody MemberLoginRequest memberLoginRequest) {
        MemberLoginResponse memberLoginResponse = memberService.loginMember(memberLoginRequest);

        if (memberLoginResponse.getToken() == null) {
            return build("이메일 또는 패스워드가 불일치 합니다.", BAD_REQUEST);
        }
        return build("로그인 성공.", OK, memberLoginResponse);
    }

    @Operation(summary = "이메일 활성화",
            description = "이메일 활성화 링크를 클릭하면 서버측 /verify 로 이동되고 유저 활성화 후 " +
                    "'프론트/login?activate=true' 로 리다이렉트 시킴. 프론트에서는 activate param을 보고 활성화가 완료됐다는 알림을 로그인 화면에 표시해야됨 ",
            responses = {
                    @ApiResponse(responseCode = "302", description = "'프론트주소/login?activate=true' 성공 시 리턴")
            })
    @GetMapping("/verify")
    public void callback(@RequestParam("code") String uuid, HttpServletResponse response) throws IOException {
        try {
            memberService.verifyAndActivateMember(uuid);
        } catch (IllegalStateException | EntityNotFoundException ex) {
            response.sendRedirect(clientId + "/needActivate?expired=true");
        }

        response.sendRedirect(clientId + "/login?activate=true");

    }
    @PostMapping("/activate/resend") //활성화 메일 재요청
    public ResponseEntity<ResponseDTO<?>> activateReSend(@Valid @RequestBody MemberEmailRequest memberEmailRequest) {
        String email = memberEmailRequest.getEmail();
        memberService.resendActivateMail(email);

        return build(email + " 메일함을 확인하여 계정을 활성화 시켜주세요.", CREATED);

    }
     
    @PostMapping("/password/reset") //비밀번호 초기화 요청
    public ResponseEntity<ResponseDTO<?>> passwordReset(@Valid @RequestBody MemberEmailRequest memberEmailRequest) {
        String email = memberEmailRequest.getEmail();
        memberService.passwordResetRequest(email);

        return build(email + " 메일함을 확인하여 비밀번호 초기화 진행해주세요.", CREATED);

    }

    @PostMapping("/password/change") //바꿀 비밀번호 받기
    public ResponseEntity<ResponseDTO<?>> passwordReset(@Valid @RequestBody MemberPasswordChangeRequest memberPasswordChangeRequest) {
        String password = memberPasswordChangeRequest.getPassword();
        String passwordConfirm = memberPasswordChangeRequest.getPasswordConfirm();
        String email = memberPasswordChangeRequest.getEmail();
        String uuid = memberPasswordChangeRequest.getUuid();

        if (!password.equals(passwordConfirm)) {
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인란이 일치하지 않습니다.");
        }

        memberService.changePassword(email, password, uuid);

        return build("패스워드 변경이 완료되었습니다.", CREATED);

    }

    @GetMapping("/{memberId}")
    public Member findMember(@PathVariable Long memberId) {
        Member findMember = memberService.findMemberById(memberId);
        return findMember;
    }

    @GetMapping("/test")
    public String test() {
        System.out.println("현재 멤버 아디: " + SecurityUtil.getCurrentMemberId());
        Long loginMemberId = SecurityUtil.getCurrentMemberId();
        return loginMemberId.toString();
    }


    /*@PostMapping("/test")
    public Member test() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            // 인증된 사용자의 ID를 가져옴
            Long memberId = (Long) authentication.getPrincipal();

            // 사용자 ID를 기반으로 멤버 정보를 조회
            return memberService.findMemberById(memberId);
        }
        return new Member();
    }*/




}
