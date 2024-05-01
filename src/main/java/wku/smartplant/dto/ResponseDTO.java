package wku.smartplant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO<T> {
    @Schema(description = "응답 메세지")
    private String message;
    @Schema(description = "응답 데이터")
    private T content;

    public ResponseDTO(String message) {
        this.message = message;
    }
}
