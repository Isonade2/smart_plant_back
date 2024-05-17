package wku.smartplant.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wku.smartplant.domain.Notification;
import wku.smartplant.domain.NotificationType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {

    private Long id;
    private String description;
    private String link;
    private Boolean isRead;
    private NotificationType notificationType;
    private LocalDateTime createdDate;

    public NotificationDTO(Notification notification) {
        id = notification.getId();
        description = notification.getDescription();
        link = notification.getLink();
        isRead = notification.getIsRead();
        notificationType = notification.getNotificationType();
        createdDate = notification.getCreatedDate();
    }
}
