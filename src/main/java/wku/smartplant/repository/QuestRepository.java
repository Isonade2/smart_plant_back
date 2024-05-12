package wku.smartplant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wku.smartplant.domain.Quest;

import java.util.Optional;

public interface QuestRepository extends JpaRepository<Quest,Long> {
}
