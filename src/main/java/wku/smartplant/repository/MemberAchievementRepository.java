package wku.smartplant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wku.smartplant.domain.MemberAchievement;

import java.util.List;
import java.util.Optional;

public interface MemberAchievementRepository extends JpaRepository<MemberAchievement, Long> {
    Optional<List<MemberAchievement>> findAllByMemberId(Long memberId);
}
