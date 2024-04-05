package wku.smartplant.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import wku.smartplant.repository.ItemRepository;
import wku.smartplant.repository.MemberRepository;
import wku.smartplant.repository.OrderItemRepository;
import wku.smartplant.repository.OrderRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class OrderItemTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    public void Test(){
        Member memberA = Member.builder()
                .username("memberA")
                .email("c1004sos@naver.com")
                .password("1234")
                .build();

        memberRepository.save(memberA);

        Item item = new Item("상추", 10000, 15,PlantType.상추);
        itemRepository.save(item);

        OrderItem orderItem = OrderItem.createOrderItem(item, 10000, 10);
        orderItemRepository.save(orderItem);

        Order order = new Order(memberA, OrderStatus.준비, new Address("서울", "강가", "123-123", "1232"));
        order.addOrderItem(orderItem);
        orderRepository.save(order);




    }

    @Test
    public void Test2() {
        Member memberA = Member.builder()
                .username("memberA")
                .email("c1004sos@naver.com")
                .password("1234")
                .build();

        memberRepository.save(memberA);

        Item item = new Item("상추", 10000, 15,PlantType.상추);
        itemRepository.save(item);

        OrderItem orderItem = OrderItem.createOrderItem(item, 10000, 10);
        orderItemRepository.save(orderItem);

        Order order = new Order(memberA, OrderStatus.준비, new Address("서울", "강가", "123-123", "1232"));
        order.addOrderItem(orderItem);
        orderRepository.save(order);


        List<Order> byUserId = orderRepository.findByMemberId(memberA.getId());
    }
}