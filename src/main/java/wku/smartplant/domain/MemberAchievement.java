package wku.smartplant.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@RequiredArgsConstructor
@Getter
public class MemberAchievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_achievement_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "achievement_id")
    private Achievement achievement;

    private int progress;
    private boolean isCompleted;

    @Builder
    public MemberAchievement(Member member, Achievement achievement, int progress, boolean isCompleted) {
        this.id = null;
        this.member = member;
        this.achievement = achievement;
        this.progress = progress;
        this.isCompleted = isCompleted;

    }

    public void updateProgress(String type, int progress) {
        if (type.equals("누적"))
            this.progress += progress;
        else
            this.progress = progress;
        if (this.progress >= achievement.getGoal()) {
            this.isCompleted = true;
        }
    }
}
