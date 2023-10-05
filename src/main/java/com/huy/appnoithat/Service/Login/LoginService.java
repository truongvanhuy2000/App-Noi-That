package com.huy.appnoithat.Service.Login;

import com.fasterxml.jackson.core.JsonProcessingException;
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

public class LoginService {
    final static Logger LOGGER = LogManager.getLogger(LoginService.class);
    private final WebClientService webClientService;
    private final ObjectMapper objectMapper;
    private final UserSessionService sessionService;

    public LoginService() {
        this.webClientService = new WebClientServiceImpl();
        this.objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        this.sessionService = new UserSessionService();
    }

    public boolean Authorization(String username, String password) {

        Account account = new Account(0, username, password, true, null, null, false, null);
        try {
            String token = webClientService.unauthorizedHttpPostJson("/api/login", objectMapper.writeValueAsString(account));
            if (token != null) {
                this.sessionService.setSession(username, token);
                this.sessionService.saveSessionToDisk();
                return true;
            }
        } catch (JsonProcessingException e) {
            LOGGER.error("Login failed");
            return false;
        } catch (IOException e) {
            LOGGER.error("Cant save session to disk");
            throw new RuntimeException(e);
        }
        return false;
    }


}
