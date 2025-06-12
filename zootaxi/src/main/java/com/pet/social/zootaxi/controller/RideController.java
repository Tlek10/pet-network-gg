package com.pet.social.zootaxi.controller;

import com.pet.social.zootaxi.dto.RideDTO;
import com.pet.social.zootaxi.model.Ride;
import com.pet.social.zootaxi.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @GetMapping
    public ResponseEntity<List<Ride>> getAllRides(@RequestParam(value = "ownerId", required = false) Long ownerId) {
        if (ownerId != null) {
            return ResponseEntity.ok(rideService.getRidesByOwnerId(ownerId));
        }
        return ResponseEntity.ok(rideService.getAllRides());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ride> getRideById(@PathVariable Long id) {
        return ResponseEntity.ok(rideService.getRideById(id));
    }

    @PostMapping
    public ResponseEntity<Ride> createRide(@RequestBody RideDTO dto) {
        return ResponseEntity.ok(rideService.createRide(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Ride> updateRide(@PathVariable Long id, @RequestBody RideDTO dto) {
        return ResponseEntity.ok(rideService.updateRide(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRide(@PathVariable Long id) {
        rideService.deleteRide(id);
        return ResponseEntity.noContent().build();
    }
}
