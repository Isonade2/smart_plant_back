package wku.smartplant.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wku.smartplant.domain.EmailVerify;
import wku.smartplant.domain.MemberPlatform;
import wku.smartplant.jwt.JwtTokenUtil;
import wku.smartplant.domain.Member;
import wku.smartplant.dto.member.MemberJoinRequest;
import wku.smartplant.dto.member.MemberLoginRequest;
import wku.smartplant.dto.member.MemberLoginResponse;
import wku.smartplant.exception.EmailAlreadyExistsException;
import wku.smartplant.repository.EmailVerifyRepository;
import wku.smartplant.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final EmailService emailService;
    private final MemberRepository memberRepository;
    private final EmailVerifyRepository emailVerifyRepository;

    @Value("${jwt.secret}")
    private String secretKey;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Transactional
    public Member joinMember(MemberJoinRequest memberJoinRequest) {
        Optional<Member> findMember = memberRepository.findByEmail(memberJoinRequest.getEmail());
        if(findMember.isPresent()) {
            throw new EmailAlreadyExistsException("이미 등록된 이메일입니다: " + memberJoinRequest.getEmail());
        }

        memberJoinRequest.setPassword(passwordEncoder.encode(memberJoinRequest.getPassword()));
        System.out.println("memberJoinRequest = " + memberJoinRequest);

        if (memberJoinRequest.getMemberPlatform() == null) { //기본 가입 시
            memberJoinRequest.setMemberPlatform(MemberPlatform.LOCAL);
        }
        Member member = memberJoinRequest.toEntity(); //엔티티화

        if (member.getMemberPlatform() == MemberPlatform.LOCAL) {
            EmailVerify emailVerify = new EmailVerify(member);
            member.changeEmailVerify(emailVerify);
            emailService.sendVerifyMail(memberJoinRequest.getEmail(), emailVerify.getUuid());
        }
        return memberRepository.save(member);
    }

    @Transactional
    public MemberLoginResponse loginMember(MemberLoginRequest memberLoginRequest) {
        Member findMember = memberRepository.findByEmail(memberLoginRequest.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));

        if (!passwordEncoder.matches(memberLoginRequest.getPassword(), findMember.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        if (!findMember.getActivate()) {
            throw new IllegalStateException(findMember.getEmail() + " 이메일을 확인하여 계정을 활성화 이후 다시 로그인 해주세요. " +
                    "메일이 보이지 않을 경우 스팸 메일함을 확인해보세요.");
        }

        String token = JwtTokenUtil.createToken(findMember.getId().toString(),  1000000);

        return MemberLoginResponse.builder()
                .email(findMember.getEmail())
                .username(findMember.getUsername())
                .token(token).build();

    }

    @Transactional
    public void verifyAndActivateMember(String uuid) {
        EmailVerify findEmailVerify = emailVerifyRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("잘못되거나 만료된 인증입니다."));

        System.out.println("findEmailVerify.getMember() = " + findEmailVerify.getMember());
        findEmailVerify.getMember().changeActivate(true);
        emailVerifyRepository.delete(findEmailVerify);
    }
    @Transactional
    public Member findMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));
    }

}
