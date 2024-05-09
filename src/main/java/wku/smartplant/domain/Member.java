package wku.smartplant.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor()
@ToString(exclude = {"plants", "emailVerify"})
@Getter
public class Member extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotBlank
    private String username;

    @Column(unique = true)
    private String email;

    @NotBlank
    private String password;
    @NotBlank
    private String role;
    private Boolean activate; //이메일 인증 여부

    @Enumerated(EnumType.STRING)
    private MemberPlatform memberPlatform;

    @Embedded
    private Address address;

    @JsonIgnore @BatchSize(size = 50)
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Plant> plants = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "member")
    private EmailVerify emailVerify;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<QuestProgress> questProgresses = new ArrayList<>();



    public void changeEmailVerify(EmailVerify emailVerify) {
        this.emailVerify = emailVerify;
    }

    public void changeActivate(Boolean activate) {
        this.activate = activate;
    }

    public void changePassword(String password) { this.password = password; }

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
