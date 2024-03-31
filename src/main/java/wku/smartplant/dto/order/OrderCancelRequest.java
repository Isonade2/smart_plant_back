package wku.smartplant.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderCancelRequest {
    @NotNull(message = "주문 ID는 필수입니다.")
    private Long orderId;
}
