package com.pet.social.user.controller;

import com.pet.social.user.dto.UserProfileDTO;
import com.pet.social.user.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    // 🔹 Получение всех пользователей
    @GetMapping
    public ResponseEntity<List<UserProfileDTO>> getAllUsers() {
        return ResponseEntity.ok(userProfileService.getAllProfiles());
    }

    // 🔹 Получение конкретного пользователя по имени
    @GetMapping("/{username}")
    public ResponseEntity<UserProfileDTO> getUser(@PathVariable String username) {
        return ResponseEntity.ok(userProfileService.getByUsername(username));
    }

    // 🔹 Создание/Обновление профиля для авторизованного пользователя
    @PutMapping("/{username}")
    public ResponseEntity<UserProfileDTO> updateUser(@PathVariable String username,
                                                     @RequestBody UserProfileDTO dto,
                                                     @AuthenticationPrincipal User currentUser) {
        // Защита: пользователь может обновлять только свой профиль
        if (!currentUser.getUsername().equals(username)) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(userProfileService.updateUserProfile(username, dto));
    }

    // 🔹 Удаление пользователя
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username,
                                           @AuthenticationPrincipal User currentUser) {
        if (!currentUser.getUsername().equals(username)) {
            return ResponseEntity.status(403).build();
        }

        userProfileService.deleteUserProfile(username);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Добавить друга
    @PostMapping("/{username}/friends/{friendUsername}")
    public ResponseEntity<UserProfileDTO> addFriend(@PathVariable String username,
                                                    @PathVariable String friendUsername,
                                                    @AuthenticationPrincipal User currentUser) {
        if (!currentUser.getUsername().equals(username)) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(userProfileService.addFriend(username, friendUsername));
    }

    // 🔹 Удалить друга
    @DeleteMapping("/{username}/friends/{friendUsername}")
    public ResponseEntity<UserProfileDTO> removeFriend(@PathVariable String username,
                                                       @PathVariable String friendUsername,
                                                       @AuthenticationPrincipal User currentUser) {
        if (!currentUser.getUsername().equals(username)) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(userProfileService.removeFriend(username, friendUsername));
    }
}
