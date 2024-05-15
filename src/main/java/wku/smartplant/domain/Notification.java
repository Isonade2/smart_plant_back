package wku.smartplant.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Notification extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "notification_id")
    private Long id;

    private String description;

    @JoinColumn(name = "member_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;
}
