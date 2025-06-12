package com.pet.social.zootaxi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RideDTO {
    private Long ownerId;
    private String origin;
    private String destination;
    private LocalDateTime departureTime;
    private String status;
}
