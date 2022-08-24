package com.example.shortregister_spring.batch;

import com.example.shortregister_spring.model.Instrument;
import com.example.shortregister_spring.model.ShortPosition;
import com.example.shortregister_spring.model.ShortPositionHistory;
import com.example.shortregister_spring.model.Shorter;
import com.example.shortregister_spring.model.dto.CompanyDto;
import com.example.shortregister_spring.model.dto.EventsDto;
import com.example.shortregister_spring.repository.InstrumentRepository;
import com.example.shortregister_spring.repository.ShortPositionHistoryRepository;
import com.example.shortregister_spring.repository.ShortPositionRepository;
import com.example.shortregister_spring.repository.ShorterRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.google.gson.Gson;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class ImportData {

    @Autowired
    InstrumentRepository instrumentRepository;

    @Autowired
    ShorterRepository shorterRepository;

    @Autowired
    ShortPositionRepository shortPositionRepository;

    @Autowired
    ShortPositionHistoryRepository shortPositionHistoryRepository;

    @PersistenceContext
    EntityManager em;

    @GetMapping("/get-data")
    public void getExternalData() {
        String uri = "https://ssr.finanstilsynet.no/api/v2/instruments";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        Gson g = new Gson();
        CompanyDto[] dataImport = g.fromJson(response.getBody(), CompanyDto[].class);

//        for (CompanyDto companyDto : dataImport[0]) {
//            parseInstance(companyDto);
//        }
        parseInstance(dataImport[0]);
    }

    public void parseInstance(CompanyDto company) {
            Instrument instrument = instrumentRepository.getInstrumentByIsin(company.isin);
            if(instrument == null) {
                instrument = instrumentRepository.save(new Instrument(company.isin, company.issuerName));
            }

        company.events.sort(Comparator.comparing(a -> a.date));

        Timestamp ts = instrumentRepository.getLatestUpdate(instrument.getIsin());

        List<EventsDto> newEvents = company.events;
        if(ts != null) {
            newEvents = company.events.stream().filter(x -> x.date.after(Date.from(ts.toInstant()))).collect(Collectors.toList());
        }

        for(var event : newEvents) {
                List<ShortPosition> existingPositions = shortPositionRepository.findAllByActiveAndInstrument(true, instrument);
                List<String> eventPositionHolders = new ArrayList<>();
                event.activePositions.forEach(x -> eventPositionHolders.add(x.positionHolder));

                if (event.activePositions.size() < existingPositions.size()) {
                    for(ShortPosition shortPos : existingPositions) {
                        if(!eventPositionHolders.contains(shortPos.getShorterCompanyName()) ) {
                            shortPos.close(event.date.toInstant().atOffset(ZoneOffset.UTC));
                            shortPositionRepository.save(shortPos);
                        }
                    }
                }

                for(var activePosition: event.activePositions) {
                    Shorter shorter;
                    if(!shorterRepository.existsByCompanyName(activePosition.positionHolder)) {
                        shorter = shorterRepository.save(new Shorter(activePosition.positionHolder));
                    } else {
                        shorter = shorterRepository.getByCompanyName(activePosition.positionHolder);
                    }

                    ShortPosition shortPosition;
                    if(!shortPositionRepository.existsByShorterAndInstrumentAndActive(shorter, instrument, true)) {
                        if(!shortPositionRepository.existsByShorterAndInstrumentAndClosedAfter(shorter, instrument, OffsetDateTime.ofInstant(event.date.toInstant(), ZoneOffset.UTC))) {
                            shortPosition = shortPositionRepository.save(new ShortPosition(instrument, shorter, activePosition.date.toInstant().atOffset(ZoneOffset.UTC)));
                        } else {
                            //TODO Refaktorer, var sliten :)
                            shortPosition = shortPositionRepository.getByShorterAndInstrumentAndActive(shorter, instrument, true);
                        }
                    } else {
                        shortPosition = shortPositionRepository.getByShorterAndInstrumentAndActive(shorter, instrument, true);
                    }

                    if(!shortPositionHistoryRepository.existsByDateAndShortPosition(event.date.toInstant().atOffset(ZoneOffset.UTC), shortPosition)) {
                        shortPositionHistoryRepository.save(new ShortPositionHistory(shortPosition, event.date.toInstant().atOffset(ZoneOffset.UTC), activePosition.shares, activePosition.shortPercent));
                    }
                }
            }
    }



}
