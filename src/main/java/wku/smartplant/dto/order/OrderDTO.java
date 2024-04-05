package wku.smartplant.dto.order;


import lombok.Data;
import wku.smartplant.domain.Address;
import wku.smartplant.domain.OrderStatus;
import wku.smartplant.dto.orderitem.OrderItemDTO;

import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private OrderStatus status;
    private Address address;
    private List<OrderItemDTO> orderItems;

    public OrderDTO(Long id, OrderStatus status, Address address, List<OrderItemDTO> orderItems) {
        this.id = id;
        this.status = status;
        this.address = address;
        this.orderItems = orderItems;
    }

}
