package co.kr.grouppurchace.domain.healthy;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@EnableScheduling
public class HealthyCheckController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/")
    public String home() {
        return "Hello World!";
    }

    // 15분마다 호출
    @Scheduled(fixedRate = 15 * 60 * 1000)
    public void pingSelf() {
        try {
            String url = "https://grouppurchase-back.onrender.com";
            String response = restTemplate.getForObject(url, String.class);
            System.out.println("Self-ping success: " + response);
        } catch (Exception e) {
            System.err.println("Self-ping failed: " + e.getMessage());
        }
    }
}