package wku.smartplant.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotBlank
    private String uuid;

    public EmailVerify(Member member, String email) {
        this.member = member;
        this.email = email;
        this.uuid = UUID.randomUUID().toString();
    }
}
