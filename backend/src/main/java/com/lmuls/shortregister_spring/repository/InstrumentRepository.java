package com.lmuls.shortregister_spring.repository;


import com.lmuls.shortregister_spring.model.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface InstrumentRepository extends JpaRepository<Instrument, String> {

    Timestamp getLatestUpdate(String isin);

    Instrument getInstrumentByIssuerName(String issuerName);
    List<Instrument> findInstrumentByIssuerName(String issuerName);
    Instrument getInstrumentByIsin(String isin);
}
