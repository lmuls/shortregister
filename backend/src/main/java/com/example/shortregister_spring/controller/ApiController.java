package com.example.shortregister_spring.controller;

import com.example.shortregister_spring.model.Instrument;
import com.example.shortregister_spring.model.ShortPosition;
import com.example.shortregister_spring.model.ShortPositionHistory;
import com.example.shortregister_spring.model.Shorter;
import com.example.shortregister_spring.model.dto.ChartDataEndpointDto;
import com.example.shortregister_spring.model.dto.InstrumentDto;
import com.example.shortregister_spring.model.dto.ShorterDto;
import com.example.shortregister_spring.repository.InstrumentRepository;
import com.example.shortregister_spring.repository.ShortPositionHistoryRepository;
import com.example.shortregister_spring.repository.ShortPositionRepository;
import com.example.shortregister_spring.repository.ShorterRepository;
import com.example.shortregister_spring.service.InstrumentService;
import com.example.shortregister_spring.util.ChartData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.util.List;
import java.util.Map;
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

    @PostMapping(path="/chart-data", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://127.0.0.1:3000")
    public ResponseEntity<List<Map<String, Object>>> getChartDate(@RequestBody Map<String, String> payload) {
        String subject = payload.get("subject");

        if(subject == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Instrument instrument = instrumentRepository.getInstrumentByIssuerName(subject);
        if(instrument != null) {
            return new ResponseEntity<>(ChartData.parse(instrument.getShortPositions(), true), HttpStatus.OK);
        } else {
            Shorter shorter = shorterRepository.getByCompanyName(subject);
            if(shorter != null) {
                return new ResponseEntity<>(ChartData.parse(shorter.getShortPositions(), false), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

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

