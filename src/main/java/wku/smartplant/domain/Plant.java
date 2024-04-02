package wku.smartplant.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@RequiredArgsConstructor
@Getter
public class Plant extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "plant_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private PlantType plantType;

    private Long exp;
    private String uuid;
    private Boolean activate;

    @OneToMany(mappedBy = "plant")
    private List<PlantHistory> plantHistory = new ArrayList<>();

    @Builder
    public Plant(String name,Member member, PlantType plantType) {
        this.id = null;
        this.name = name;
        this.member = member;
        this.plantType = plantType;
        this.exp = 0L;
    }

}
