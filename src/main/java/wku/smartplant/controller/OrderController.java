package wku.smartplant.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wku.smartplant.domain.Member;
import wku.smartplant.dto.ResponseDTO;
import wku.smartplant.dto.ResponseEntityBuilder;
import wku.smartplant.dto.order.OrderCancelRequest;
import wku.smartplant.dto.order.OrderRequest;
import wku.smartplant.jwt.SecurityUtil;
import wku.smartplant.repository.MemberRepository;
import wku.smartplant.service.OrderService;

import java.security.Security;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final MemberRepository memberRepository;


    @PostMapping
    public ResponseEntity<ResponseDTO<?>> createOrder(@Valid @RequestBody OrderRequest orderRequest, BindingResult bindingResult){
        log.info("OrderController.createOrder");
        log.info("orderRequest : {}", orderRequest);
        if (bindingResult.hasErrors()) {
            return ResponseEntityBuilder.build(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        //Long currentMemberId = SecurityUtil.getCurrentMemberId();
        Long orderOne = orderService.createOrderOne(1L, orderRequest);

        return ResponseEntityBuilder.build("주문 성공", HttpStatus.OK, orderOne);
    }

    @PostMapping("/cancel")
    public ResponseEntity<ResponseDTO<?>> cancelOrder(@Valid @RequestBody OrderCancelRequest orderCancelRequest, BindingResult bindingResult){
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
