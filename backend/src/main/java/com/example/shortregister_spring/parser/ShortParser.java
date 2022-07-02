package com.example.shortregister_spring.parser;

import org.springframework.scheduling.annotation.Scheduled;

public class ShortParser {

    @Scheduled(fixedRate = 5000)
    public static void printWithDelay() {
        System.out.println("It fired");
    }
}
