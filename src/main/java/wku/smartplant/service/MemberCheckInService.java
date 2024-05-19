package wku.smartplant.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wku.smartplant.domain.Member;
import wku.smartplant.domain.MemberCheckIn;
import wku.smartplant.repository.MemberCheckInRepository;
import wku.smartplant.repository.MemberRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberCheckInService {
    private final MemberRepository memberRepository;
    private final MemberCheckInRepository memberCheckInRepository;
    private final QuestService questService;

    public void checkIn(Long memberId) {
        log.info("checkIn memberId : {}", memberId);
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalStateException("회원을 찾을 수 없습니다."));

        MemberCheckIn memberCheckIn = new MemberCheckIn(member, LocalDateTime.now());

        //이미 출석했는지 확인
        if(memberCheckInRepository.existsByMemberId(memberId)){
            throw new IllegalStateException("이미 출석했습니다.");
        }

        memberCheckInRepository.save(memberCheckIn);
        questService.updateQuestProgress(memberId, 1L);

    }
    //매일 자정마다 초기화
    @Scheduled(cron = "0 0 0 * * *")
    public void resetDailyCheckIn(){
        log.info("resetDailyCheckIn");
        memberCheckInRepository.deleteAll();
    }

    //출석체크가 되었는지 확인
    public boolean ischeckIn(Long memberId) {
        boolean b = memberCheckInRepository.existsByMemberId(memberId);
        if (b) {
            return true;
        } else {
            return false;
        }
    }
}
