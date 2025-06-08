package com.pet.social.user.service;

import com.pet.social.user.dto.PetDTO;
import com.pet.social.user.exception.NotFoundException;
import com.pet.social.user.model.Pet;
import com.pet.social.user.model.UserProfile;
import com.pet.social.user.repository.PetRepository;
import com.pet.social.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final UserProfileRepository userProfileRepository;
    private final JwtUtil jwtUtil;

    public Pet createPet(PetDTO dto) {
        String username = getCurrentUsername();
        UserProfile owner = userProfileRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Owner not found"));

        Pet pet = Pet.builder()
                .name(dto.getName())
                .type(dto.getType())
                .age(dto.getAge())
                .owner(owner)
                .build();

        return petRepository.save(pet);
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwner(String username) {
        return petRepository.findByOwnerUsername(username);
    }

    public Pet updatePet(Long id, PetDTO dto) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet not found"));

        pet.setName(dto.getName());
        pet.setType(dto.getType());
        pet.setAge(dto.getAge());

        return petRepository.save(pet);
    }

    public void deletePet(Long id) {
        if (!petRepository.existsById(id)) {
            throw new NotFoundException("Pet not found");
        }
        petRepository.deleteById(id);
    }

    private String getCurrentUsername() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("Authentication credentials are not available");
        }
        return authentication.getName();
    }

}