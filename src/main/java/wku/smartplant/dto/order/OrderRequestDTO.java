package wku.smartplant.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequestDTO {
    @NotNull(message = "구매할 상품을 선택해주세요.")
    private Long itemId;
    @NotNull(message = "상품의 수량을 선택해주세요.")
    private int count;
}
