package com.example.shortregister_spring.batch;

import com.example.shortregister_spring.model.Instrument;
import com.example.shortregister_spring.model.ShortPosition;
import com.example.shortregister_spring.model.ShortPositionHistory;
import com.example.shortregister_spring.model.Shorter;
import com.example.shortregister_spring.model.dto.DataImportDto;
import com.example.shortregister_spring.repository.InstrumentRepository;
import com.example.shortregister_spring.repository.ShortPositionHistoryRepository;
import com.example.shortregister_spring.repository.ShortPositionRepository;
import com.example.shortregister_spring.repository.ShorterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.google.gson.Gson;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


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

    @GetMapping("/get-data")
    public void getExternalData() {
        String uri = "https://ssr.finanstilsynet.no/api/v2/instruments";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        Gson g = new Gson();
        DataImportDto[] dataImport = g.fromJson(response.getBody(), DataImportDto[].class);

        for (DataImportDto dataImportDto : dataImport) {
            parseInstance(dataImportDto);
        }
    }

    public int parseInstance(DataImportDto o) {
        try {
            Instrument instrument = instrumentRepository.getInstrumentByIsin(o.isin);
            if(instrument != null) {
                instrument = instrumentRepository.save(new Instrument(o.isin, o.issuerName));
            }

            o.events.sort(Comparator.comparing(a -> a.date));

            for(var event : o.events) {
                List<String> activePositions = new ArrayList<>();
                event.activePositions.forEach(x -> activePositions.add(x.positionHolder));

                int nActivePositions = event.activePositions.size();
                List<ShortPosition> registeredActivePositions = shortPositionRepository.findAllByActiveAndInstrument(true, instrument);
                int nRegisteredActivePositions = registeredActivePositions.size();

                if (nActivePositions < nRegisteredActivePositions) {
                    for(ShortPosition shortPos : registeredActivePositions) {
                        if(!activePositions.contains(shortPos.getShorterCompanyName()) ) {
                            shortPos.close(event.date.toInstant().atOffset(ZoneOffset.UTC));
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
                        shortPosition = shortPositionRepository.save(new ShortPosition(instrument, shorter, activePosition.date.toInstant().atOffset(ZoneOffset.UTC)));
                    } else {
                        shortPosition = shortPositionRepository.getByShorterAndInstrumentAndActive(shorter, instrument, true);
                    }

                    ShortPositionHistory shortPositionHistory;
                    if(!shortPositionHistoryRepository.existsByDateAndShortPosition(activePosition.date.toInstant().atOffset(ZoneOffset.UTC), shortPosition)) {
                        shortPositionHistory = shortPositionHistoryRepository.save(new ShortPositionHistory(shortPosition, activePosition.date.toInstant().atOffset(ZoneOffset.UTC), activePosition.shares, activePosition.shortPercent));
                    } else {
                        shortPositionHistory = shortPositionHistoryRepository.getByDateAndShortPosition(activePosition.date.toInstant().atOffset(ZoneOffset.UTC), shortPosition);
                    }

                    /* ShortPositionHistory newest entry **/
                    OffsetDateTime newestDate = shortPositionHistoryRepository.getDistinctFirstByShortPositionOrderByDate(shortPosition).getDate();
//                    System.out.println("For " + shorter.getCompanyName() + " and " + instrument.getIssuerName() + " .The newest date is: " + newestDate);

                }
            }

            return 201;
        } catch(Exception e) {
            System.out.println(e);
            return 500;
        }
    }



}
