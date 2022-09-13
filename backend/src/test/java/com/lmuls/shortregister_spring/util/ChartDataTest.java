package com.lmuls.shortregister_spring.util;

import com.lmuls.shortregister_spring.model.ShortPosition;
import com.lmuls.shortregister_spring.service.InstrumentService;
import com.lmuls.shortregister_spring.service.ShortPositionService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

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
        List<ShortPosition> shortPositions = shortPositionService.getShortPositions("2020 BULKERS");
//        assertThat(shortPositions.size()).isEqualTo(7);

        List<Map<String, Object>> chartEntries = ChartData.parse(shortPositions, true);
        for(var inst: chartEntries) System.out.println(inst);
    }
}