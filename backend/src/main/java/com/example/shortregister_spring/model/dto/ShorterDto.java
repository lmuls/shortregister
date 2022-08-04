package com.example.shortregister_spring.model.dto;

import com.example.shortregister_spring.model.ShortPosition;
import com.example.shortregister_spring.model.Shorter;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ShorterDto {
    int id;
    String companyName;
    List<ShortPositionDto> shortPositions;

    public static ShorterDto convertEntityToDto(Shorter shorter) {
        ShorterDto shorterDto = new ShorterDto();
        shorterDto.setId(shorter.getId());
        shorterDto.setCompanyName(shorter.getCompanyName());
        shorterDto.setShortPositions(shorter.getShortPositions().stream().map(ShortPositionDto::to).collect(Collectors.toList()));
        return shorterDto;
    }
}
