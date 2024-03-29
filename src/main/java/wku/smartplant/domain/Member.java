package wku.smartplant.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor()
@EntityListeners(AuditingEntityListener.class)
@ToString(exclude = {"id","username","email","password","role","address"})
@Getter
public class Member extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Plant> plants = new ArrayList<>();

    private String username;
    private String email;
    private String password;
    private String role;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @Embedded
    private Address address;

    @Builder
    public Member(String username, String email, String password, Address address) {
        this.id = null;
        this.plants = null;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = "USER";
        this.address = address;
        this.memberType = memberType;
    }
}
