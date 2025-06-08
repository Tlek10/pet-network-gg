package com.pet.social.user.service;

import com.pet.social.user.dto.PetDTO;
import com.pet.social.user.dto.UserProfileDTO;
import com.pet.social.user.exception.NotFoundException;
import com.pet.social.user.model.UserProfile;
import com.pet.social.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    // ✅ Создание нового профиля (при первом входе по токену)
    public UserProfileDTO createUserProfile(UserProfileDTO dto) {
        boolean exists = userProfileRepository.findByUsername(dto.getUsername()).isPresent();
        if (exists) {
            throw new RuntimeException("UserProfile already exists: " + dto.getUsername());
        }

        UserProfile user = UserProfile.builder()
                .username(dto.getUsername())
                .bio(dto.getBio())
                .location(dto.getLocation())
                .avatarUrl(dto.getAvatarUrl())
                .build();

        userProfileRepository.save(user);
        return mapToDto(user);
    }

    // ✅ Получение всех профилей
    public List<UserProfileDTO> getAllProfiles() {
        return userProfileRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // ✅ Получение по имени
    public UserProfileDTO getByUsername(String username) {
        UserProfile user = userProfileRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found: " + username));
        return mapToDto(user);
    }

    // ✅ Обновление профиля
    public UserProfileDTO updateUserProfile(String username, UserProfileDTO dto) {
        UserProfile user = userProfileRepository.findByUsername(username)
                .orElseGet(() -> UserProfile.builder()
                        .username(username)
                        .bio(dto.getBio())
                        .location(dto.getLocation())
                        .avatarUrl(dto.getAvatarUrl())
                        .build());

        user.setBio(dto.getBio());
        user.setLocation(dto.getLocation());
        user.setAvatarUrl(dto.getAvatarUrl());

        userProfileRepository.save(user);
        return mapToDto(user);
    }

    // ✅ Удаление профиля
    public void deleteUserProfile(String username) {
        UserProfile user = userProfileRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found: " + username));
        userProfileRepository.delete(user);
    }

    // ✅ Добавление друга
    public UserProfileDTO addFriend(String username, String friendUsername) {
        if (username.equals(friendUsername)) {
            throw new RuntimeException("You can't add yourself as a friend");
        }

        UserProfile user = userProfileRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found: " + username));

        UserProfile friend = userProfileRepository.findByUsername(friendUsername)
                .orElseThrow(() -> new NotFoundException("Friend not found: " + friendUsername));

        user.getFriends().add(friend);
        userProfileRepository.save(user);

        return mapToDto(user);
    }

    // ✅ Удаление друга
    public UserProfileDTO removeFriend(String username, String friendUsername) {
        UserProfile user = userProfileRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found: " + username));

        UserProfile friend = userProfileRepository.findByUsername(friendUsername)
                .orElseThrow(() -> new NotFoundException("Friend not found: " + friendUsername));

        user.getFriends().remove(friend);
        userProfileRepository.save(user);

        return mapToDto(user);
    }

    // ✅ Вспомогательный метод маппинга
    private UserProfileDTO mapToDto(UserProfile user) {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setUsername(user.getUsername());
        dto.setBio(user.getBio());
        dto.setLocation(user.getLocation());
        dto.setAvatarUrl(user.getAvatarUrl());

        // Защита от null
        Set<PetDTO> petDtos = user.getPets() == null ? Collections.emptySet() :
                user.getPets().stream().map(pet -> {
                    PetDTO p = new PetDTO();
                    p.setName(pet.getName());
                    p.setType(pet.getType());
                    p.setAge(pet.getAge());
                    return p;
                }).collect(Collectors.toSet());

        Set<String> friends = user.getFriends() == null ? Collections.emptySet() :
                user.getFriends().stream()
                        .map(UserProfile::getUsername)
                        .collect(Collectors.toSet());

        dto.setPets(petDtos);
        dto.setFriends(friends);

        return dto;
    }
}
