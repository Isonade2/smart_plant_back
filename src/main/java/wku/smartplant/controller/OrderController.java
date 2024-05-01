package wku.smartplant.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import wku.smartplant.domain.Member;
import wku.smartplant.dto.ResponseDTO;
import wku.smartplant.dto.ResponseEntityBuilder;
import wku.smartplant.dto.order.OrderCancelRequest;
import wku.smartplant.dto.order.OrderDTO;
import wku.smartplant.dto.order.OrderRequest;
import wku.smartplant.jwt.SecurityUtil;
import wku.smartplant.repository.MemberRepository;
import wku.smartplant.service.OrderService;

import java.security.Security;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final MemberRepository memberRepository;



    @GetMapping // 주문 목록 조회
    public ResponseEntity<ResponseDTO<List<OrderDTO>>> getOrder(){
        log.info("OrderController.getOrder");
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        List<OrderDTO> orders = orderService.getOrders(currentMemberId);
        return ResponseEntityBuilder.build("주문 조회 성공", HttpStatus.OK,orders);
    }

    @PostMapping // 주문 생성
    public ResponseEntity<ResponseDTO<Long>> createOrder(@Valid @RequestBody OrderRequest orderRequest, BindingResult bindingResult){
        log.info("OrderController.createOrder");
        log.info("orderRequest : {}", orderRequest);
//        if (bindingResult.hasErrors()) {
//            return ResponseEntityBuilder.build(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
//        }
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        Long orderOne = orderService.createOrderOne(currentMemberId, orderRequest);

        return ResponseEntityBuilder.build("주문 성공", HttpStatus.OK, orderOne);
    }

    @PostMapping("/{orderId}/cancel") // 주문 취소
    public ResponseEntity<ResponseDTO<?>> cancelOrder(
            @Valid @RequestBody OrderCancelRequest orderCancelRequest, BindingResult bindingResult){
        log.info("OrderController.cancelOrder");
        log.info("orderCancelRequest : {}", orderCancelRequest);
        if (bindingResult.hasErrors()) {
            return ResponseEntityBuilder.build(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        orderService.cancelOrder(orderCancelRequest.getOrderId());
        return ResponseEntityBuilder.build("주문 취소 성공", HttpStatus.OK);
    }






    @PostMapping("/test")
    public String test(){
        log.info("OrderController.test");
        Member member = memberRepository.findById(1L).get();
        System.out.println("member = " + member);

        return "test";
    }
}
