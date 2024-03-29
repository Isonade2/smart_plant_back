package wku.smartplant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import wku.smartplant.domain.Member;
import wku.smartplant.dto.member.MemberJoinRequest;
import wku.smartplant.dto.ResponseDTO;
import wku.smartplant.dto.member.MemberLoginRequest;
import wku.smartplant.dto.member.MemberLoginResponse;
import wku.smartplant.service.MemberService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<ResponseDTO<?>> join(@Valid @RequestBody MemberJoinRequest memberJoinRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errorList = bindingResult.getFieldErrors();
            String errorMessage = errorList.stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(" / "));
            return new ResponseEntity<>(ResponseDTO.builder()
                    .statusCode(HttpStatus.BAD_REQUEST)
                    .resultMsg(errorMessage)
                    .build(), HttpStatus.BAD_REQUEST);
        }

        Member joinedMember = memberService.joinMember(memberJoinRequest);
        return new ResponseEntity<>(ResponseDTO.builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("가입 성공")
                .resultData(joinedMember)
                .build(),HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<?>> login(@RequestBody MemberLoginRequest memberLoginRequest) {
        MemberLoginResponse memberLoginResponse = memberService.loginMember(memberLoginRequest);

        if (memberLoginResponse.getToken() == null) {
            return new ResponseEntity<>(ResponseDTO.builder()
                    .resultMsg("이메일 또는 패스워드가 불일치 합니다.").build(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ResponseDTO.builder()
                .resultMsg("로그인 성공.")
                .resultData(memberLoginResponse).build(), HttpStatus.OK);
    }

    @PostMapping("/test")
    public Member test() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            // 인증된 사용자의 ID를 가져옴
            Long memberId = (Long) authentication.getPrincipal();

            // 사용자 ID를 기반으로 멤버 정보를 조회
            return memberService.findMemberById(memberId);
        }
        return new Member();
    }

}
