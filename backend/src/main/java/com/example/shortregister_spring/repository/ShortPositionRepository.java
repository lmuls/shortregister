package com.example.shortregister_spring.repository;


import com.example.shortregister_spring.model.Instrument;
import com.example.shortregister_spring.model.ShortPosition;
import com.example.shortregister_spring.model.Shorter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ShortPositionRepository extends JpaRepository<ShortPosition, Integer> {
    ShortPosition getByShorterAndInstrumentAndActive(Shorter shorter, Instrument instrument, boolean active);
    List<ShortPosition> getByInstrument(Instrument instrument);
    ShortPosition getDistinctTopByShorterAndInstrumentOrderByClosed(Shorter shorter, Instrument instrument);
    boolean existsByShorterAndInstrumentAndClosedIsBefore(Shorter shorter, Instrument instrument, Date date);
    ShortPosition getByShorterAndInstrumentAndOpened(Shorter shorter, Instrument instrument, Date date);
    boolean existsByShorterAndOpened(Shorter shorter, Date opened);
    boolean existsByShorterAndInstrumentAndOpened(Shorter shorter, Instrument instrument, Date date);
    boolean existsByShorterAndInstrument(Shorter shorter, Instrument instrument);
    boolean existsByShorterAndInstrumentAndClosedBefore(Shorter shorter, Instrument instrument, Date date);
    boolean existsByShorterAndInstrumentAndActive(Shorter shorter, Instrument instrument, boolean active);
    boolean existsByShorterAndInstrumentAndActiveAndOpened(Shorter shorter, Instrument instrument, boolean active, Date opened);
    boolean existsByShorterAndInstrumentAndActiveAndOpenedNot(Shorter shorter, Instrument instrument, boolean active, Date opened);
    boolean existsByInstrument_IsinAndShorter_CompanyNameAndActive(String isin, String company_name, boolean active);
    boolean existsByInstrumentAndShorterAndActiveAndOpened(Instrument instrument, Shorter shorter, boolean active, Date opened);
    List<ShortPosition> findAllByActiveAndInstrument(boolean active, Instrument instrument);
}
