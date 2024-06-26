package wku.smartplant.controller;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
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
    @Operation(summary = "주문 목록 조회",
            description = "요청 시 헤더 토큰 필요. 해당 멤버의 주문정보들을 리턴해줌")
    public ResponseEntity<ResponseDTO<List<OrderDTO>>> getOrder(){
        log.info("OrderController.getOrder");
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        List<OrderDTO> orders = orderService.getOrders(currentMemberId);
        return ResponseEntityBuilder.build("주문 조회 성공", HttpStatus.OK,orders);
    }

    @PostMapping // 클라이언트로부터 주문을 받는 컨트롤러
    @Operation(summary = "식물 주문",
            description = "요청 시 헤더 토큰 필요. 식물을 주문할 때 사용하는 컨트롤러")
    public ResponseEntity<ResponseDTO<OrderDTO>> createOrder(@Valid @RequestBody OrderRequest orderRequest, BindingResult bindingResult){
        log.info("OrderController.createOrder");
        log.info("orderRequest : {}", orderRequest);
        if (bindingResult.hasErrors()) {
            return ResponseEntityBuilder.build(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST,null);
        }
        Long currentMemberId = SecurityUtil.getCurrentMemberId(); // 로그인 되었는지 확인

        OrderDTO orderDTO = orderService.createOrderOne(currentMemberId, orderRequest);

        return ResponseEntityBuilder.build("주문 성공", HttpStatus.OK, orderDTO);
    }

    @PostMapping("/{orderId}/cancel") // 주문 취소
    @Operation(summary = "주문 취소",
            description = "요청 시 헤더 토큰 필요. 주문을 취소할 때 사용하는 컨트롤러")
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
