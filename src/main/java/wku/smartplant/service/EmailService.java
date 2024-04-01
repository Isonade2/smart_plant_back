package wku.smartplant.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.profiles.url}")
    private String serverUrl;

    public void sendVerifyMail(String to, String uuid) {
        String verifyUrl = serverUrl + "/member/verify?code=" + uuid;
        String htmlContent = "<p>NAMOO 서비스 회원가입 확인</p>" +
                "<p>아래의 링크를 클릭하여 계정을 활성화해주세요:</p>" +
                "<a href='" + serverUrl + "/member/verify?code=" + uuid + "'>계정 활성화하기</a>";

        try {
            sendHtmlMessage(to, "NAMOO 서비스 회원가입 확인", htmlContent);
        } catch (MessagingException e) {
            e.printStackTrace();
            // 적절한 예외 처리를 수행합니다.
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
