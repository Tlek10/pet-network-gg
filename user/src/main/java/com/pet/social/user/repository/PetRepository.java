package com.pet.social.user.repository;

import com.pet.social.user.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByOwnerUsername(String username);
}
