package wku.smartplant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wku.smartplant.domain.Notification;
import wku.smartplant.domain.NotificationType;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByMemberIdOrderByCreatedDateDesc(Long memberId);
    Optional<Notification> findByIdAndMemberId(Long id, Long memberId);
    void deleteAllByMemberId(Long memberId);
    void deleteByNotificationTypeAndMemberId(NotificationType type, Long memberId);
}
