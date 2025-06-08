package com.pet.social.user.controller;

import com.pet.social.user.dto.PetDTO;
import com.pet.social.user.model.Pet;
import com.pet.social.user.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @PostMapping
    public ResponseEntity<Pet> createPet(@RequestBody PetDTO dto) {
        return ResponseEntity.ok(petService.createPet(dto));
    }

    @GetMapping
    public ResponseEntity<List<Pet>> getAllPets() {
        return ResponseEntity.ok(petService.getAllPets());
    }

    @GetMapping("/owner/{username}")
    public ResponseEntity<List<Pet>> getPetsByOwner(@PathVariable String username) {
        return ResponseEntity.ok(petService.getPetsByOwner(username));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pet> updatePet(@PathVariable Long id, @RequestBody PetDTO dto) {
        return ResponseEntity.ok(petService.updatePet(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }
}