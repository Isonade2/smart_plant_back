package wku.smartplant.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wku.smartplant.dto.ResponseDTO;
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
        achievementService.getAchievement(currentMemberId);
        return build(achievementList, OK);
    }


    @PostMapping("/achievement")
    public ResponseEntity<ResponseDTO<?>> achievement(@RequestBody AchievementRequest achievementRequest) {
        Member member = memberService.getMember(SecurityUtil.getCurrentMemberId());
        achievementService.achievement(member.getId(), achievementRequest.getAchievementId());
        return build("성공", OK);
    }


}
