package com.example.shortregister_spring.util;

import com.example.shortregister_spring.model.ShortPosition;
import com.example.shortregister_spring.model.ShortPositionHistory;
import com.example.shortregister_spring.model.dto.ChartEntryDto;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class ChartData {

    public static List<ChartEntryDto> parse(List<ShortPosition> shortPositions) {
        List<ChartEntryDto> res = new ArrayList<>();
        for(ShortPosition shortPosition : shortPositions) {
            OffsetDateTime opened = shortPosition.getOpened().minusDays(1);

           res.add(new ChartEntryDto(opened, shortPosition.getShorterCompanyName(), 0, 0d));
           if(!shortPosition.isActive()) {
               res.add(new ChartEntryDto(shortPosition.getClosed().plusDays(1), shortPosition.getShorterCompanyName(), 0, 0d));
           }


           List<ShortPositionHistory> histories = shortPosition.getShortPositionHistories();
           histories.sort((x, y) -> x.getDate().compareTo(y.getDate()));
           for(int i = 0; i < histories.size(); i++) {
               ShortPositionHistory shortPositionHistory = histories.get(i);
               res.add(new ChartEntryDto(shortPositionHistory.getDate(), shortPosition.getShorterCompanyName(), shortPositionHistory.getShares(), shortPositionHistory.getShortPercent()));

               if(i < histories.size() -1) {
                   OffsetDateTime nextDate = histories.get(i+1).getDate();
                   OffsetDateTime interimDate = OffsetDateTime.from(shortPositionHistory.getDate()).plusDays(1);
                   while(interimDate.isBefore(nextDate)) {
                       if(res.fi)
                       res.add(new ChartEntryDto(interimDate, shortPosition.getShorterCompanyName(), shortPositionHistory.getShares(), shortPositionHistory.getShortPercent()));
                       interimDate = interimDate.plusDays(1);
                   }
               }
           }
           res.sort((x, y) -> x.getDate().compareTo(y.getDate()));
        }
        return res;
    }

}
