package wku.smartplant.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wku.smartplant.dto.member.MemberLoginResponse;
import wku.smartplant.exception.EmailAlreadyExistsException;
import wku.smartplant.service.KakaoLoginService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@Slf4j
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;

    @Value("${kakao.client_id}")
    private String client_id;

    @GetMapping("/user/kakao/callback")
    public void callback(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
        try {
            //카카오 로그인 로직
            String accessToken = kakaoLoginService.getAccessTokenFromKakao(client_id, code);
            MemberLoginResponse memberLoginResponse = kakaoLoginService.joinOrLoginMember(accessToken);
            
            //파라미터 인코딩
            String emailParam = URLEncoder.encode(memberLoginResponse.getEmail(), StandardCharsets.UTF_8.toString());
            String usernameParam = URLEncoder.encode(memberLoginResponse.getUsername(), StandardCharsets.UTF_8.toString());
            String tokenParam = URLEncoder.encode(memberLoginResponse.getToken(), StandardCharsets.UTF_8.toString());

            String params = "email=" + emailParam + "&username=" + usernameParam + "&token=" + tokenParam;
            response.sendRedirect("http://localhost:5173/loginSuccess?" + params);
        } catch (EmailAlreadyExistsException ex) { //이미 다른플랫폼으로 가입 했을시
            String errorMessage = URLEncoder.encode("이미 다른 플랫폼으로 가입한 이메일입니다.", StandardCharsets.UTF_8);
            response.sendRedirect("http://localhost:5173/errorPage?message=" + errorMessage);
        }

    }
}
