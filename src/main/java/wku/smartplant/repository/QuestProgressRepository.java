package wku.smartplant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wku.smartplant.domain.QuestProgress;

public interface QuestProgressRepository extends JpaRepository<QuestProgress,Long> {
}
