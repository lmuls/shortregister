package com.example.shortregister_spring.controller;

import com.example.shortregister_spring.model.Instrument;
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

import java.util.Arrays;
import java.util.List;

@RestController
public class GetDataController {

    @Autowired
    InstrumentRepository instrumentRepository;

    @Autowired
    ShorterRepository shorterRepository;

    @Autowired
    ShortPositionRepository shortPositionRepository;

    @Autowired
    ShortPositionHistoryRepository shortPositionHistoryRepository;


    public int parseInstance(DataImportDto o) {
        try {
            instrumentRepository.save(new Instrument(o.isin, o.issuerName));

            for(var event : o.events) {
                for(var activePosition: event.activePositions) {
                    int shorterId = shorterRepository.save(new Shorter(activePosition.positionHolder)).getId();

//                    int shorterId = shorterRepository.
                }
            }

            return 201;
        } catch(Exception e) {
            System.out.println(e);
            return 500;
        }

    }


    @GetMapping("/get-data")
    public String getExternalData() {
        String uri = "https://ssr.finanstilsynet.no/api/v2/instruments";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

        Gson g = new Gson();

        DataImportDto[] dataImport = g.fromJson(response.getBody(), DataImportDto[].class);

        for(int i = 0; i < 2; i++) {
            System.out.println(dataImport[i].isin);
//            System.out.println(instrumentRepository.save(new Instrument(dataImport[i].isin, dataImport[i].issuerName)));
            parseInstance(dataImport[i]);
        }

        return dataImport[0].toString();


//        Object[] objects = restTemplate.getForObject(uri, Object[].class);
//        Object obj1 = objects[0];
//        return List.of(obj1);
    }
}
