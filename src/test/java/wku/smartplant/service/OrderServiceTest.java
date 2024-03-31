package wku.smartplant.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import wku.smartplant.dto.order.OrderRequest;

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

}