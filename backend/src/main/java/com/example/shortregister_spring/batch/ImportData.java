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

import java.util.ArrayList;
import java.util.Date;
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

        for(int i = 0; i < dataImport.length; i++) {
            parseInstance(dataImport[i]);
        }
    }

    public int parseInstance(DataImportDto o) {
        try {
            Instrument _instrument = new Instrument(o.isin, o.issuerName);

            if( !instrumentRepository.existsById(_instrument.getIsin())) {
                instrumentRepository.save(new Instrument(o.isin, o.issuerName));
            }

            o.events.sort((a,b) -> a.date.compareTo(b.date));

            for(var event : o.events) {
                List<String> activePositions = new ArrayList<>();
                event.activePositions.forEach(x -> activePositions.add(x.positionHolder));

                System.out.println(activePositions);

                int nActivePositions = event.activePositions.size();
                List<ShortPosition> registeredActivePositions = shortPositionRepository.findAllByActiveAndInstrument(true, _instrument);
                int nRegisteredActivePositions = registeredActivePositions.size();

                System.out.println(_instrument.getIssuerName() + " Has " + nRegisteredActivePositions + " registered active pos and " + nActivePositions + " no of new active pos. " + event.date + "\n");

                if (nActivePositions < nRegisteredActivePositions) {
                    for(ShortPosition shortPos : registeredActivePositions) {
                        if(!activePositions.contains(shortPos.getShorterCompanyName()) ) {
                            System.out.println(shortPos.getShorterCompanyName() + " was deleted! +\n");
                            shortPos.close(event.date);
                        }
                    }
                    System.out.println("NO POSITIONS NOT EQUAL");
                }

                for(var activePosition: event.activePositions) {

                    Shorter shorter;
                    if(!shorterRepository.existsByCompanyName(activePosition.positionHolder)) {
                        shorter = shorterRepository.save(new Shorter(activePosition.positionHolder));
                    } else {
                        shorter = shorterRepository.getByCompanyName(activePosition.positionHolder);
                    }

//                    try {
//                        ShortPosition newestPos = shortPositionRepository.getDistinctTopByShorterAndInstrumentOrderByClosed(shorter, _instrument);
//                        List<ShortPositionHistory> shortPositionHistories = newestPos.getShortPositionHistories();
//
//                        shortPositionHistories.sort((a,b) -> b.getDate().compareTo(a.getDate()));
//                        Date newestShortPositionHistoryEntry = shortPositionHistories.get(0).getDate();
//                        System.out.println("Newest date is: " + newestShortPositionHistoryEntry + ". activePos date is: " + activePosition.date);
//                        if(activePosition.date.compareTo(newestShortPositionHistoryEntry) < 0) {
//                            System.out.println("Going to the top");
//                            continue;
//                        }
//
//                    } catch(Exception e) {
//                        System.out.println(e);
//                    }

                    ShortPosition shortPosition;
                    /* Short position relation exists? */

//                    if(!shortPositionRepository.existsByShorterAndInstrument(shorter, _instrument)) {
//                        if(shortPositionRepository.existsByShorterAndInstrumentAndActive(shorter, _instrument, true)) {
//                            shortPosition = shortPositionRepository.getByShorterAndInstrumentAndActive(shorter, _instrument, true);
//                        } else {
//                            ShortPosition newestPos = shortPositionRepository.getDistinctTopByShorterAndInstrumentOrderByClosed(shorter, _instrument);
//                            if(newestPos.getClosed().compareTo(activePosition.date) < 0) {
//                                shortPosition = shortPositionRepository.save(new ShortPosition(_instrument, shorter, activePosition.date));
//                            } else {
//                                shortPosition = shortPositionRepository.getByShorterAndInstrumentAndActive(shorter, _instrument, true);
//                            }
//                        }
//                    } else {
//                        shortPosition = shortPositionRepository.save(new ShortPosition(_instrument, shorter, activePosition.date));
//                    }

                    if(!shortPositionRepository.existsByShorterAndInstrumentAndActive(shorter, _instrument, true)) {
                            shortPosition = shortPositionRepository.save(new ShortPosition(_instrument, shorter, activePosition.date));
                    } else {
                        shortPosition = shortPositionRepository.getByShorterAndInstrumentAndActive(shorter, _instrument, true);
                    }

                    ShortPositionHistory shortPositionHistory;
                    if(!shortPositionHistoryRepository.existsByDateAndShortPosition(activePosition.date, shortPosition)) {
                        shortPositionHistory = shortPositionHistoryRepository.save(new ShortPositionHistory(shortPosition, activePosition.date, activePosition.shares, activePosition.shortPercent));
                    } else {
                        shortPositionHistory = shortPositionHistoryRepository.getByDateAndShortPosition(activePosition.date, shortPosition);
                    }

                    /* ShortPositionHistory newest entry **/
                    Date newestDate = shortPositionHistoryRepository.getDistinctFirstByShortPositionOrderByDate(shortPosition).getDate();
//                    System.out.println("For " + shorter.getCompanyName() + " and " + _instrument.getIssuerName() + " .The newest date is: " + newestDate);

                }
            }

            return 201;
        } catch(Exception e) {
            System.out.println(e);
            return 500;
        }
    }



}
