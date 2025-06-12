package com.pet.social.zootaxi.service;

import com.pet.social.zootaxi.dto.RideDTO;
import com.pet.social.zootaxi.model.Ride;
import com.pet.social.zootaxi.repository.RideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepository;

    public List<Ride> getAllRides() {
        return rideRepository.findAll();
    }

    public Ride getRideById(Long id) {
        return rideRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ride not found with id: " + id));
    }

    public List<Ride> getRidesByOwnerId(Long ownerId) {
        return rideRepository.findByOwnerId(ownerId);
    }

    public Ride createRide(RideDTO dto) {
        Ride ride = Ride.builder()
                .ownerId(dto.getOwnerId())
                .origin(dto.getOrigin())
                .destination(dto.getDestination())
                .departureTime(dto.getDepartureTime())
                .status(dto.getStatus() != null ? dto.getStatus() : "PLANNED")
                .build();

        return rideRepository.save(ride);
    }

    public Ride updateRide(Long id, RideDTO dto) {
        Ride ride = getRideById(id);

        ride.setOrigin(dto.getOrigin());
        ride.setDestination(dto.getDestination());
        ride.setDepartureTime(dto.getDepartureTime());
        ride.setStatus(dto.getStatus());

        return rideRepository.save(ride);
    }

    public void deleteRide(Long id) {
        Ride ride = getRideById(id);
        rideRepository.delete(ride);
    }
}
