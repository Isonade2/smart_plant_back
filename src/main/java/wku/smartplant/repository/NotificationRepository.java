package wku.smartplant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wku.smartplant.domain.Notification;
import wku.smartplant.domain.NotificationType;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByMemberIdOrderByCreatedDateDesc(Long memberId);
    Optional<Notification> findByIdAndMemberId(Long id, Long memberId);
    void deleteAllByMemberId(Long memberId);
    void deleteByNotificationTypeAndMemberId(NotificationType type, Long memberId);
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.member.id = :memberId AND n.isRead = false")
    long countByMemberIdAndIsReadFalse(@Param("memberId") Long memberId);
}
