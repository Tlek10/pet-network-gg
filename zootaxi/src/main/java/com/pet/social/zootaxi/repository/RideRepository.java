package com.pet.social.zootaxi.repository;

import com.pet.social.zootaxi.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findByOwnerId(Long ownerId);
}
