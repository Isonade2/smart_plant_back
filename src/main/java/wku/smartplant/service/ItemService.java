package wku.smartplant.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wku.smartplant.domain.Item;
import wku.smartplant.exception.ItemNotFoundException;
import wku.smartplant.repository.ItemRepository;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;


    public void createItem(String name, int price, int stockQuantity){
        return;
    }

    // ItemService.java
    public Item getItem(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException("존재하지 않는 아이템입니다."));
    }
}
