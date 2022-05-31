package com.example.shortregister_spring.model.dto;

import com.example.shortregister_spring.model.Instrument;
import com.example.shortregister_spring.model.ShortPosition;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.util.List;

@Data
public class InstrumentDto {
    String isin;
    String issuerName;

    public static InstrumentDto convertEntityToDto(Instrument instrument) {
        InstrumentDto instrumentDto = new InstrumentDto();
        instrumentDto.setIsin(instrument.getIsin());
        instrumentDto.setIssuerName(instrument.getIssuerName());
        return instrumentDto;
    }
}
