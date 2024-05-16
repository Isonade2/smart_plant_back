package wku.smartplant.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wku.smartplant.domain.Notification;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {

    private Long id;
    private String description;
    private String link;
    private Boolean read;
    private LocalDateTime createdDate;

    public NotificationDTO(Notification notification) {
        id = notification.getId();
        description = notification.getDescription();
        link = notification.getLink();
        read = notification.getRead();
        createdDate = notification.getCreatedDate();
    }
}
