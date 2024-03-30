package wku.smartplant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wku.smartplant.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
