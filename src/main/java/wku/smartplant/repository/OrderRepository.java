package wku.smartplant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wku.smartplant.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

//    @Query("select o from Order o where o.member.id = :userid")
    List<Order> findByMemberId(@Param("userid") Long userid);
    Optional<Order> findByPlantId(Long plantId);
}
