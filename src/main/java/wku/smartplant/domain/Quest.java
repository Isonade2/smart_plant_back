package wku.smartplant.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@RequiredArgsConstructor
@Getter
@ToString
public class Quest {
    @Id
    @GeneratedValue()
    @Column(name = "quest_id")
    private Long id;
    private String title;
    private String description;
    private int reward;
    private int goal;


    @Builder
    public Quest(String title, String description, int reward, int goal) {
        this.id = null;
        this.title = title;
        this.description = description;
        this.reward = reward;
        this.goal = goal;

    }

    public boolean isQuestCompleted(int progress) {
        return progress >= goal;
    }
}
