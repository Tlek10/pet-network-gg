package com.pet.social.user.util;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class AuthClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String AUTH_SERVICE_URL = "http://localhost:8081/api/auth/check/";

    public boolean checkUserExists(String username) {
        try {
            restTemplate.getForEntity(AUTH_SERVICE_URL + username, Void.class);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        }
    }
}
