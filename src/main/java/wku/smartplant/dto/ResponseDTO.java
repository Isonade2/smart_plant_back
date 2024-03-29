package wku.smartplant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
public class ResponseDTO<T> {
    private HttpStatus statusCode;
    private String message;
    private T content;

    public ResponseDTO(HttpStatus statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.content = null;
    }

}
