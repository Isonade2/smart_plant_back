package wku.smartplant.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wku.smartplant.domain.*;
import wku.smartplant.dto.quest.QuestAcceptResponseDTO;
import wku.smartplant.dto.quest.QuestDTO;
import wku.smartplant.dto.quest.QuestListDTO;
import wku.smartplant.repository.MemberRepository;
import wku.smartplant.repository.PlantRepository;
import wku.smartplant.repository.QuestProgressRepository;
import wku.smartplant.repository.QuestRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QuestService {
    private final QuestRepository questRepository;
    private final MemberRepository memberRepository;
    private final QuestProgressRepository questProgressRepository;
    private final PlantRepository plantRepository;
    private final NotificationService notificationService;


    //주마다 퀘스트 진행도를 초기화 한다.
    @Scheduled(cron = "0 0 0 * * MON")
    public void resetWeeklyQuests(){
        List<QuestProgress> QuestProgresses = questProgressRepository.findAll();
        //모두 삭제
        questProgressRepository.deleteAll(QuestProgresses);
    }

    public List<QuestListDTO> getWeeklyQuest(Long memberId){
        //퀘스트 목록을 불러온다.
        List<Quest> quests = questRepository.findAll();
        log.info("quests : {}", quests.toString());

        //현재 멤버의 퀘스트 진행 정보를 가져온다.
        List<QuestProgress> questProgresses = questProgressRepository.findAllByMemberId(memberId);
        log.warn("questProgresses : {}", questProgresses.toString());

        //퀘스트 목록을 불러오는데 그 퀘스트가 QuestProgress에 있는지 확인한다.
        //Quests와 QuestProgresses를 비교해서 QuestProgress에 있는 퀘스트는 제외한 QuestListDTO를 만든다.
        List<QuestListDTO> questListDTO = createQuestListDTO(quests, questProgresses);
        questListDTO.forEach(quest -> log.info("quest : {}", quest.toString()));

        return questListDTO;
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
            //멤버의 모든 식물을 불러온다.

            List<Plant> Plants = plantRepository.findAllByMemberId(memberId);
            if (Plants.isEmpty()) {
                throw new IllegalArgumentException("식물이 존재하지 않습니다.");
            }
            for (Plant plant : Plants) {
                boolean isLevelUp = plant.addExpAndIsLevelUp(quest.getReward());
                if (isLevelUp) { //레벨 업을 하였을 경우 알림 추가
                    notificationService.createNotification(memberId, plant.getName() + " 식물이 레벨 업을 하였습니다! 축하합니다!", "home", NotificationType.레벨업);
                }
            }
        }
        else{
            throw new IllegalArgumentException("퀘스트 완료 조건을 만족하지 못했습니다.");
        }
    }

    //퀘스트 진행도를 업데이트 한다.
    public void updateQuestProgress(Long memberId,Long questId){
        QuestProgress questProgress = questProgressRepository.findByMemberIdAndQuestId(memberId, questId).orElseGet(() -> {
            log.info("퀘스트 진행 정보가 없습니다.");
            return null;
        });
        if(questProgress == null){
            return;
        }
        log.info("퀘스트 진행정보가 있음");
        if (questProgress != null) {
            questProgress.updateProgress(1);
        }
        //퀘스트 목표를 달성했다면
        if(questProgress.isCanComplete()){
            log.info("퀘스트 완료 가능");

            notificationService.createNotification(memberId, "완료된 퀘스트가 있습니다.", null, NotificationType.퀘스트);
            //알림 생성
        }
    }


    // 퀘스트목록을 불러온다. QuestProgress에 있는 퀘스트에 같은 퀘스트가 있다면 QuestProgress에 있는 퀘스트로 QuestListDTO를 만든다.
    private List<QuestListDTO> createQuestListDTO(List<Quest> quests, List<QuestProgress> questProgresses) {
        Map<Long, QuestProgress> questProgressMap = new HashMap<>();
        for (QuestProgress questProgress : questProgresses) {
            questProgressMap.put(questProgress.getQuest().getId(), questProgress);
        }

        List<QuestListDTO> questList = new ArrayList<>();
        for (Quest quest : quests) {
            QuestProgress questProgress = questProgressMap.get(quest.getId());
            if (questProgress == null) {
                questList.add(QuestListDTO.builder()
                        .isAccepted(false)
                        .isCompleted(false)
                        .questId(quest.getId())
                        .title(quest.getTitle())
                        .description(quest.getDescription())
                        .reward(quest.getReward())
                        .goal(quest.getGoal())
                        .progress(0)
                        .build());
            } else {
                questList.add(QuestListDTO.builder()
                        .isAccepted(true)
                        .isCompleted(questProgress.isCompleted())
                        .questId(quest.getId())
                        .title(quest.getTitle())
                        .description(quest.getDescription())
                        .reward(quest.getReward())
                        .goal(quest.getGoal())
                        .progress(questProgress.getProgress())
                        .build());
            }
        }
        return questList;
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
