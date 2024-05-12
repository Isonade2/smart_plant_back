package wku.smartplant.dto.quest;

import lombok.Builder;
import lombok.Data;

@Data
public class QuestListDTO {
    private boolean isAccepted = false;
    private boolean isCompleted = false;
    private Long questId;
    private String title;
    private String description;
    private int reward;
    private int goal;
    private int progress;

    @Builder
    public QuestListDTO(boolean isAccepted, boolean isCompleted, Long questId, String title, String description, int reward, int goal, int progress) {
        this.isAccepted = isAccepted;
        this.isCompleted = isCompleted;
        this.questId = questId;
        this.title = title;
        this.description = description;
        this.reward = reward;
        this.goal = goal;
        this.progress = progress;
    }


}
