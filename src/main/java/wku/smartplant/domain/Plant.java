package wku.smartplant.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
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
