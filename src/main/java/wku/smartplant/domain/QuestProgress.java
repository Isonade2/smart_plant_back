package wku.smartplant.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@RequiredArgsConstructor
@Getter
@ToString(exclude = {"quest", "member"})
public class QuestProgress {
    @Id
    @GeneratedValue
    @Column(name = "quest_progress_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_id")
    private Quest quest;
    private int progress;
    private boolean completed;

    @Builder
    public QuestProgress(Member member, Quest quest, int progress, boolean completed) {
        this.id = null;
        this.member = member;
        this.quest = quest;
        this.progress = progress;
        this.completed = completed;
    }

    public void updateProgress(int progress) {
        this.progress += progress;
    }

    public boolean checkCompleted() {
        if(quest.isQuestCompleted(progress)) {
            this.completed = true;
            return true;
        }else {
            return false;
        }
    }

    public void changeCompleted(boolean completed) {
        this.completed = completed;
    }

    public QuestProgress createQuestProgress(Member member, Quest quest) {
        return QuestProgress.builder()
                .member(member)
                .quest(quest)
                .progress(0)
                .completed(false)
                .build();
    }

    public boolean isCanComplete(){
        return progress == quest.getGoal();
    }
}
