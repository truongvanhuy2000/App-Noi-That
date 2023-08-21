package com.huy.appnoithat.Service.WebClient;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WebClientServiceTest {
    private WebClientService webClientService;
    @BeforeEach
    void setUp() {
        webClientService = new WebClientService();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void randomPost() {
        webClientService.randomPost();
    }
}