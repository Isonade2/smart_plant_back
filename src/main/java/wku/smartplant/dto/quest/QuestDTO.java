package wku.smartplant.dto.quest;

import lombok.Builder;
import lombok.Data;

@Data
public class QuestDTO {
    private Long questId;
    private String title;
    private String description;
    private int reward;
    private int goal;

//    public QuestDTO(Long questId, String title, String description, int reward, int goal) {
//        this.questId = questId;
//        this.title = title;
//        this.description = description;
//        this.reward = reward;
//        this.goal = goal;
//    }


    @Builder
    public QuestDTO(Long questId, String title, String description, int reward, int goal) {
        this.questId = questId;
        this.title = title;
        this.description = description;
        this.reward = reward;
        this.goal = goal;
    }

}
