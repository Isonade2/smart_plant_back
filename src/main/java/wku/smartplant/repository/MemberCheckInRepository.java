package wku.smartplant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wku.smartplant.domain.MemberCheckIn;

public interface MemberCheckInRepository extends JpaRepository<MemberCheckIn, Long> {
    boolean existsByMemberId(Long memberId);
}
