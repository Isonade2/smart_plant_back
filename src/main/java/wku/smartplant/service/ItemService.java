package wku.smartplant.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wku.smartplant.repository.ItemRepository;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;


    public void createItem(String name, int price, int stockQuantity){
        return;
    }
}
