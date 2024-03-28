package wku.smartplant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wku.smartplant.repository.PlantHistoryRepository;
import wku.smartplant.repository.PlantRepository;

@Service
@RequiredArgsConstructor
public class PlantService {

    private final PlantRepository plantRepository;
    private final PlantHistoryRepository plantHistoryRepository;


}
