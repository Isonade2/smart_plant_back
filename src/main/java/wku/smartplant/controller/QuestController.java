package wku.smartplant.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wku.smartplant.dto.ResponseEntityBuilder;
import wku.smartplant.dto.quest.QuestAcceptRequestDTO;
import wku.smartplant.dto.quest.QuestAcceptResponseDTO;
import wku.smartplant.jwt.SecurityUtil;
import wku.smartplant.service.QuestService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/quest")
public class QuestController {
    private final QuestService questService;

    @GetMapping("/weekly")
    public ResponseEntity<?> getWeeklyQuest(){
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        return null;
    }

    @PostMapping("/weekly/accept")
    public ResponseEntity<?> acceptWeeklyQuest(@RequestBody QuestAcceptRequestDTO requestDTO){
        log.info("acceptWeeklyQuest questId : {}",requestDTO.getQuestId());
        //Long currentMemberId = SecurityUtil.getCurrentMemberId();
        Long currentMemberId = 52L;

        QuestAcceptResponseDTO questAcceptResponse = questService.acceptQuest(requestDTO.getQuestId(), currentMemberId);
        return ResponseEntityBuilder.build("퀘스트 수락 성공", HttpStatus.OK, questAcceptResponse);
    }
}
