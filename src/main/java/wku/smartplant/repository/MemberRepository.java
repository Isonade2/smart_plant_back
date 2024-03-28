package wku.smartplant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wku.smartplant.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
