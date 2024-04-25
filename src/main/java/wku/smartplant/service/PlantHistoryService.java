package wku.smartplant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wku.smartplant.domain.Plant;
import wku.smartplant.domain.PlantHistory;
import wku.smartplant.dto.plant.PlantHistoryDTO;
import wku.smartplant.repository.PlantHistoryRepository;
import wku.smartplant.repository.PlantRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlantHistoryService {

    private final PlantRepository plantRepository;
    private final PlantHistoryRepository plantHistoryRepository;


    public Page<PlantHistoryDTO> findAllHistoryByPlantId(Long memberId, Long plantId, Pageable pageable) {
        Page<PlantHistory> plantHistoryPage = plantHistoryRepository.findByPlantIdAndMemberId(plantId, memberId, pageable);
        return plantHistoryPage.map(PlantHistoryDTO::new);
    }

    public List<PlantHistoryDTO> findAllHistory( ) {
        List<PlantHistory> plantHistoryList = plantHistoryRepository.findAll();
        return plantHistoryList.stream().map(PlantHistoryDTO::new).collect(Collectors.toList());
    }
}
