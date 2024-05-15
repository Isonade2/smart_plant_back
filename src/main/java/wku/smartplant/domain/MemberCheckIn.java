package wku.smartplant.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor()
@Getter
//출석 체크 테이블 도메인
public class MemberCheckIn {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime checkInTime;

    @Builder
    public MemberCheckIn(Member member, LocalDateTime checkInTime) {
        this.member = member;
        this.checkInTime = checkInTime;
    }
}
