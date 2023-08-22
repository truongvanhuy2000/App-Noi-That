package com.huy.appnoithat.Service.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.appnoithat.Entity.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.Authenticator;

import static org.junit.jupiter.api.Assertions.*;

class WebClientServiceTest {
    private WebClientService webClientService;
    private ObjectMapper objectMapper;
    private String token;
    @BeforeEach
    void setUp() {
        webClientService = new WebClientService("http://localhost:8080", 10);
        objectMapper = new ObjectMapper();
        Account account = new Account(1, "admin", "admin", 1, null, null);
        try {
            token = webClientService.unauthorizeHttpPostJson("/api/login", objectMapper.writeValueAsString(account));
            System.out.println(token);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAccounts() {
        String response2 = webClientService.authorizeHttpGetJson("/api/accounts", token);
        System.out.println(response2);
    }

    @Test
    void getPhongCachs() {
        String response = webClientService.authorizeHttpGetJson("/api/phongcach", token);
        System.out.println(response);
    }

}