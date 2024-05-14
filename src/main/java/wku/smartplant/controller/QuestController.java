package wku.smartplant.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wku.smartplant.dto.ResponseEntityBuilder;
import wku.smartplant.dto.quest.*;
import wku.smartplant.jwt.SecurityUtil;
import wku.smartplant.service.QuestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/quest")
public class QuestController {
    private final QuestService questService;

    @GetMapping("/weekly")
    public ResponseEntity<?> getWeeklyQuest(){
        log.info("getWeeklyQuest");
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        //Long currentMemberId = 52L;
        List<QuestListDTO> weeklyQuest = questService.getWeeklyQuest(currentMemberId);
        return ResponseEntityBuilder.build("주간 퀘스트 조회 성공", HttpStatus.OK, weeklyQuest);
    }

    @PostMapping("/weekly/accept")
    public ResponseEntity<?> acceptWeeklyQuest(@RequestBody QuestAcceptRequestDTO requestDTO){
        log.info("acceptWeeklyQuest questId : {}",requestDTO.getQuestId());
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
//        Long currentMemberId = 52L;

        QuestAcceptResponseDTO questAcceptResponse = questService.acceptQuest(requestDTO.getQuestId(), currentMemberId);
        return ResponseEntityBuilder.build("퀘스트 수락 성공", HttpStatus.OK, questAcceptResponse);
    }

    //퀘스트 완료 API
    @PostMapping("/weekly/complete")
    public ResponseEntity<?> completeWeeklyQuest(@RequestBody QuestCompleteRequestDTO requestDTO){
        log.info("completeWeeklyQuest questId : {}",requestDTO.getQuestId());
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        //Long currentMemberId = 52L;
        questService.completeQuest(requestDTO.getQuestId(), currentMemberId);
        return ResponseEntityBuilder.build("퀘스트 완료", HttpStatus.OK);
    }

}
