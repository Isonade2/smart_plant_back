package wku.smartplant.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import wku.smartplant.exception.OrderNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Entity
@Repository
@Table(name = "orders")
@Getter
@NoArgsConstructor
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_id")
    private Plant plant;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    public Order(OrderStatus status, Address address){
        this.id = null;
        this.plant = null;
        this.status = status;
        this.address = address;
    }

    public Order(Member member, OrderStatus status, Address address){
        changeMember(member);
        this.status = status;
        this.address = address;
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        System.out.println("OrderItem added");
        orderItem.changeOrder(this);
    }

    public void changeMember(Member member){
        this.member = member;
    }

    public void cancel(){
        if (this.status != OrderStatus.준비){
            throw new OrderNotFoundException("이미 배송중이거나 완료된 상품은 취소가 불가능합니다.");
        }

        this.status = OrderStatus.취소;
        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }
}
