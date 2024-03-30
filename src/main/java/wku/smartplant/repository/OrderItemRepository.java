package wku.smartplant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wku.smartplant.domain.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
}
