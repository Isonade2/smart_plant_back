package wku.smartplant.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DiscordService {
    private static final String WEBHOOK_URL = "https://discord.com/api/webhooks/1235189028521771108/8zMq0BeE1h5TVVozWSvYPsdscY4y-oLizIl8P1WZ4wM3AoLd29S7jU1eYOh3hpR1lx2E";

    public void sendDiscordMessage(String message) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonPayload = "{\"content\": \"" + message + "\"}";
        HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);

        restTemplate.postForObject(WEBHOOK_URL, request, String.class);
    }
}
