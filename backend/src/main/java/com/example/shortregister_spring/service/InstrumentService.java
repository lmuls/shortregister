

package com.example.shortregister_spring.service;

import com.example.shortregister_spring.model.dto.InstrumentDto;
import com.example.shortregister_spring.repository.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstrumentService {
    private final InstrumentRepository instrumentRepository;

    public InstrumentDto getInstrument(String isin) {
        return InstrumentDto.to(instrumentRepository.getById(isin));
    }
    public InstrumentDto getInstrumentByIssuerName(String issuerName) {
        return InstrumentDto.to(instrumentRepository.getInstrumentByIssuerName(issuerName));
    }



    public List<InstrumentDto> listInstruments() {
        return instrumentRepository.findAll().stream().map(InstrumentDto::to).collect(Collectors.toList());
    }

    @Autowired
    public InstrumentService(InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }

}
