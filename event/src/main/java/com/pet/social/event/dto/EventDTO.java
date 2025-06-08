package com.pet.social.event.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDTO {
    private String title;
    private String description;
    private String location;
    private LocalDateTime dateTime;
}