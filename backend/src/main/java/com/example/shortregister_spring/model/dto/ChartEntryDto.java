package com.example.shortregister_spring.model.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ChartEntryDto {
    OffsetDateTime date;
    String companyName;
    Integer shares;
    Double shortPercent;

    @Override
    public String toString() {
        return "ChartEntryDto{" +
                "date=" + date +
                ", companyName='" + companyName + '\'' +
                ", shares=" + shares +
                ", shortPercent=" + shortPercent +
                '}';
    }


    public ChartEntryDto(OffsetDateTime date, String companyName, Integer shares, Double shortPercent) {
        this.date = date;
        this.companyName = companyName;
        this.shares = shares;
        this.shortPercent = shortPercent;
    }
}
