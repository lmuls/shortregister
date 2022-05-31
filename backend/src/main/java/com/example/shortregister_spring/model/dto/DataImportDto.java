package com.example.shortregister_spring.model.dto;

import java.util.List;

public class DataImportDto {

    public String isin;
    public String issuerName;
    public List<EventsDto> events;

    public String toString() {
        return this.isin + " " + this. issuerName + " " + this.events;
    }

}
