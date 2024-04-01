package wku.smartplant.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor()
@EntityListeners(AuditingEntityListener.class)
@ToString(exclude = {"plants", "emailVerify"})
@Getter
public class Member extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;
    private String email;
    private String password;
    private String role;
    private Boolean activate; //이메일 인증 여부

    @Enumerated(EnumType.STRING)
    private MemberPlatform memberPlatform;

    @Embedded
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Plant> plants = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "member")
    private EmailVerify emailVerify;

    public void changeEmailVerify(EmailVerify emailVerify) {
        this.emailVerify = emailVerify;
    }

    public void changeActivate(Boolean activate) {
        this.activate = activate;
    }

    @Builder
    public Member(String username, String email, String password, Address address, MemberPlatform memberPlatform, Boolean activate){
        this.id = null;
        this.plants = null;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = "USER";
        this.address = address;
        this.memberPlatform = memberPlatform;
        this.activate = activate;
    }
}
