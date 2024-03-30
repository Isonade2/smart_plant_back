package wku.smartplant.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @Builder
    public Item(String name, int price, int stockQuantity){
        this.id = null;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public void removeStock(int count){
        int restStock = this.stockQuantity - count;
        if(restStock < 0){
            throw new IllegalStateException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
