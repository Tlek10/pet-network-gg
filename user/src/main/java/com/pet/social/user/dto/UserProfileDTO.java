package com.pet.social.user.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserProfileDTO {
    private String username;
    private String bio;
    private String location;
    private String avatarUrl;

    private Set<PetDTO> pets = new HashSet<>();
    private Set<String> friends = new HashSet<>();
}
