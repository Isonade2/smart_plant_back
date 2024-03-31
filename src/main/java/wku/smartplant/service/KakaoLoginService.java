package wku.smartplant.service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class KakaoService {

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

    public HashMap<String, Object> getUserInfo(String accessToken) throws IOException {
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

        return userInfo;
    }

    private void logResponse(String response) {
        log.info("Response Body : {}", response);
    }
}
