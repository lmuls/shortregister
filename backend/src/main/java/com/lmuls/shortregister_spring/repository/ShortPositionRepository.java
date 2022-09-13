package com.lmuls.shortregister_spring.repository;


import com.lmuls.shortregister_spring.model.Instrument;
import com.lmuls.shortregister_spring.model.ShortPosition;
import com.lmuls.shortregister_spring.model.Shorter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface ShortPositionRepository extends JpaRepository<ShortPosition, Integer> {
    ShortPosition getByShorterAndInstrumentAndActive(Shorter shorter, Instrument instrument, boolean active);
    List<ShortPosition> getByInstrument(Instrument instrument);
    List<ShortPosition> getByInstrument_IssuerName(String instrumentName);
    ShortPosition getDistinctTopByShorterAndInstrumentOrderByClosed(Shorter shorter, Instrument instrument);
    boolean existsByShorterAndInstrumentAndClosedIsBefore(Shorter shorter, Instrument instrument, OffsetDateTime date);
    ShortPosition getByShorterAndInstrumentAndOpened(Shorter shorter, Instrument instrument, OffsetDateTime date);
    boolean existsByShorterAndOpened(Shorter shorter, OffsetDateTime opened);
    boolean existsByShorterAndInstrumentAndOpened(Shorter shorter, Instrument instrument, OffsetDateTime date);
    boolean existsByShorterAndInstrument(Shorter shorter, Instrument instrument);
    boolean existsByShorterAndInstrumentAndClosedAfter(Shorter shorter, Instrument instrument, OffsetDateTime date);
    boolean existsByShorterAndInstrumentAndActive(Shorter shorter, Instrument instrument, boolean active);
    boolean existsByShorterAndInstrumentAndActiveAndOpened(Shorter shorter, Instrument instrument, boolean active, OffsetDateTime opened);
    boolean existsByShorterAndInstrumentAndActiveAndOpenedNot(Shorter shorter, Instrument instrument, boolean active, OffsetDateTime opened);
    boolean existsByInstrument_IsinAndShorter_CompanyNameAndActive(String isin, String company_name, boolean active);
    boolean existsByInstrumentAndShorterAndActiveAndOpened(Instrument instrument, Shorter shorter, boolean active, OffsetDateTime opened);
    List<ShortPosition> findAllByActiveAndInstrument(boolean active, Instrument instrument);
}
