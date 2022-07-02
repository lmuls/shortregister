package com.example.shortregister_spring.model.dto;

import com.example.shortregister_spring.model.ShortPosition;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ShortPositionDto {
    String issuerName;
    String companyName;
    boolean active;
    Date opened;
    Date closed;
    List<ShortPositionHistoryDto> history;

    public static ShortPositionDto to(ShortPosition shortPosition) {
        ShortPositionDto e = new ShortPositionDto();
        e.setIssuerName(shortPosition.getInstrument().getIssuerName());
        e.setCompanyName(shortPosition.getShorterCompanyName());
        e.setActive(shortPosition.isActive());
        e.setOpened(shortPosition.getOpened());
        e.setClosed(shortPosition.getClosed());
        e.setHistory(shortPosition.getShortPositionHistories().stream().map(ShortPositionHistoryDto::to).collect(Collectors.toList()));
        return e;
    }
}
