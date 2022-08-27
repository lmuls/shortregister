package com.example.shortregister_spring.util;

import com.example.shortregister_spring.model.ShortPosition;
import com.example.shortregister_spring.model.ShortPositionHistory;
import com.example.shortregister_spring.model.dto.ChartEntryDto;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class ChartData {
    public static List<Map<String, Object>> parse(List<ShortPosition> shortPositions, boolean isInstrument) {
        List<ChartEntryDto> res = new ArrayList<>();
        for (ShortPosition shortPosition : shortPositions) {
            OffsetDateTime opened = shortPosition.getOpened().minusDays(1);
            List<ShortPositionHistory> histories = shortPosition.getShortPositionHistories();

            String name = isInstrument ? shortPosition.getShorterCompanyName()  : shortPosition.getInstrumentName() ;

            res.add(new ChartEntryDto(opened, name, 0, 0d));
            if (!shortPosition.isActive()) {
                histories.add(new ShortPositionHistory(shortPosition, shortPosition.getClosed(), 0, 0d));
            }

            histories.sort(Comparator.comparing(ShortPositionHistory::getDate));
            for (int i = 0; i < histories.size(); i++) {
                ShortPositionHistory shortPositionHistory = histories.get(i);
                res.add(new ChartEntryDto(shortPositionHistory.getDate(), name, shortPositionHistory.getShares(), shortPositionHistory.getShortPercent()));

                if (i < histories.size() - 1) {
                    OffsetDateTime nextDate = histories.get(i + 1).getDate();
                    OffsetDateTime interimDate = OffsetDateTime.from(shortPositionHistory.getDate()).plusDays(1);
                    while (interimDate.isBefore(nextDate)) {
                            res.add(new ChartEntryDto(interimDate, name, shortPositionHistory.getShares(), shortPositionHistory.getShortPercent()));
                            interimDate = interimDate.plusDays(1);
                        }
                    }
                }
            }

        List<Map<String, Object>> newRes = new ArrayList<>();

        for(var inst : res) {
            if(newRes.stream().noneMatch(x -> x.get("date").equals(inst.getDate().toLocalDate()))) {
                Map<String, Object> newMap = new HashMap<>();
                newMap.put("date", inst.getDate().toLocalDate());
                newMap.put(inst.getCompanyName(), inst.getShares());
                newRes.add(newMap);
            } else {
                for(var inst2: newRes) {
                    if(inst2.get("date").equals(inst.getDate().toLocalDate())) {
                        inst2.put(inst.getCompanyName(), inst.getShares());
                    }
                }
            }
        }
        return newRes;
    }
}