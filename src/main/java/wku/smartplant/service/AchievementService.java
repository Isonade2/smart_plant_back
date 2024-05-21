package wku.smartplant.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wku.smartplant.domain.Achievement;
import wku.smartplant.domain.Member;
import wku.smartplant.domain.MemberAchievement;
import wku.smartplant.dto.achievement.AchievementResponseDTO;
import wku.smartplant.repository.AchievementRepository;
import wku.smartplant.repository.MemberAchievementRepository;
import wku.smartplant.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final MemberAchievementRepository memberAchievementRepository;
    private final MemberRepository memberRepository;

    public List<AchievementResponseDTO> getAchievement(Long memberId) {
        //멤버의 업적 정보를 가져온다.
        List<MemberAchievement> memberAchievements = memberAchievementRepository.findAllByMemberId(memberId).orElseGet(() -> {
            log.warn("해당 멤버의 업적 정보가 없습니다.");

            return null;
        });
        log.info("memberAchievements : {}", memberAchievements.toString());

        //모든 업적 정보를 가져온다.
        List<Achievement> achievements = achievementRepository.findAll();
        log.info("achievements : {}", achievements.toString());

        //멤버의 업적 정보와 모든 업적 정보를 비교해서 업적 정보를 만든다.
        List<AchievementResponseDTO> achievementList = createAchievementList(memberAchievements, achievements);
        achievementList.forEach(achievement -> log.info("achievement : {}", achievement.toString()));

        return achievementList;
    }


    private List<AchievementResponseDTO> createAchievementList(List<MemberAchievement> memberAchievements, List<Achievement> achievements) {
        List<AchievementResponseDTO> achievementList = new ArrayList<>();
        for(Achievement achievement : achievements){
            boolean isCompleted = false;
            int progress = 0;
            for(MemberAchievement memberAchievement : memberAchievements){
                if(achievement.getId().equals(memberAchievement.getAchievement().getId())){
                    isCompleted = memberAchievement.isCompleted();
                    progress = memberAchievement.getProgress();
                    break;
                }
            }
            achievementList.add(AchievementResponseDTO.builder()
                    .AchievementId(achievement.getId())
                    .title(achievement.getTitle())
                    .description(achievement.getDescription())
                    .goal(achievement.getGoal())
                    .progress(progress)
                    .isCompleted(isCompleted)
                    .build());
        }
        return achievementList;
    }


    public void updateMemberAchievement(Long memberId, Long achievementId, int progress) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("해당 멤버가 없습니다."));
        Achievement achievement = achievementRepository.findById(achievementId).orElseThrow(() -> new EntityNotFoundException("해당 업적이 없습니다."));
        MemberAchievement memberAchievement =
                memberAchievementRepository.findByMemberIdAndAchievementId(memberId, achievementId)
                                .orElseGet(() -> {
            log.warn("해당 멤버의 업적 정보가 없습니다.");

            MemberAchievement newMemberAchievement = MemberAchievement.builder()
                    .member(member)
                    .achievement(achievement)
                    .progress(0)
                    .isCompleted(false)
                    .build();
            return memberAchievementRepository.save(newMemberAchievement);

        });
        memberAchievement.updateProgress(memberAchievement.getAchievement().getType(),progress); //업적 정보 업데이트
    }


}
