package com.lmuls.shortregister_spring;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import static com.lmuls.shortregister_spring.util.Constants.GET_DATA_CHRONEX;

@SpringBootApplication
@EnableScheduling
public class ShortregisterSpringApplication {

    @Scheduled(cron = GET_DATA_CHRONEX)
    void getExternalDataTrigger() {
        String url = "http://127.0.0.1:8080/get-data";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(url, String.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ShortregisterSpringApplication.class, args);
    }
}
