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

    @Enumerated(EnumType.STRING)
    private PlantType plantType;
    private String name;
    private int price;
    private int stockQuantity;

    @Builder
    public Item(String name, int price, int stockQuantity,PlantType plantType){
        this.id = null;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.plantType = plantType;
    }

    public void removeStock(int count){
        int restStock = this.stockQuantity - count;
        if(restStock < 0){
            throw new IllegalStateException("need more stock");
        }
        this.stockQuantity = restStock;
    }

    public void addStock(int count){
        this.stockQuantity += count;
    }
}
