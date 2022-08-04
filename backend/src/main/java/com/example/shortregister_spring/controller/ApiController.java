package com.example.shortregister_spring.controller;

import com.example.shortregister_spring.model.Instrument;
import com.example.shortregister_spring.model.ShortPosition;
import com.example.shortregister_spring.model.ShortPositionHistory;
import com.example.shortregister_spring.model.Shorter;
import com.example.shortregister_spring.model.dto.InstrumentDto;
import com.example.shortregister_spring.model.dto.ShorterDto;
import com.example.shortregister_spring.repository.InstrumentRepository;
import com.example.shortregister_spring.repository.ShortPositionHistoryRepository;
import com.example.shortregister_spring.repository.ShortPositionRepository;
import com.example.shortregister_spring.repository.ShorterRepository;
import com.example.shortregister_spring.service.InstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    ShorterRepository shorterRepository;

    @Autowired
    ShortPositionHistoryRepository shortPositionHistoryRepository;

    @Autowired
    ShortPositionRepository shortPositionRepository;

//    private final InstrumentService instrumentService;


//    public ApiController(InstrumentService instrumentService) {
//        this.instrumentService = instrumentService;
//    }


    @Autowired
    InstrumentRepository instrumentRepository;

    @Autowired
    InstrumentService instrumentService;


    @GetMapping("/instruments")
    public List<InstrumentDto> listInstruments() {
        return instrumentService.listInstruments();
    }

    @GetMapping("/instruments/{issuerName}")
    public ResponseEntity<InstrumentDto> getInstrumentByIssuerName(@PathVariable(value="issuerName") String issuerName) {
        issuerName = issuerName.replaceAll("-", " ");
        System.out.println(issuerName);
        try {
            return new ResponseEntity<InstrumentDto>(instrumentService.getInstrumentByIssuerName(issuerName), HttpStatus.OK);
        } catch(Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/shorters")
    public List<ShorterDto> listShorters() {
        return shorterRepository.findAll().stream().map(ShorterDto::convertEntityToDto).collect(Collectors.toList());
    }

    @GetMapping("/shorters/{companyName}")
    public ResponseEntity<ShorterDto> getShorterByCompanyName(@PathVariable(value="companyName") String companyName) {
        companyName = companyName.replaceAll("-", " ");
        try {
            ShorterDto shorter = ShorterDto.convertEntityToDto(shorterRepository.getByCompanyName(companyName));
            return new ResponseEntity<>(shorter, HttpStatus.OK);
        } catch(Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    Not needed, kept for debugging
    @GetMapping("/shortpositionhistory")
    public List<ShortPositionHistory> getPositionHistory() {
        return shortPositionHistoryRepository.findAll();
    }

    @GetMapping("/shortpositions")
    public List<ShortPosition> getShortPositions() {
        return shortPositionRepository.findAll();
    }

//    @GetMapping("shorter/{id}")
//    public ResponseEntity<Shorter> getShorterById(@PathVariable(value = "id") int id) {
//        Shorter shorter = shorterRepository.getById(id);
//        System.out.println(shorter.);
//        return new ResponseEntity<>(shorter, HttpStatus.OK);
//    }

//    @PostMapping("/instrument")
//    public ResponseEntity<Instrument> createInstrument(@RequestBody Instrument instrument) {
//        try {
//            Instrument _instrument = instrumentRepository.save(new Instrument(instrument.getIsin(), instrument.getIssuerName()));
//            return new ResponseEntity<>(_instrument, HttpStatus.CREATED);
//        } catch(Exception e) {
//            System.out.println(e.toString());
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    private InstrumentDto convertEntityToDto(Instrument instrument) {
//        InstrumentDto instrumentDto = new InstrumentDto();
//        instrumentDto.setIsin(instrument.getIsin());
//        instrumentDto.setIssuerName(instrument.getIssuerName());
//        instrumentDto.setPositions(instrument.getShortPositions());
//        return instrumentDto;
//    }
}

