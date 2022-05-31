package com.example.shortregister_spring.repository;

import com.example.shortregister_spring.model.Instrument;
import com.example.shortregister_spring.model.Shorter;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ShortPositionRepositoryTest {

    @Autowired
    InstrumentRepository instrumentRepository;

    @Autowired
    ShorterRepository shorterRepository;

    @Autowired
    ShortPositionRepository shortPositionRepository;

    @Test
    public void myTest() {
        assertTrue(true);
    }

    @Test
    public void insertStatementBooleanQueriesTest() {
        Instrument instrument = instrumentRepository.getById("BMG9156K1018");
        Shorter shorter = shorterRepository.getById(1);

        assertNotNull(instrument);
        assertNotNull(shorter);

        Date myDate = new Date();

        boolean bool1 = shortPositionRepository.existsByShorterAndInstrumentAndClosedBefore(shorter, instrument, myDate);
        assertTrue(bool1);
    }
}
