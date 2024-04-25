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
import wku.smartplant.repository.PlantRepository;
import wku.smartplant.service.MemberService;
import wku.smartplant.service.OrderService;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;


    @PostConstruct
    public void init() {
        initService.dbInit();
        initService.dbInit2();
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

        public void dbInit() { // 초기 더미데이터 생성. Member, Plant 엔티티 생성
            Member koalaMember = memberService.joinMember(MemberJoinRequest.builder().username("koala").email("koala@naver.com").password("a1234567").memberPlatform(MemberPlatform.GOOGLE).build());
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

            Item item1 = createItem("상추", 3000, 50);
            Item item2 = createItem("양파", 4000, 60);
            Item item3 = createItem("대파", 5000, 70);
            em.persist(item1);
            em.persist(item2);
            em.persist(item3);

            orderService.createOrderOne(1L, new OrderRequest(1L, 1));
            orderService.createOrderOne(1L, new OrderRequest(1L, 2));
            orderService.createOrderOne(1L, new OrderRequest(1L, 3));

        }

        public void dbInit2() { // 초기 더미데이터 생성, Item 엔티티 생성


        }


        public static Item createItem(String name, int price, int stockQuantity) {
            return Item.builder()
                    .name(name)
                    .price(price)
                    .stockQuantity(stockQuantity)
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
    }
}
