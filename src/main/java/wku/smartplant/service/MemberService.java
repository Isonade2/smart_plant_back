package wku.smartplant.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wku.smartplant.domain.Member;
import wku.smartplant.dto.MemberJoinDto;
import wku.smartplant.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member joinMember(MemberJoinDto memberJoinDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberJoinDto.setPassword(passwordEncoder.encode(memberJoinDto.getPassword()));
        return memberRepository.save(memberJoinDto.toEntity());
    }
    @Transactional
    public Member findMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));
    }
}
