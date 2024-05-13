package wku.smartplant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import wku.smartplant.dto.ResponseDTO;
import wku.smartplant.dto.ResponseEntityBuilder;
import wku.smartplant.jwt.JwtTokenUtil;
import wku.smartplant.service.RefreshTokenService;

import java.util.Map;

import static wku.smartplant.dto.ResponseEntityBuilder.*;

@RestController
@RequiredArgsConstructor
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    @GetMapping("/token/refresh")
    @Operation(summary = "리프레시 토큰으로 액세스 토큰 재발급",
            description = "리프레시 토큰으로 액세스 토큰 재발급 받기. 헤더에 액세스 토큰 보내듯이 리프레시 토큰을 보내면됨" ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "토큰 재발급 성공. content : \"토큰값\" 으로 문자열 리턴"),
                    @ApiResponse(responseCode = "406", description = "토큰 재발급 실패. 로그아웃, 비밀번호 변경으로 리프레시 토큰이 삭제되었거나 리프레시 토큰 시간 만료")
            })
    public ResponseEntity<ResponseDTO<String>> refreshAccessToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return build("토큰이 없습니다.", HttpStatus.BAD_REQUEST, null);
            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Refresh Token");
        }

        String refreshToken = authorizationHeader.split(" ")[1];

        if (!JwtTokenUtil.validateRefreshToken(refreshToken)) {
            return build("리프레시 토큰 시간이 만료되었거나. 리프레시 토큰이 아닙니다.", HttpStatus.BAD_REQUEST, null);
        }

        String memberId = JwtTokenUtil.getMemberId(refreshToken);
        String newAccessToken = refreshTokenService.getNewAccessToken(memberId, refreshToken);
        return build("새로운 토큰이 발급되었습니다.", HttpStatus.OK, newAccessToken);
    }

}
