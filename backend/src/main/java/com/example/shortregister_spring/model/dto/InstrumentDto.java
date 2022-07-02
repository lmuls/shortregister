package com.example.shortregister_spring.model.dto;

import com.example.shortregister_spring.model.Instrument;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class InstrumentDto {
    String isin;
    String issuerName;
    List<ShortPositionDto> shortPositions;

    public static InstrumentDto to(Instrument instrument) {
        InstrumentDto instrumentDto = new InstrumentDto();
        instrumentDto.setIsin(instrument.getIsin());
        instrumentDto.setIssuerName(instrument.getIssuerName());
        instrumentDto.setShortPositions(instrument.getShortPositions().stream().map(ShortPositionDto::to).collect(Collectors.toList()));
        return instrumentDto;
    }
}
