package wku.smartplant.domain;


import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.stereotype.Repository;

@Entity
@Repository
@Table(name = "orders")
@Getter
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

}
