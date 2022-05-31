package com.example.shortregister_spring.model.dto;

import com.example.shortregister_spring.model.Shorter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShorterDto {
    int id;
    String companyName;

    public static ShorterDto convertEntityToDto(Shorter shorter) {
        ShorterDto shorterDto = new ShorterDto();
        shorterDto.setId(shorter.getId());
        shorterDto.setCompanyName(shorter.getCompanyName());
        return shorterDto;
    }
}
