package wku.smartplant.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wku.smartplant.dto.ResponseDTO;
import wku.smartplant.dto.ResponseEntityBuilder;
import wku.smartplant.dto.achievement.AchievementResponseDTO;
import wku.smartplant.jwt.SecurityUtil;
import wku.smartplant.service.AchievementService;
import wku.smartplant.service.MemberService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class AchievementController {
    private final AchievementService achievementService;
    private final MemberService memberService;


    @Operation(summary = "도전과제 목록 조회",
            description = "도전과제 목록을 조회한다." +
                    "Iscompleted : 퀘스트 완료 여부, false이면 미달성 도전과제"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "업적 조회 성공"),
            @ApiResponse(responseCode = "400", description = "멤버과 업적관련 오류입니다. 메시지에 에러이유 포함. 그 밖의 에러코드는 오류제어 못한 코드이므로 구현할때 물어봐주세요.")
    })
    @GetMapping("/achievement")
    public ResponseEntity<ResponseDTO<List<AchievementResponseDTO>>> getAchievement() {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        List<AchievementResponseDTO> achievement = achievementService.getAchievement(currentMemberId);
        return ResponseEntityBuilder.build("업적 조회 성공", HttpStatus.OK, achievement);
    }



    @Operation(summary = "(테스트용) 도전과제 완료",
            description = "특정 도전과제를 완료합니다." +
                    "헤더토큰과 완료할 퀘스트의 id를 json형식으로 보내주면 됩니다." +
                    "프론트에서 도전과제 테스트시에 사용하세요. {\n" +
                    "    \"target\" : 2(db참고하셔서 Achievement_id 입력해주시면 됩니다.)\n" +
                    "}"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도전과제를 달성 상태로 만듭니다."),

    })
    @PostMapping("/achievement/test")
    //도전과제id를 받음
    public ResponseEntity<ResponseDTO<String>> test(@RequestBody Map<String, Integer> targetMap) {
        int target = targetMap.get("target");
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        log.info("achievementId = {}",target);
        achievementService.updateMemberAchievement(currentMemberId,Long.valueOf(target),500);
        return ResponseEntityBuilder.build("(test용)진행도 업데이트", HttpStatus.OK, "test");
    }
}
