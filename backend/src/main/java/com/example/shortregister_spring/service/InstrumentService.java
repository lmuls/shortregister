

package com.example.shortregister_spring.service;

import com.example.shortregister_spring.model.Instrument;
import com.example.shortregister_spring.repository.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstrumentService {
    private final InstrumentRepository instrumentRepository;

    public Instrument getInstrumentByIsin(String isin) {
        return instrumentRepository.getById(isin);
    }

    public List<Instrument> listInstruments() {
        return instrumentRepository.findAll();
    }

    @Autowired
    public InstrumentService(InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }

}
