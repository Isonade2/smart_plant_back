package wku.smartplant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wku.smartplant.domain.Plant;
import wku.smartplant.repository.PlantHistoryRepository;
import wku.smartplant.repository.PlantRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PlantService {

    private final PlantRepository plantRepository;
    private final PlantHistoryRepository plantHistoryRepository;

    public Long createPlant(Plant plant){
        plantRepository.save(plant);
        return plant.getId();
    }

    public Optional<Plant> findOne(Long id){
        Plant plant = plantRepository.findById(id).get();
        return Optional.of(plant);
    }


}
