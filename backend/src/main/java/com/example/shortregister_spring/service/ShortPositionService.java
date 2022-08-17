package com.example.shortregister_spring.service;

import com.example.shortregister_spring.model.Instrument;
import com.example.shortregister_spring.model.ShortPosition;
import com.example.shortregister_spring.model.ShortPositionHistory;
import com.example.shortregister_spring.model.dto.ShortPositionDto;
import com.example.shortregister_spring.repository.ShortPositionHistoryRepository;
import com.example.shortregister_spring.repository.ShortPositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShortPositionService {
    private final ShortPositionRepository shortPositionRepository;

    public List<ShortPosition> getShortPositions(String issuerName) {
        return shortPositionRepository.getByInstrument_IssuerName(issuerName);
    }

    public List<ShortPositionDto> listShortPositionsForIssuer(String isin) {
        List<ShortPosition> shortPositions = shortPositionRepository.getByInstrument(new Instrument(isin, ""));
        return shortPositions.stream().map(ShortPositionDto::to).collect(Collectors.toList());
    }


    public ShortPositionService(ShortPositionRepository shortPositionRepository, ShortPositionHistoryRepository shortPositionHistoryRepository) {
        this.shortPositionRepository = shortPositionRepository;
    }
}
