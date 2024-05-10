package wku.smartplant.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Getter
public class Quest {
    @Id
    @GeneratedValue
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
