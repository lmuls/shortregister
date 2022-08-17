package com.example.shortregister_spring.model.dto;

import com.example.shortregister_spring.model.ShortPositionHistory;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ShortPositionHistoryDto {
    OffsetDateTime date;
    int shares;
    double shortPercent;

    public static ShortPositionHistoryDto to(ShortPositionHistory shortPositionHistory) {
        ShortPositionHistoryDto e = new ShortPositionHistoryDto();
        e.setDate(shortPositionHistory.getDate());
        e.setShares(shortPositionHistory.getShares());
        e.setShortPercent(shortPositionHistory.getShortPercent());
        return e;
    }
}
