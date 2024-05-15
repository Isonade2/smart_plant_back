package wku.smartplant.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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

    private Long exp;
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

    public void changeExp(Long exp) {
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
        this.exp = 0L;
        this.id = null;
        this.giveWater = false;
        this.activate = true;
    }

    public void addExp(Long exp) {
        this.exp += exp;
    }

}
