package wku.smartplant.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmailServiceTest {

    @Autowired EmailService emailService;

    @Test
    void emailTest() {
        //mailService.sendSimpleMessage("playgm1@naver.com","subject","이메일 테스트입니다~~");
    }


}