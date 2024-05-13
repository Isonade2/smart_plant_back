package wku.smartplant.exception;

import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import wku.smartplant.dto.ResponseDTO;
import wku.smartplant.jwt.JwtTokenUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
        return build(ex.getMessage(), BAD_REQUEST);
    }

    // GlobalExceptionHandler.java
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ResponseDTO<?>> handleOrderNotFoundException(OrderNotFoundException ex) {
        return build(ex.getMessage(), BAD_REQUEST);
    }

    // GlobalExceptionHandler.java
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ResponseDTO<?>> handleItemNotFoundException(ItemNotFoundException ex) {
        return build(ex.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseDTO<?>> handleBadCredentialsException(BadCredentialsException ex) {
        return build(ex.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredRefreshTokenException.class)
    public ResponseEntity<ResponseDTO<?>> handleExpiredRefreshTokenException(ExpiredRefreshTokenException ex) {
        return build(ex.getMessage(), NOT_ACCEPTABLE);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDTO<?>> handleRuntimeException(RuntimeException ex) {
        return build(ex.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class) //jpa 예외 처리
    public ResponseEntity<ResponseDTO<?>> handleConstraintViolationException(ConstraintViolationException ex) {
        String errorMessages = ex.getConstraintViolations().stream()
                .map(constraintViolation -> constraintViolation.getPropertyPath() + " " + constraintViolation.getMessage())
                .collect(Collectors.joining(", "));
        return build(errorMessages, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) //valid 예외 처리
    public ResponseEntity<ResponseDTO<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return build("올바르지 않은 형식이 있습니다.", BAD_REQUEST, errors);
//        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ResponseDTO<?>> handleIllegalStateException(IllegalStateException ex) {
        return build(ex.getMessage(), NOT_ACCEPTABLE);
    }
}
