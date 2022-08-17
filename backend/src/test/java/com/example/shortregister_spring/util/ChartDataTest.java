package com.example.shortregister_spring.util;

import com.example.shortregister_spring.model.ShortPosition;
import com.example.shortregister_spring.model.dto.ChartEntryDto;
import com.example.shortregister_spring.service.InstrumentService;
import com.example.shortregister_spring.service.ShortPositionService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class ChartDataTest {

    @Autowired
    ShortPositionService shortPositionService;

    @Autowired
    InstrumentService instrumentService;

    @Test
    void parse() {
        List<ShortPosition> shortPositions = shortPositionService.getShortPositions("AKER CARBON CAPTUR");
        assertThat(shortPositions.size()).isEqualTo(3);

        List<ChartEntryDto> chartEntries = ChartData.parse(shortPositions);

        for(var chartEntry : chartEntries) {
            System.out.println(chartEntry);
        }
    }
}