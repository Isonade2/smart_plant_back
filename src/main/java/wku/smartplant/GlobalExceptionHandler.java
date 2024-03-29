package wku.smartplant;

import io.jsonwebtoken.MalformedJwtException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {
        // 상태 코드 404 NOT FOUND와 함께 예외 메시지를 반환
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        // 오류 메시지와 HTTP 상태 코드를 설정하여 응답 생성
        Map<String, Object> body = new HashMap<>();
        body.put("error", ex.getMessage());
        body.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
