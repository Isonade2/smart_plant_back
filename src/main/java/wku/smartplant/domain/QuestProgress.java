package wku.smartplant.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Getter
public class QuestProgress {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "quest_id")
    private Quest quest;
    private int progress;
    private boolean completed;


}
