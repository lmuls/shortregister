package com.example.shortregister_spring.model.dto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

public class EventsDto {

    public Date date;
    public int shares;
    public List<ActivePositionsDto> activePositions;
}
