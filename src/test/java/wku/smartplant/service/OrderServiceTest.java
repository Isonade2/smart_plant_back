package wku.smartplant.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import wku.smartplant.dto.order.OrderDTO;
import wku.smartplant.dto.order.OrderRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(value = false)
class OrderServiceTest {
    @Autowired
    private OrderService orderService;


    @Test
    public void createOrderOne() {
        // given
        Long memberid = 1L;
        OrderRequest orderRequest = new OrderRequest(1L, 1);

        // when
        Long orderId = orderService.createOrderOne(memberid, orderRequest);

        // then
        assertNotNull(orderId);
    }

    @Test
    public void orderCancel(){
        // given
        Long memberid = 1L;
        OrderRequest orderRequest = new OrderRequest(1L, 1);
        Long orderId = orderService.createOrderOne(memberid, orderRequest);

        // when
        orderService.cancelOrder(orderId);


    }

    @Test
    public void getOrders(){
        // given
        Long memberid = 1L;
        OrderRequest orderRequest1 = new OrderRequest(1L, 1);
        OrderRequest orderRequest2 = new OrderRequest(1L, 2);
        OrderRequest orderRequest3 = new OrderRequest(1L, 3);
        OrderRequest orderRequest4 = new OrderRequest(1L, 4);
        Long orderId1 = orderService.createOrderOne(memberid, orderRequest1);
        Long orderId2 = orderService.createOrderOne(memberid, orderRequest2);
        Long orderId3 = orderService.createOrderOne(memberid, orderRequest3);
        Long orderId4 = orderService.createOrderOne(memberid, orderRequest4);

        // when
        List<OrderDTO> orders = orderService.getOrders(memberid);

        for (OrderDTO order : orders) {
            System.out.println("order = " + order);
        }


    }

}