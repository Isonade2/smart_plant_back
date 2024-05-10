package wku.smartplant.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wku.smartplant.domain.Member;
import wku.smartplant.domain.Quest;
import wku.smartplant.domain.QuestProgress;
import wku.smartplant.repository.MemberRepository;
import wku.smartplant.repository.QuestProgressRepository;
import wku.smartplant.repository.QuestRepository;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QuestService {
    private final QuestRepository questRepository;
    private final MemberRepository memberRepository;
    private final QuestProgressRepository questProgressRepository;


    public void acceptQuest(Long questId, Long memberId){
        // 퀘스트를 수락하는 로직
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));
        Quest quest = questRepository.findById(questId).orElseThrow(() -> new IllegalArgumentException("해당 퀘스트가 존재하지 않습니다."));

        // 퀘스트 진행 정보를 생성
        QuestProgress questProgress = QuestProgress.builder()
                .member(member)
                .quest(quest)
                .progress(0)
                .completed(false)
                .build();
        questProgressRepository.save(questProgress);
    }
}
