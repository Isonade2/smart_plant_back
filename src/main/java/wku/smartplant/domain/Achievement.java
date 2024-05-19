package wku.smartplant.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@RequiredArgsConstructor
@Getter
@ToString
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private int goal;


    @Builder
    public Achievement(String title, String description, int goal) {
        this.id = null;
        this.title = title;
        this.description = description;
        this.goal = goal;
    }
}
