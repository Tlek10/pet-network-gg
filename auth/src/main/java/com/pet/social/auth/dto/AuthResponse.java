package com.pet.social.auth.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String errorMessage;

    public AuthResponse(String token) {
        this.token = token;
        this.errorMessage = null;
    }
}