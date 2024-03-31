package wku.smartplant.dto.item;

import lombok.Data;
import wku.smartplant.domain.Item;

@Data
public class ItemDTO {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    public ItemDTO() {
    }

    public ItemDTO(Long id, String name, int price, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public ItemDTO(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
    }
}
