package com.pet.social.zootaxi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ownerId; // ID владельца поездки (можно связать с профилем)

    private String origin;
    private String destination;

    private LocalDateTime departureTime;

    private String status;
}
