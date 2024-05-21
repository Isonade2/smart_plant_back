package wku.smartplant.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@RequiredArgsConstructor
@Getter
public class Plant extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "plant_id")
    private Long id;
    private String name;
    private Integer level;
    private Integer exp;
    //테스트용
    @Setter
    private String uuid;
    private Boolean giveWater;
    private Boolean activate;

    @Enumerated(EnumType.STRING)
    private PlantType plantType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "plant", cascade = CascadeType.REMOVE)
    private List<PlantHistory> plantHistory = new ArrayList<>();

    public void changeExp(int exp) {
        this.exp = exp;
    }
    public void changeGiveWater(Boolean bool) {
        this.giveWater = bool;
    }

    public LocalDateTime getCreateDate() {
        return super.getCreatedDate();
    }
    @Builder
    public Plant(String name,Member member, PlantType plantType) {
        this.name = name;
        this.member = member;
        this.plantType = plantType;
        this.uuid = UUID.randomUUID().toString();
        this.exp = 0;
        this.level = 1;
        this.id = null;
        this.giveWater = false;
        this.activate = true;
    }

    public boolean addExpAndIsLevelUp(int exp) { //경험치 추가와 레벨업 했는지 확인
        this.exp += exp;
        if (this.exp >= 100) {
            if (level < 4)
            {
                level += 1;
                this.exp = this.exp - 100;
                return true; //레벨 업을 하였음
            }
        }
        return false; //레벨 업 x
    }


}
