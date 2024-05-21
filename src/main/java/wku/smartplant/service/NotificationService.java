package wku.smartplant.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wku.smartplant.domain.Member;
import wku.smartplant.domain.Notification;
import wku.smartplant.domain.NotificationType;
import wku.smartplant.dto.notification.NotificationDTO;
import wku.smartplant.repository.MemberRepository;
import wku.smartplant.repository.NotificationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;

    public void createNotification(Long memberId, String description, String link, NotificationType type) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("해당 회원이 없습니다."));

        if (type != NotificationType.퀘스트)
            notificationRepository.deleteByNotificationTypeAndMemberId(type, memberId); //기존 같은 타입의 식물 알림은 제거함

        Notification notification = Notification.builder()
                .description(description)
                .link(link)
                .notificationType(type)
                .member(findMember)
                .build();

        notificationRepository.save(notification);
    }

    @Transactional
    public Long readNotification(Long notificationId, Long memberId) {
        Notification findNotification = notificationRepository.findByIdAndMemberId(notificationId, memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 기록이 없습니다."));

        findNotification.changeRead(); //read를 true로 변경
        return findNotification.getId();
    }

    @Transactional
    public Long deleteOneNotification(Long notificationId, Long memberId) {
        Notification findNotification = notificationRepository.findByIdAndMemberId(notificationId, memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 기록이 없습니다."));

        notificationRepository.delete(findNotification);
        return findNotification.getId();
    }

    @Transactional
    public void deleteAllNotification(Long memberId) {
        notificationRepository.deleteAllByMemberId(memberId);
    }


    public List<NotificationDTO> getNotificationListByMemberId(Long memberId) {
        List<Notification> notifications = notificationRepository.findAllByMemberIdOrderByCreatedDateDesc(memberId);
        return notifications.stream().map(NotificationDTO::new).toList();
    }

    public long getNotReadCountByMemberId(Long memberId) {
        return notificationRepository.countByMemberIdAndIsReadFalse(memberId);
    }
}
