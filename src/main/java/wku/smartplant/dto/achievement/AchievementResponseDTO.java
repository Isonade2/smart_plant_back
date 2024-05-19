package wku.smartplant.dto.achievement;

import lombok.Builder;
import lombok.Data;

@Data

public class AchievementResponseDTO {
    private Long AchievementId;
    private String title;
    private String description;
    private int goal;
    private boolean isCompleted = false;


    @Builder
    public AchievementResponseDTO(Long AchievementId, String title, String description, int goal, boolean isCompleted) {
        this.AchievementId = AchievementId;
        this.title = title;
        this.description = description;
        this.goal = goal;
        this.isCompleted = isCompleted;
    }
}
