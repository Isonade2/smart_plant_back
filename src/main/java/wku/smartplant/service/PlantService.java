package wku.smartplant.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wku.smartplant.domain.Member;
import wku.smartplant.domain.Plant;
import wku.smartplant.domain.PlantHistory;
import wku.smartplant.dto.plant.PlantDTO;
import wku.smartplant.dto.plant.PlantHistoryDTO;
import wku.smartplant.dto.plant.PlantRequestDTO;
import wku.smartplant.repository.MemberRepository;
import wku.smartplant.repository.PlantHistoryRepository;
import wku.smartplant.repository.PlantRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PlantService {

    private final PlantRepository plantRepository;
    private final PlantHistoryRepository plantHistoryRepository;
    private final MemberRepository memberRepository;

    public Long createPlant(PlantRequestDTO plantRequestDTO, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));

        Plant plant = plantRequestDTO.toEntity(member);
        log.info("plant: {}", plant);
        plantRepository.save(plant);
        return plant.getId();
    }

    public List<PlantDTO> getAllPlantsByMemberId(Long memberId) {
        List<Plant> plants = plantRepository.findAllByMemberId(memberId);

        return plants.stream()
                .map(PlantDTO::new)
                .collect(toList());
    }

    public PlantDTO findPlantById(Long id){
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("식물을 찾을 수 없습니다."));
        return new PlantDTO(plant);
    }


}
