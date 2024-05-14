package wku.smartplant.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wku.smartplant.dto.ResponseDTO;
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

    @Operation(summary = "주간 퀘스트 조회",
            description = "주간 퀘스트 목록을 조회한다." +
                    "accept : 퀘스트 수락여부, completed : 퀘스트 완료 여부"
    )
    @GetMapping("/weekly")
    public ResponseEntity<ResponseDTO<List<QuestListDTO>>> getWeeklyQuest(){
        log.info("getWeeklyQuest");
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        //Long currentMemberId = 52L;
        List<QuestListDTO> weeklyQuest = questService.getWeeklyQuest(currentMemberId);
        return ResponseEntityBuilder.build("주간 퀘스트 조회 성공", HttpStatus.OK, weeklyQuest);
    }


    @Operation(summary = "퀘스트 수락",
            description = "주간 퀘스트를 수락한다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "퀘스트 수락 성공"),
    })
    @PostMapping("/weekly/accept")
    public ResponseEntity<ResponseDTO<QuestAcceptResponseDTO>> acceptWeeklyQuest(@RequestBody QuestAcceptRequestDTO requestDTO){
        log.info("acceptWeeklyQuest questId : {}",requestDTO.getQuestId());
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
//        Long currentMemberId = 52L;

        QuestAcceptResponseDTO questAcceptResponse = questService.acceptQuest(requestDTO.getQuestId(), currentMemberId);
        return ResponseEntityBuilder.build("퀘스트 수락 성공", HttpStatus.OK, questAcceptResponse);
    }

    //퀘스트 완료 API
    @Operation(summary = "퀘스트 완료",
            description = "주간 퀘스트를 완료한다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "퀘스트 완료 성공"),
            @ApiResponse(responseCode = "400", description = "퀘스트 완료 실패")
    })
    @PostMapping("/weekly/complete")
    public ResponseEntity<ResponseDTO<?>> completeWeeklyQuest(@RequestBody QuestCompleteRequestDTO requestDTO){
        log.info("completeWeeklyQuest questId : {}",requestDTO.getQuestId());
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        //Long currentMemberId = 52L;
        questService.completeQuest(requestDTO.getQuestId(), currentMemberId);
        return ResponseEntityBuilder.build("퀘스트 완료", HttpStatus.OK);
    }

}
