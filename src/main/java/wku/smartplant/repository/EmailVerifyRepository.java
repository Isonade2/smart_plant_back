package wku.smartplant.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import wku.smartplant.domain.EmailVerify;

import java.util.Optional;

public interface EmailVerifyRepository extends JpaRepository<EmailVerify, Long> {
    Optional<EmailVerify> findByMemberId(Long id);

    @EntityGraph(attributePaths = {"member"})
    Optional<EmailVerify> findByUuid(String uuid);

    @EntityGraph(attributePaths = {"member"})
    Optional<EmailVerify> findByEmail(String email);
}

