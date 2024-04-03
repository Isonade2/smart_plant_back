package wku.smartplant.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import wku.smartplant.domain.Member;
import wku.smartplant.jwt.SecurityUtil;
import wku.smartplant.service.MemberService;

@Aspect
@Component
@RequiredArgsConstructor
public class AdminCheckAspect {

    private final MemberService memberService;

    @Pointcut("execution(* wku.smartplant.controller.ArduinoController.*(..))")
    public void adminMethods() {}

//    @Before("adminMethods()")
//    public void checkAdminRole() throws IllegalAccessException {
//        System.out.println("checkadmin 실행");
//        Member findMember = memberService.findMemberById(SecurityUtil.getCurrentMemberId());
//        if (findMember == null || !findMember.getRole().equals("ADMIN")) {
//            System.out.println("어드민이 아님");
//            throw new IllegalAccessException("Not authorized");
//        }
//    }

}
