package wku.smartplant.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

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
    private OrderStatus orderStatus;

    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    public Order(OrderStatus orderStatus, Address address){
        this.id = null;
        this.plant = null;
        this.orderStatus = orderStatus;
        this.address = address;
    }

    public Order(Member member,OrderStatus orderStatus, Address address){
        changeMember(member);
        this.orderStatus = orderStatus;
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
}
