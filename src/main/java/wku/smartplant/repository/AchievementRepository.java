package wku.smartplant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wku.smartplant.domain.Achievement;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {
}
