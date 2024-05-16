package wku.smartplant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wku.smartplant.domain.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByMemberIdOrderByCreatedDateDesc(Long memberId);
    Optional<Notification> findByNotificationIdAndMemberId(Long NotificationId, Long memberId);
}
