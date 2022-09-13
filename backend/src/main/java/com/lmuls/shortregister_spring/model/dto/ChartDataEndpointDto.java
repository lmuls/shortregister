package com.lmuls.shortregister_spring.model.dto;

public class ChartDataEndpointDto {
    String subject;

    public ChartDataEndpointDto(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return this.subject;
    }
}
