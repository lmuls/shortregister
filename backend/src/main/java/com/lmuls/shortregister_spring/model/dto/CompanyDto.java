package com.lmuls.shortregister_spring.model.dto;

import java.util.List;

public class CompanyDto {

    public String isin;
    public String issuerName;
    public List<EventsDto> events;

    public String toString() {
        return this.isin + " " + this. issuerName + " " + this.events;
    }

}
