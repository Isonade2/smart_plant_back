package wku.smartplant.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.profiles.url}")
    private String serverUrl;

    @Value("${client.url}")
    private String clientUrl;

    @Async
    public void sendJoinVerifyMail(String to, String uuid) {
        String verifyUrl = serverUrl + "/member/verify?code=" + uuid;
        String htmlContent = "<p>NAMOO 서비스 회원가입 확인</p>" +
                "<p>아래의 링크를 클릭하여 계정을 활성화 후 로그인 해주세요.</p>" +
                "<p>링크의 유효 시간은 2시간 입니다." +
                "<a href='" + serverUrl + "/member/verify?code=" + uuid + "'>계정 활성화하기</a>";

        try {
            sendHtmlMessage(to, "NAMOO 서비스 회원가입 확인", htmlContent);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendPasswordResetMail(String to, String uuid) {
        String verifyUrl = serverUrl + "/member/verify?code=" + uuid;
        String htmlContent = "<p>NAMOO 서비스 비밀번호 초기화</p>" +
                "<p>아래의 링크를 클릭하여 계정의 비밀번호를 변경해주세요.</p>" +
                "<p>링크의 유효 시간은 2시간 입니다." +
                "<a href='" + clientUrl + "/password/reset?code=" + uuid + "&email=" + to + "'>계정 활성화하기</a>";

        try {
            sendHtmlMessage(to, "NAMOO 서비스 회원가입 확인", htmlContent);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    private void sendHtmlMessage(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("fdxguy2@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // true는 HTML 메일을 의미합니다.

        mailSender.send(message);
        log.info("인증 이메일 전송완료. ({})", to);
    }

    private void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("fdxguy2@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

}
