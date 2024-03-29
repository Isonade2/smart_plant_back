package wku.smartplant;


import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import wku.smartplant.domain.*;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    public static class InitService{
        private final EntityManager em;

        public void dbInit(){ // 초기 더미데이터 생성. Member, Plant 엔티티 생성
            Member member1 = createMember("António Lee", "c1004sos@naver.com", "rHnxrDID3A", MemberType.LOCAL, new Address("서울", "무왕로5길", "53224", "104-607"));
            Member member2 = createMember("Sani Raut", "playgm1@naver.com", "tp3LKmPXI", MemberType.LOCAL, new Address("익산", "부송1로", "54556", "101-503"));
            Member member3 = createMember("Qing Ye", "playgm1@naver.com", "9G9m3YPX7", MemberType.LOCAL, new Address("서울", "한강로3가", "13224", "202-201"));
            em.persist(member1);
            em.persist(member2);
            em.persist(member3);

            Plant plant1 = createPlant("모람이", PlantType.대파, member1);
            Plant plant2 = createPlant("이모람", PlantType.상추, member1);

            Plant plant3 = createPlant("피카추", PlantType.상추, member2);
            Plant plant4 = createPlant("피카모", PlantType.양파, member2);

            Plant plant5 = createPlant("메타몽", PlantType.대파, member3);
            em.persist(plant1); em.persist(plant2); em.persist(plant3); em.persist(plant4); em.persist(plant5);

        }

        public void dbInit2(){ // 초기 더미데이터 생성, Order 엔티티
            
        }

        private static Plant createPlant(String name, PlantType plantType, Member member) {
            return Plant.builder()
                    .name(name)
                    .plantType(plantType)
                    .member(member)
                    .build();
        }

        private static Member createMember(String username, String email, String password,
                                         MemberType memberType, Address address) {
            return Member.builder()
                    .username(username)
                    .email(email)
                    .password(password)
                    .memberType(memberType)
                    .address(address)
                    .build();
        }
    }
}
