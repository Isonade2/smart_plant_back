package wku.smartplant.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Notification extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "notification_id")
    private Long id;

    private String description;

    private String link;

    @Builder.Default
    private Boolean isRead = false;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public void changeRead() {
        isRead = true;
    }
}
