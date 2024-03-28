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
    private String resultMsg;
    private T resultData;

    public ResponseDTO(HttpStatus statusCode, String resultMsg) {
        this.statusCode = statusCode;
        this.resultMsg = resultMsg;
        this.resultData = null;
    }

}
