package wku.smartplant.exception;

import io.jsonwebtoken.MalformedJwtException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import wku.smartplant.dto.ResponseDTO;
import wku.smartplant.dto.ResponseEntityBuilder;
import wku.smartplant.jwt.JwtTokenUtil;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseDTO<?>> handleEntityNotFound(EntityNotFoundException ex) {
        // 상태 코드 404 NOT FOUND와 함께 예외 메시지를 반환
        return ResponseEntityBuilder.build(ex.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ResponseDTO<?>> handleEmailAlreadyExistsExceptionHandler(EmailAlreadyExistsException ex) {
        // 클라이언트에게 반환할 메시지
        return ResponseEntityBuilder.build(ex.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDTO<?>> handleRuntimeException(RuntimeException ex) {
        // 오류 메시지와 HTTP 상태 코드를 설정하여 응답 생성
        return ResponseEntityBuilder.build(ex.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(JwtTokenUtil.TokenValidationException.class)
    public ResponseEntity<ResponseDTO<?>> handleTokenValidationException(RuntimeException ex) {
        // 오류 메시지와 HTTP 상태 코드를 설정하여 응답 생성
        return ResponseEntityBuilder.build(ex.getMessage(), BAD_REQUEST);
    }
}
