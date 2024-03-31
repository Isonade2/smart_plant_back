package wku.smartplant.dto.orderitem;


import lombok.Data;
import wku.smartplant.domain.OrderItem;
import wku.smartplant.dto.item.ItemDTO;

@Data
public class OrderItemDTO {
    private Long id;
    private Long orderId;
    private ItemDTO item;
    private int orderPrice;
    private int count;

    public OrderItemDTO() {
    }

    public OrderItemDTO(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.orderId = orderItem.getOrder().getId();
        this.item = new ItemDTO(orderItem.getItem());
        this.orderPrice = orderItem.getOrderPrice();
        this.count = orderItem.getCount();
    }


}
