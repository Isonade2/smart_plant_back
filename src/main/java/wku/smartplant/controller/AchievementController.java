package wku.smartplant.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wku.smartplant.dto.ResponseDTO;
import wku.smartplant.dto.ResponseEntityBuilder;
import wku.smartplant.dto.achievement.AchievementResponseDTO;
import wku.smartplant.jwt.SecurityUtil;
import wku.smartplant.service.AchievementService;
import wku.smartplant.service.MemberService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class AchievementController {
    private final AchievementService achievementService;
    private final MemberService memberService;


    @GetMapping("/achievement")
    public ResponseEntity<ResponseDTO<List<AchievementResponseDTO>>> getAchievement() {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        List<AchievementResponseDTO> achievement = achievementService.getAchievement(currentMemberId);
        return ResponseEntityBuilder.build("업적 조회 성공", HttpStatus.OK, achievement);
    }
}
