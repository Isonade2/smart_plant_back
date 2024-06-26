package wku.smartplant.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class RefreshToken {
    @Id @GeneratedValue
    @Column(name = "refresh_token_id")
    private Long id;

    private Long memberId;
    private String token;
}
