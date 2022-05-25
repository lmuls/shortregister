package com.example.shortregister_spring.controller;

import com.example.shortregister_spring.model.Instrument;
import com.example.shortregister_spring.repository.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class InstrumentController {

    @Autowired
    InstrumentRepository instrumentRepository;

    @PostMapping("/instrument")
    public ResponseEntity<Instrument> createInstrument(@RequestBody Instrument instrument) {
        try {
            Instrument _instrument = instrumentRepository.save(new Instrument(instrument.getIsin(), instrument.getIssuerName()));
            return new ResponseEntity<>(_instrument, HttpStatus.CREATED);
        } catch(Exception e) {
            System.out.println(e.toString());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
