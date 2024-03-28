package wku.smartplant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wku.smartplant.domain.PlantHistory;

public interface PlantHistoryRepository extends JpaRepository<PlantHistory, Long> {
}
