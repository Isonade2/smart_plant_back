package wku.smartplant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

@RestController
@RequiredArgsConstructor
@Slf4j
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;

    @Value("${kakao.client_id}")
    private String client_id;

    @Value("${client.url}")
    private String clientUrl;

    @Operation(summary = "카카오 로그인 콜백",
            description = "성공 시 사용자는 지정된 URL로 리다이렉트됩니다. 리다이렉트 URL은 다음과 같은 쿼리 파라미터를 포함합니다: 'email', 'username', 'token'. 각 파라미터는 URL 인코딩됩니다.",
            responses = {
                    @ApiResponse(responseCode = "302", description = "'프론트주소/login-success?email=xxx&username=xxx&token=xxx' 성공 시 이런식으로 리턴",
                            content = @Content(schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "400", description = "'프론트주소/error-page?message=xxx' 실패 시 파람에 에러메세지 담아서 리다이렉트"),
                    @ApiResponse(responseCode = "500", description = "서버 에러")
            })
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
            response.sendRedirect(clientUrl + "/login-success?" + params);
        } catch (EmailAlreadyExistsException ex) { //이미 다른플랫폼으로 가입 했을시
            String errorMessage = URLEncoder.encode("이미 다른 플랫폼으로 가입한 이메일입니다.", StandardCharsets.UTF_8);
            response.sendRedirect(clientUrl + "/error-page?message=" + errorMessage);
        }

    }
}
