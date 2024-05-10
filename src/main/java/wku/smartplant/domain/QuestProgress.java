package wku.smartplant.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Getter
public class QuestProgress {
    @Id
    @GeneratedValue
    @Column(name = "quest_progress_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
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
        this.progress = progress;
        this.completed = quest.isQuestCompleted(progress);
    }

    public void checkCompleted() {
        this.completed = quest.isQuestCompleted(progress);
    }

    public QuestProgress createQuestProgress(Member member, Quest quest) {
        return QuestProgress.builder()
                .member(member)
                .quest(quest)
                .progress(0)
                .completed(false)
                .build();
    }
}
