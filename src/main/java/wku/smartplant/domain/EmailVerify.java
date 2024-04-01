package wku.smartplant.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailVerify extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "email_verify_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String uuid;

    public EmailVerify(Member member) {
        this.member = member;
        this.uuid = UUID.randomUUID().toString();
    }
}
