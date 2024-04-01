package wku.smartplant.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import wku.smartplant.jwt.SecurityUtil;
import wku.smartplant.domain.Member;
import wku.smartplant.dto.member.MemberJoinRequest;
import wku.smartplant.dto.ResponseDTO;
import wku.smartplant.dto.member.MemberLoginRequest;
import wku.smartplant.dto.member.MemberLoginResponse;
import wku.smartplant.service.MemberService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<ResponseDTO<?>> join(@Valid @RequestBody MemberJoinRequest memberJoinRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { //정규식 체크
            List<FieldError> errorList = bindingResult.getFieldErrors();
            String errorMessage = errorList.stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return build(errorMessage, BAD_REQUEST);
        }

        Member joinedMember = memberService.joinMember(memberJoinRequest);
        return build("가입 성공 " + joinedMember.getEmail() + "을 확인하여 계정을 활성화시켜주세요.", OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<?>> login(@RequestBody MemberLoginRequest memberLoginRequest) {
        MemberLoginResponse memberLoginResponse = memberService.loginMember(memberLoginRequest);

        if (memberLoginResponse.getToken() == null) {
            return build("이메일 또는 패스워드가 불일치 합니다.", BAD_REQUEST);
        }
        return build("로그인 성공.", OK, memberLoginResponse);
    }

    @GetMapping("/verify")
    public void callback(@RequestParam("code") String uuid, HttpServletResponse response) throws IOException {
        memberService.verifyAndActivateMember(uuid);
        response.sendRedirect(clientId + "/login");

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
