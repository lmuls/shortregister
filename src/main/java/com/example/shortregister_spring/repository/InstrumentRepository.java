package com.example.shortregister_spring.repository;


import com.example.shortregister_spring.model.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstrumentRepository extends JpaRepository<Instrument, String> {
    List<Instrument> findInstrumentByIssuerName(String issuerName);
}
