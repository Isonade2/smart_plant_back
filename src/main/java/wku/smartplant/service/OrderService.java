package wku.smartplant.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wku.smartplant.domain.*;
import wku.smartplant.dto.order.OrderDTO;
import wku.smartplant.dto.order.OrderRequest;
import wku.smartplant.dto.orderitem.OrderItemDTO;
import wku.smartplant.exception.OrderNotFoundException;
import wku.smartplant.repository.ItemRepository;
import wku.smartplant.repository.MemberRepository;
import wku.smartplant.repository.OrderItemRepository;
import wku.smartplant.repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderItemRepository orderItemRepository;


    // 상품 주문 서비스
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

    public List<OrderDTO> getOrders(Long memberId) {
        List<Order> orders = orderRepository.findByMemberId(memberId);

        List<OrderDTO> collect = orders.stream()
                .map(order -> {
                    List<OrderItemDTO> orderItemDTOs = order.getOrderItems().stream()
                            .map(OrderItemDTO::new) // OrderItem 객체를 OrderItemDTO 객체로 변환
                            .collect(Collectors.toList());
                    return new OrderDTO(order.getId(), order.getStatus(), order.getAddress(), orderItemDTOs);
                })
                .collect(Collectors.toList());
        return collect;
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("존재하지 않는 주문입니다."));
        order.cancel();
    }
}
