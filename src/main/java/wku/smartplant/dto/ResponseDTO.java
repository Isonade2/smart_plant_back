package wku.smartplant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
public class ResponseDTO<T> {
    private String message;
    private T content;

    public ResponseDTO(String message) {
        this.message = message;
    }
}
