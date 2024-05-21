package wku.smartplant.dto.achievement;

import lombok.Builder;
import lombok.Data;

@Data

public class AchievementResponseDTO {
    private Long achievementId;
    private String title;
    private String description;
    private int goal;
    private int progress;
    private boolean isCompleted = false;


    @Builder
    public AchievementResponseDTO(Long AchievementId, String title, String description, int goal,int progress, boolean isCompleted) {
        this.achievementId = AchievementId;
        this.title = title;
        this.description = description;
        this.goal = goal;
        this.progress = progress;
        this.isCompleted = isCompleted;
    }
}
