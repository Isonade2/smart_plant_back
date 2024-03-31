package wku.smartplant.service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wku.smartplant.domain.Member;
import wku.smartplant.domain.MemberType;
import wku.smartplant.dto.member.KakaoMemberDTO;
import wku.smartplant.dto.member.MemberJoinRequest;
import wku.smartplant.dto.member.MemberLoginRequest;
import wku.smartplant.dto.member.MemberLoginResponse;
import wku.smartplant.exception.EmailAlreadyExistsException;
import wku.smartplant.repository.MemberRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoLoginService {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    private static final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";
    private static final String POST_METHOD = "POST";
    private static final String GET_METHOD = "GET";

    public String getAccessTokenFromKakao(String clientId, String code) throws IOException {
        String parameters = String.format("grant_type=authorization_code&client_id=%s&code=%s", clientId, code);
        String response = makeHttpRequest(KAKAO_TOKEN_URL + "?" + parameters, POST_METHOD, null);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = objectMapper.readValue(response, new TypeReference<Map<String, Object>>() {});

        logResponse(response);

        return extractTokensAndLog(jsonMap);
    }


    public MemberLoginResponse joinOrLoginMember(String accessToken) throws IOException {
        HashMap<String, Object> userInfo = getUserInfo(accessToken);

        // HashMap에서 필요한 정보를 추출
        Long id = Long.parseLong(String.valueOf(userInfo.get("id")));
        String nickname = (String) userInfo.get("nickname");
        String email = (String) userInfo.get("email");

        Optional<Member> findMember = memberRepository.findByEmail(email);

        findMember.ifPresent(member -> {
            if (member.getMemberType() != MemberType.KAKAO) {
                throw new EmailAlreadyExistsException("이미 다른 플랫폼으로 가입한 이메일입니다.");
            }
        });

        if(findMember.isEmpty()) {
            memberService.joinMember(new MemberJoinRequest(nickname,email,id+nickname, MemberType.KAKAO));
        }
        return memberService.loginMember(new MemberLoginRequest(email,id+nickname));
    }
    private HashMap<String, Object> getUserInfo(String accessToken) throws IOException {
        String response = makeHttpRequest(KAKAO_USER_INFO_URL, GET_METHOD, accessToken);

        logResponse(response);

        return parseUserInfo(response);
    }

    private String makeHttpRequest(String urlString, String method, String accessToken) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);

        if (accessToken != null) {
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }

    private String extractTokensAndLog(Map<String, Object> jsonMap) {
        String accessToken = (String) jsonMap.get("access_token");
        log.info("Access Token : {}", accessToken);
        log.info("Refresh Token : {}", jsonMap.get("refresh_token"));
        log.info("Scope : {}", jsonMap.get("scope"));

        return accessToken;
    }

    private HashMap<String, Object> parseUserInfo(String response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = objectMapper.readValue(response, new TypeReference<Map<String, Object>>() {});

        Map<String, Object> properties = (Map<String, Object>) jsonMap.get("properties");
        Map<String, Object> kakaoAccount = (Map<String, Object>) jsonMap.get("kakao_account");

        HashMap<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", jsonMap.get("id"));
        userInfo.put("nickname", properties.get("nickname"));
        //userInfo.put("profileImage", properties.get("profile_image"));
        userInfo.put("email", kakaoAccount.get("email"));
        log.info("{}",userInfo);

        return userInfo;
    }

    private void logResponse(String response) {
        log.info("Response Body : {}", response);
    }
}
