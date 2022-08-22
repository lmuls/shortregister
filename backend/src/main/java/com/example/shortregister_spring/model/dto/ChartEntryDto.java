package com.example.shortregister_spring.model.dto;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Objects;

@Data
public class ChartEntryDto implements Comparable<ChartEntryDto>{
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChartEntryDto that = (ChartEntryDto) o;
        return Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

    public ChartEntryDto(OffsetDateTime date, String companyName, Integer shares, Double shortPercent) {
        this.date = date;
        this.companyName = companyName;
        this.shares = shares;
        this.shortPercent = shortPercent;
    }

    @Override
    public int compareTo(ChartEntryDto o) {
        return this.date.compareTo(o.getDate());
    }
}
