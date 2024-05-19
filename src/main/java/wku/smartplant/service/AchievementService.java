package wku.smartplant.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wku.smartplant.dto.achievement.AchievementResponseDTO;
import wku.smartplant.repository.AchievementRepository;
import wku.smartplant.repository.MemberAchievementRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final MemberAchievementRepository memberAchievementRepository;

    public List<AchievementResponseDTO> getAchievement(Long memberId) {
        return null;
    }
}
