package wku.smartplant.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wku.smartplant.config.JwtTokenUtil;
import wku.smartplant.domain.Member;
import wku.smartplant.dto.member.MemberJoinRequest;
import wku.smartplant.dto.member.MemberLoginRequest;
import wku.smartplant.dto.member.MemberLoginResponse;
import wku.smartplant.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Value("${jwt.secret}")
    private String secretKey;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Transactional
    public Member joinMember(MemberJoinRequest memberJoinRequest) {
        memberJoinRequest.setPassword(passwordEncoder.encode(memberJoinRequest.getPassword()));
        return memberRepository.save(memberJoinRequest.toEntity());
    }

    @Transactional
    public MemberLoginResponse loginMember(MemberLoginRequest memberLoginRequest) {
        Member findMember = memberRepository.findByEmail(memberLoginRequest.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));
        if (!passwordEncoder.matches(memberLoginRequest.getPassword(), findMember.getPassword())) {
            return MemberLoginResponse.builder()
                    .build();
        }

        String token = JwtTokenUtil.createToken(findMember.getId().toString(),  10000);

        return MemberLoginResponse.builder()
                .email(findMember.getEmail())
                .username(findMember.getUsername())
                .token(token).build();

    }
    @Transactional
    public Member findMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));
    }
}
