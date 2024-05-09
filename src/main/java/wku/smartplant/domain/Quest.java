package wku.smartplant.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
    private String reward;
    private int goal;


    public boolean isQuestCompleted(int progress) {
        return progress >= goal;
    }
}
