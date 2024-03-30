package wku.smartplant.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wku.smartplant.domain.*;
import wku.smartplant.dto.order.OrderRequest;
import wku.smartplant.repository.ItemRepository;
import wku.smartplant.repository.MemberRepository;
import wku.smartplant.repository.OrderItemRepository;
import wku.smartplant.repository.OrderRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderItemRepository orderItemRepository;

    public Long createOrderOne(Long memberid, OrderRequest orderRequest) {
        log.info("OrderService.createOrder");
        log.info("orderRequest : {}", orderRequest);

        Member member = memberRepository.findById(memberid).get();
        log.info("member : {}", member);
        Item item = itemRepository.findById(orderRequest.getItemId()).get();

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), orderRequest.getCount());
        orderItemRepository.save(orderItem);
        Order order = new Order(member, OrderStatus.준비, new Address("서울", "강가", "123-123", "1232"));
        order.addOrderItem(orderItem);
        orderRepository.save(order);
        return order.getId();
    }
}
