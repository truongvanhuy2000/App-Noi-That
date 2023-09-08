package com.huy.appnoithat.Service.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.appnoithat.Entity.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class WebClientServiceImplTest {
    private WebClientService webClientService;
    private ObjectMapper objectMapper;
    private String token;
    @BeforeEach
    void setUp() {
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        objectMapper = new ObjectMapper();
        Account account = new Account(1, "admin", "admin", true, null, new ArrayList<>(), true);
        try {
            token = webClientService.unauthorizedHttpPostJson("/api/login", objectMapper.writeValueAsString(account));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAccounts() {
        String response2 = webClientService.authorizedHttpGetJson("/api/accounts", token);
        System.out.println(response2);
    }

    @Test
    void getPhongCachs() {
        String response = webClientService.authorizedHttpGetJson("/api/phongcach", token);
        System.out.println(response);
    }


    @Test
    void unauthorizeHttpPostJson() {
    }

    @Test
    void unauthorizeHttpGetJson() {
    }

    @Test
    void authorizeHttpPostJson() {
    }

    @Test
    void authorizeHttpGetJson() {
    }

    @Test
    void authorizeHttpPutJson() {
    }

    @Test
    void authorizeHttpDeleteJson() {
    }
}