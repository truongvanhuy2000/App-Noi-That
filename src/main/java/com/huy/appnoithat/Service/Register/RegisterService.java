package com.huy.appnoithat.Service.Register;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Entity.Account;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class RegisterService {
    final static Logger LOGGER = LogManager.getLogger(RegisterService.class);
    private final WebClientService webClientService;
    private final ObjectMapper objectMapper;
    private final UserSessionService sessionService;

    public RegisterService() {
        webClientService = new WebClientServiceImpl();
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        sessionService = new UserSessionService();
    }

    public void registerNewAccount(Account account) {
        String token = this.sessionService.getToken();
        try {
            this.webClientService.authorizedHttpPostJson("/api/register", objectMapper.writeValueAsString(account), token);
        } catch (IOException e) {
            LOGGER.error("Can't parse response from server when register new account");
        }
    }
}
