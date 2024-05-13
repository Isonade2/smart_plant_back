package wku.smartplant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wku.smartplant.domain.RefreshToken;
import wku.smartplant.exception.ExpiredRefreshTokenException;
import wku.smartplant.jwt.JwtTokenUtil;
import wku.smartplant.repository.RefreshTokenRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public void saveRefreshToken(Long memberId, String token) {
        RefreshToken refreshToken = RefreshToken.builder().memberId(memberId).token(token).build();

        refreshTokenRepository.save(refreshToken);
    }

    public String getNewAccessToken(String memberId, String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new ExpiredRefreshTokenException("만료된 재발급"));

        return JwtTokenUtil.createAccessToken(memberId.toString(), 3600000);
    }

    public void deleteByMemberId(Long memberId) {
        refreshTokenRepository.deleteByMemberId(memberId);
    }

}
