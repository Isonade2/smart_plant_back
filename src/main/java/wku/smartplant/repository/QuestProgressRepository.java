package wku.smartplant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wku.smartplant.domain.Member;
import wku.smartplant.domain.Quest;
import wku.smartplant.domain.QuestProgress;

import java.util.List;
import java.util.Optional;

public interface QuestProgressRepository extends JpaRepository<QuestProgress,Long> {
    boolean existsByMemberAndQuest(Member member, Quest quest);
    Optional<QuestProgress> findByMemberAndQuest(Member member, Quest quest);

    Optional<QuestProgress> findByMemberIdAndQuestId(Long memberId, Long questId);

    //멤버의 아이디가 일치하는 퀘스트정보들을 가져온다.
    List<QuestProgress> findAllByMemberId(Long memberId);
}
