package wku.smartplant.service;

import org.springframework.stereotype.Service;
import wku.smartplant.jwt.SecurityUtil;
@Service
public class AuthenticationSevice {
    //로그인 여부 확인
    public Long isLoggedIn(){
        try{
            return SecurityUtil.getCurrentMemberId();
        } catch (Exception e){
            throw new RuntimeException("로그인이 필요합니다.");
        }
    }

    public void IsNotLoggedIn(){
        try{
            SecurityUtil.getCurrentMemberId();
        } catch (Exception e){
            return;
        }
        throw new RuntimeException("이미 로그인 되어있습니다.");
    }

}
