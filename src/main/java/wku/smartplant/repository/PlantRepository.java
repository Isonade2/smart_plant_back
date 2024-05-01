package wku.smartplant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wku.smartplant.domain.Plant;

import java.util.List;
import java.util.Optional;

public interface PlantRepository extends JpaRepository<Plant, Long> {

    Optional<Plant> findByUuid(String uuid);
    Optional<Plant> findByIdAndMemberId(Long plantId, Long memberId);
    List<Plant> findAllByMemberId(Long memberId);

    //countByMemberId
    Long countByMemberId(Long memberId);
}
