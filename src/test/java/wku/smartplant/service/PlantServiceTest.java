package wku.smartplant.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import wku.smartplant.domain.Member;
import wku.smartplant.domain.Plant;
import wku.smartplant.domain.PlantType;
import wku.smartplant.dto.plant.PlantRequestDTO;
import wku.smartplant.repository.MemberRepository;
import wku.smartplant.repository.PlantRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class PlantServiceTest {
    @Autowired
    private PlantService plantService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PlantRepository plantRepository;

    @Test
    public void 회원가입(){
        //given
        PlantRequestDTO plantRequestDTO = new PlantRequestDTO(PlantType.대파, "대파파");
        Member member = memberRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        Plant plant = plantRequestDTO.toEntity(member);
        plantRepository.save(plant);

        //when
        //then
    }

}