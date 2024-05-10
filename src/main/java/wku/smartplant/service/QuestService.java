package wku.smartplant.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wku.smartplant.domain.Member;
import wku.smartplant.domain.Quest;
import wku.smartplant.domain.QuestProgress;
import wku.smartplant.dto.quest.QuestAcceptResponseDTO;
import wku.smartplant.dto.quest.QuestDTO;
import wku.smartplant.repository.MemberRepository;
import wku.smartplant.repository.QuestProgressRepository;
import wku.smartplant.repository.QuestRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QuestService {
    private final QuestRepository questRepository;
    private final MemberRepository memberRepository;
    private final QuestProgressRepository questProgressRepository;




    public List<QuestDTO> getWeeklyQuest(){
        List<Quest> quests = questRepository.findAll();

        return quests.stream().map(quest -> QuestDTO.builder()
                .questId(quest.getId())
                .title(quest.getTitle())
                .description(quest.getDescription())
                .reward(quest.getReward())
                .goal(quest.getGoal())
                .build()).toList();
    }


    // 퀘스트를 수락하는 로직
    public QuestAcceptResponseDTO acceptQuest(Long questId, Long memberId){

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));
        Quest quest = questRepository.findById(questId).orElseThrow(() -> new IllegalArgumentException("해당 퀘스트가 존재하지 않습니다."));
        //이미 퀘스트를 수락했다면
        if(questProgressRepository.existsByMemberAndQuest(member, quest)){
            throw new IllegalArgumentException("이미 수락한 퀘스트입니다.");
        }


        // 퀘스트 진행 정보를 생성
        QuestProgress questProgress = QuestProgress.builder()
                .member(member)
                .quest(quest)
                .progress(0)
                .completed(false)
                .build();
        QuestProgress progress = questProgressRepository.save(questProgress);

        QuestAcceptResponseDTO questResponse = createQuestResponse(progress, quest);
        return questResponse;
    }

    // 퀘스트를 완료하는 로직
    public void completeQuest(Long questId, Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));
        Quest quest = questRepository.findById(questId).orElseThrow(() -> new IllegalArgumentException("해당 퀘스트가 존재하지 않습니다."));
        QuestProgress questProgress = questProgressRepository.findByMemberAndQuest(member, quest).orElseThrow(() -> new IllegalArgumentException("해당 퀘스트 진행 정보가 존재하지 않습니다."));

        //퀘스트가 이미 완료됐는지?
        if (questProgress.isCompleted()) {
            throw new IllegalArgumentException("이미 완료한 퀘스트입니다.");
        }

        if(questProgress.checkCompleted()){
            log.info("퀘스트 완료");
        }

    }

    private QuestAcceptResponseDTO createQuestResponse(QuestProgress progress, Quest quest) {
        return QuestAcceptResponseDTO.builder()
                .questId(progress.getId())
                .title(quest.getTitle())
                .description(quest.getDescription())
                .reward(quest.getReward())
                .goal(quest.getGoal())
                .progress(progress.getProgress())
                .completed(progress.isCompleted())
                .build();
    }
}
