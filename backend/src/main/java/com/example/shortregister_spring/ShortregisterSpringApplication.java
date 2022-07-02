package com.example.shortregister_spring;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import com.example.shortregister_spring.batch.ImportData;
import org.springframework.web.client.RestTemplate;

import static com.example.shortregister_spring.util.Constants.GET_DATA_CHRONEX;

@SpringBootApplication
@EnableScheduling
public class ShortregisterSpringApplication {

    @Scheduled(cron = GET_DATA_CHRONEX)
    void getExternalDataTrigger() {
        String url = "http://127.0.0.1:8080/get-data";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);

        System.out.println(result);
    }

    public static void main(String[] args) {
        SpringApplication.run(ShortregisterSpringApplication.class, args);
    }
}
