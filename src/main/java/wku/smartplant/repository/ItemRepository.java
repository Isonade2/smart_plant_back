package wku.smartplant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wku.smartplant.domain.Item;

public interface ItemRepository extends JpaRepository<Item,Long> {
}
