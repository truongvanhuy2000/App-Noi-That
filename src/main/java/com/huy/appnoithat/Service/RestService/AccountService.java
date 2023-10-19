package com.huy.appnoithat.Service.RestService;

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

public class AccountService {
    final static Logger LOGGER = LogManager.getLogger(AccountService.class);
    private static AccountService instance;
    private final UserSessionService sessionService;
    private final ObjectMapper objectMapper;
    private final WebClientService webClientService;
    public static synchronized AccountService getInstance() {
        if (instance == null) {
            instance = new AccountService();
        }
        return instance;
    }

    public AccountService() {
        this.sessionService = new UserSessionService();;
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        webClientService = new WebClientServiceImpl();
    }

    public Account getAccountInformation() {
        String token = this.sessionService.getToken();
        String response = this.webClientService.authorizedHttpGetJson("/api/info?username=" + this.sessionService.getUsername(), token);
        try {
            return this.objectMapper.readValue(response, Account.class);
        } catch (IOException e) {
            LOGGER.error("Error when find account by username: " + this.sessionService.getUsername());
            throw new RuntimeException(e);
        }
    }
}
