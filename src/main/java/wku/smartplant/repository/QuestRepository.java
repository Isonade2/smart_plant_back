package wku.smartplant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wku.smartplant.domain.Quest;

public interface QuestRepository extends JpaRepository<Quest,Long> {
}
