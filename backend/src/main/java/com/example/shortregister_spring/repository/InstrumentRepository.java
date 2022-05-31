package com.example.shortregister_spring.repository;


import com.example.shortregister_spring.model.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstrumentRepository extends JpaRepository<Instrument, String> {
    List<Instrument> findInstrumentByIssuerName(String issuerName);
    Instrument getInstrumentByIsin(String isin);
}
