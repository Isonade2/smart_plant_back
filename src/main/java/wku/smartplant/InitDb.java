package wku.smartplant;


import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import wku.smartplant.domain.*;
import wku.smartplant.dto.member.MemberJoinRequest;
import wku.smartplant.dto.order.OrderRequest;
import wku.smartplant.repository.MemberRepository;
import wku.smartplant.repository.PlantRepository;
import wku.smartplant.repository.QuestRepository;
import wku.smartplant.service.MemberService;
import wku.smartplant.service.OrderService;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitDb {
    private final InitService initService;


    @PostConstruct
    public void init() {
        log.info("초기 데이터 생성 시작");
        initService.dbInit();
        initService.dbInit2();
        log.info("초기 데이터 생성 완료");
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    @Slf4j
    public static class InitService {
        private final EntityManager em;
        private final OrderService orderService;
        private final MemberService memberService;
        private final PlantRepository plantRepository;
        private final MemberRepository memberRepository;
        private final QuestRepository questRepository;

        public void dbInit() { // 초기 더미데이터 생성. Member, Plant 엔티티 생성
            List<Member> allMember = memberRepository.findAll();
            if (!allMember.isEmpty()) {
                log.info("이미 데이터가 있으므로 데이터 생성을 하지않았음");
                return;
            }

            Member koalaMember = memberService.joinMember(MemberJoinRequest.builder().username("koala").email("koala@naver.com").password("a1234567").memberPlatform(MemberPlatform.GOOGLE).build());
            koalaMember.changeActivate(true);
            Plant savedPlant = plantRepository.save(new Plant("알라의 상추", koalaMember, PlantType.상추));
            log.info("첫번째 식물 uuid : {}", savedPlant.getUuid());
            plantRepository.save(new Plant("알라의 대파", koalaMember, PlantType.대파));
            plantRepository.save(new Plant("알라의 양파", koalaMember, PlantType.양파));
            Member manboMember = memberService.joinMember(MemberJoinRequest.builder().username("manbo").email("manbo@naver.com").password("a1234567").memberPlatform(MemberPlatform.GOOGLE).build());
            plantRepository.save(new Plant("만보의 무럭1", manboMember, PlantType.상추));
            plantRepository.save(new Plant("만보의 무럭2", manboMember, PlantType.상추));
            memberService.joinMember(MemberJoinRequest.builder().username("peach").email("peach@google.com").password("a1234567").memberPlatform(MemberPlatform.GOOGLE).build());


//            Plant plant1 = createPlant("모람이", PlantType.대파, member1);
//            Plant plant2 = createPlant("이모람", PlantType.상추, member1);
//
//            Plant plant3 = createPlant("피카추", PlantType.상추, member2);
//            Plant plant4 = createPlant("피카모", PlantType.양파, member2);
//
//            Plant plant5 = createPlant("메타몽", PlantType.대파, member3);
//            em.persist(plant1); em.persist(plant2); em.persist(plant3); em.persist(plant4); em.persist(plant5);

            Item item1 = createItem("상추", 3000, 50,PlantType.상추);
            Item item2 = createItem("양파", 4000, 60,PlantType.양파);
            Item item3 = createItem("대파", 5000, 70,PlantType.대파);
            Item item4 = createItem("딸기", 10000, 70,PlantType.딸기);
            em.persist(item1);
            em.persist(item2);
            em.persist(item3);
            em.persist(item4);

//            orderService.createOrderOne(1L, new OrderRequest(1L, 1));
//            orderService.createOrderOne(1L, new OrderRequest(1L, 2));
//            orderService.createOrderOne(1L, new OrderRequest(1L, 3));

        }

        public void dbInit2() { // 초기 더미데이터 생성, Item 엔티티 생성
            List<Quest> all = questRepository.findAll();

            if(!all.isEmpty()){
                log.info(all.toString());
                log.info("이미 데이터가 있으므로 데이터 생성을 하지않았음");
                return;
            }
            Quest quest1 = createQuest("출석체크", "기간 내에 3번 이상 출석하세요.", 15, 3);
            Quest quest2 = createQuest("수분 공급", "기간 내에 무선 물주기 기능 1번 사용해보세요.", 15, 1);
            Quest quest3 = createQuest("식물과 대화하기", "AI챗봇 기능을 이용해 식물과 대화해보세요.", 15, 1);
            Quest quest4 = createQuest("물병 채우기", "식물의 물병을 새로 리필해주세요.", 15, 1);
            Quest quest5 = createQuest("질병 체크", "질병 확인 기능을 이용하여 식물의 이상을 확인해보세요.", 15, 1);
            em.persist(quest1);
            em.persist(quest2);
            em.persist(quest3);
            em.persist(quest4);
            em.persist(quest5);
            Achievement achievement1 = createAchievement("물 뿌리개", "물 주기 기능 5번 이상 사용", 5,"누적");
            Achievement achievement2 = createAchievement("뽀빠이", "최고 레벨 달성",300, "달성");
            Achievement achievement3 = createAchievement("수다왕", "식물과 5번 이상 대화",5,"누적");
            Achievement achievement4 = createAchievement("선인장", "일정 습도 달성", 1,"달성");
            Achievement achievement5 = createAchievement("개근상", "일주일 동안 빠지지않고 출석",3,"누적");
            Achievement achievement6 = createAchievement("소울메이트", "식물과 일정 애정도 이상 달성",360,"달성");
            em.persist(achievement1);
            em.persist(achievement2);
            em.persist(achievement3);
            em.persist(achievement4);
            em.persist(achievement5);
            em.persist(achievement6);
        }


        public static Item createItem(String name, int price, int stockQuantity,PlantType plantType) {
            return Item.builder()
                    .name(name)
                    .price(price)
                    .stockQuantity(stockQuantity)
                    .plantType(plantType)
                    .build();
        }

        private static Plant createPlant(String name, PlantType plantType, Member member) {
            return Plant.builder()
                    .name(name)
                    .plantType(plantType)
                    .member(member)
                    .build();
        }

        private static Member createMember(String username, String email, String password,
                                           MemberPlatform memberPlatform, Address address) {
            return Member.builder()
                    .username(username)
                    .email(email)
                    .password(password)
                    .memberPlatform(memberPlatform)
                    .activate(false)
                    .address(address)
                    .build();
        }

        public static Quest createQuest(String title, String description, int reward, int goal) {
            return Quest.builder()
                    .title(title)
                    .description(description)
                    .reward(reward)
                    .goal(goal)
                    .build();
        }

        public static Achievement createAchievement(String title, String description, int goal,String type) {
            return Achievement.builder()
                    .title(title)
                    .description(description)
                    .goal(goal)
                    .type(type)
                    .build();
        }
    }
}
