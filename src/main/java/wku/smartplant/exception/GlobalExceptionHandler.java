package wku.smartplant.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import wku.smartplant.dto.ResponseDTO;
import wku.smartplant.jwt.JwtTokenUtil;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;
import static wku.smartplant.dto.ResponseEntityBuilder.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseDTO<?>> handleEntityNotFound(EntityNotFoundException ex) {
        // 상태 코드 404 NOT FOUND와 함께 예외 메시지를 반환
        return build(ex.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ResponseDTO<?>> handleEmailAlreadyExistsExceptionHandler(EmailAlreadyExistsException ex) {
        // 클라이언트에게 반환할 메시지
        return build(ex.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<ResponseDTO<?>> handleIllegalAccessException(IllegalAccessException ex) {
        return build(ex.getMessage(), FORBIDDEN);
    }

    @ExceptionHandler(NoUserAuthorizationException.class)
    public ResponseEntity<ResponseDTO<?>> handleNoUserAuthorization(NoUserAuthorizationException ex) {
        return build(ex.getMessage(), UNAUTHORIZED);
    }

    @ExceptionHandler(JwtTokenUtil.TokenValidationException.class)
    public ResponseEntity<ResponseDTO<?>> handleTokenValidationException(JwtTokenUtil.TokenValidationException ex) {
        // 오류 메시지와 HTTP 상태 코드를 설정하여 응답 생성
        return build(ex.getMessage(), BAD_REQUEST);
    }

    // GlobalExceptionHandler.java
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ResponseDTO<?>> handleOrderNotFoundException(OrderNotFoundException ex) {
        // 오류 메시지와 HTTP 상태 코드를 설정하여 응답 생성
        return build(ex.getMessage(), BAD_REQUEST);
    }

    // GlobalExceptionHandler.java
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ResponseDTO<?>> handleItemNotFoundException(ItemNotFoundException ex) {
        // 오류 메시지와 HTTP 상태 코드를 설정하여 응답 생성
        return build(ex.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseDTO<?>> handleBadCredentialsException(ItemNotFoundException ex) {
        // 오류 메시지와 HTTP 상태 코드를 설정하여 응답 생성
        return build(ex.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ResponseDTO<?>> handleIllegalStateException(IllegalStateException ex) {
        // 오류 메시지와 HTTP 상태 코드를 설정하여 응답 생성
        return build(ex.getMessage(), BAD_REQUEST);
    }
}
