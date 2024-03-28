package wku.smartplant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wku.smartplant.domain.Plant;

public interface PlantRepository extends JpaRepository<Plant, Long> {
}
