package wku.smartplant.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wku.smartplant.domain.EmailVerify;
import wku.smartplant.jwt.JwtTokenUtil;
import wku.smartplant.domain.Member;
import wku.smartplant.dto.member.MemberJoinRequest;
import wku.smartplant.dto.member.MemberLoginRequest;
import wku.smartplant.dto.member.MemberLoginResponse;
import wku.smartplant.exception.EmailAlreadyExistsException;
import wku.smartplant.repository.EmailVerifyRepository;
import wku.smartplant.repository.MemberRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static wku.smartplant.domain.MemberPlatform.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final EmailService emailService;
    private final MemberRepository memberRepository;
    private final EmailVerifyRepository emailVerifyRepository;
    private final RefreshTokenService refreshTokenService;

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

        Member member = memberJoinRequest.toEntity(); //엔티티화

        if (member.getMemberPlatform() == LOCAL) {
            EmailVerify emailVerify = new EmailVerify(member, member.getEmail());
            member.changeEmailVerify(emailVerify);
            emailService.sendJoinVerifyMail(memberJoinRequest.getEmail(), emailVerify.getUuid());
        }
        return memberRepository.save(member);
    }

    @Transactional
    public MemberLoginResponse loginMember(MemberLoginRequest memberLoginRequest) {
        Member findMember = memberRepository.findByEmail(memberLoginRequest.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));

        if (findMember.getMemberPlatform() == KAKAO
                && memberLoginRequest.getMemberPlatform() == LOCAL) {
            //카카오로 가입했는데 로컬 로그인으로 시도했을경우
            throw new IllegalStateException("카카오로 가입했으나 로컬 로그인으로 시도했습니다. \n올바른 플랫폼 로그인을 선택해주세요.");
        }

        if (findMember.getMemberPlatform() == LOCAL
                && memberLoginRequest.getMemberPlatform() == KAKAO) {
            //로컬로 가입했는데 카카오 로그인으로 시도했을경우
            throw new IllegalStateException("로컬 이메일로 가입했으나 카카오 로그인으로 시도했습니다. \n올바른 플랫폼 로그인을 선택해주세요.");
        }

        if (!passwordEncoder.matches(memberLoginRequest.getPassword(), findMember.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        if (!findMember.getActivate()) {
            throw new IllegalStateException(findMember.getEmail() + " 메일함을 확인하여 계정을 활성화 이후 다시 로그인 해주세요. " +
                    "메일이 보이지 않을 경우 스팸 메일함을 확인해보세요.");
        }

        //예외 처리 끝, 로직 시작
        String accessToken = JwtTokenUtil.createAccessToken(findMember.getId().toString(), 3600000); // 1시간
        String refreshToken = JwtTokenUtil.createRefreshToken(findMember.getId().toString(), 604800000); // 7일

        refreshTokenService.saveRefreshToken(findMember.getId(), refreshToken);

        log.info("{} 로그인. 토근 = {}, {}", findMember.getEmail(), accessToken, refreshToken);

        return MemberLoginResponse.builder()
                .email(findMember.getEmail())
                .username(findMember.getUsername())
                .accessToken(accessToken)
                .refreshToken(refreshToken).build();
    }

    @Transactional
    public void logoutMember(Long memberId) {
        refreshTokenService.deleteByMemberId(memberId);
    }

    @Transactional
    public void verifyAndActivateMember(String uuid) {
        EmailVerify findEmailVerify = emailVerifyRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("잘못되거나 만료된 인증입니다."));

        timeExpiredCheck(findEmailVerify);

        System.out.println("findEmailVerify.getMember() = " + findEmailVerify.getMember());
        findEmailVerify.getMember().changeActivate(true);
        findEmailVerify.getMember().changeEmailVerify(null);
        emailVerifyRepository.delete(findEmailVerify);

        log.info("{} 계정 활성화. UUID = {}", findEmailVerify.getEmail(), findEmailVerify.getUuid());
    }

    @Transactional
    public Member findMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));
    }

    @Transactional
    public void resendActivateMail(String email) {
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("가입 이력이 없는 이메일입니다."));

        if (findMember.getActivate()) {
            throw new IllegalStateException("이미 활성화 된 메일입니다.");
        }

        EmailVerify newEmailVerify = new EmailVerify(findMember, email);
        findMember.changeEmailVerify(newEmailVerify);
        emailService.sendJoinVerifyMail(email, newEmailVerify.getUuid());
    }

    @Transactional
    public void passwordResetRequest(String email) {
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("잘못된 이메일 입니다."));

        if (!findMember.getActivate()) {
            throw new IllegalStateException("아직 가입이 완료되지 않은 이메일입니다.");
        }

        EmailVerify emailVerify = new EmailVerify(findMember, email);
        findMember.changeEmailVerify(emailVerify);

        emailService.sendPasswordResetMail(email, emailVerify.getUuid());
    }

    @Transactional
    public void changePassword(String email, String password, String uuid) {
        EmailVerify findEmailVerify = emailVerifyRepository.findByEmailAndUuid(email, uuid)
                .orElseThrow(() -> new IllegalArgumentException("인증 정보가 잘못됐습니다."));

        timeExpiredCheck(findEmailVerify);

        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다."));
        refreshTokenService.deleteByMemberId(findMember.getId()); //비밀번호 변경시 기존에 발급한 리프레시 토큰 삭제

        findMember.changePassword(passwordEncoder.encode(password));
    }

    @Transactional
    public void deleteMember(Long memberId, String password) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다."));
        if (!passwordEncoder.matches(password, findMember.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        memberRepository.delete(findMember);
    }


    private void timeExpiredCheck(EmailVerify findEmailVerify) {
        Duration duration = Duration.between(findEmailVerify.getCreatedDate(), LocalDateTime.now());
        if (duration.toMinutes() > 120) {
            log.info("{} 활성화 이메일 만료", findEmailVerify.getEmail());
            emailVerifyRepository.delete(findEmailVerify);

            throw new IllegalStateException("메일 인증 시간이 만료되었습니다.");
        }
    }

}
