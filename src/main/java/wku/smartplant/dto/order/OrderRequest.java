package wku.smartplant.dto.order;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import wku.smartplant.domain.Address;

@Data
@RequiredArgsConstructor
public class OrderRequest {
    @NotNull(message = "구매할 상품을 선택해주세요.")
    private final Long itemId;

    @NotNull
    @Min(value = 1, message = "상품의 수량은 1개 이상이어야 합니다.")
    @Max(value = 100, message = "상품의 수량은 100개 이하이어야 합니다.")
    private final int count;
    @NotNull
    private AddressDTO address;
    @NotNull
    private String name;

//    public OrderRequest() {
//    }
}
