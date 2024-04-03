package wku.smartplant.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wku.smartplant.domain.PlantHistory;

public interface PlantHistoryRepository extends JpaRepository<PlantHistory, Long> {
    Page<PlantHistory> findByPlantId(Long plantId, Pageable pageable);
}
