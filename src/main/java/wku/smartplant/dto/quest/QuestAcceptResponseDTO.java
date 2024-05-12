package wku.smartplant.dto.quest;

import lombok.Builder;
import lombok.Data;

@Data
public class QuestAcceptResponseDTO {
    private Long questId;
    private String title;
    private String description;
    private int reward;
    private int goal;
    private int progress;
    private boolean completed;

    @Builder
    public QuestAcceptResponseDTO(Long questId, String title, String description, int reward, int goal, int progress, boolean completed) {
        this.questId = questId;
        this.title = title;
        this.description = description;
        this.reward = reward;
        this.goal = goal;
        this.progress = progress;
        this.completed = completed;
    }

}
