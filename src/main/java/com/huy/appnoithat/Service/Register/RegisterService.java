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

    /**
     * Initializes a new instance of the RegisterService class.
     * Initializes the WebClientService, ObjectMapper, and UserSessionService.
     */
    public RegisterService() {
        webClientService = new WebClientServiceImpl();
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        sessionService = new UserSessionService();
    }

    /**
     * Registers a new account by sending a POST request to the server.
     * Requires an authorized session token.
     *
     * @param account The Account object to register.
     */
    public void registerNewAccount(Account account) {
        String token = this.sessionService.getToken();
        try {
            this.webClientService.authorizedHttpPostJson("/api/register", objectMapper.writeValueAsString(account), token);
        } catch (IOException e) {
            LOGGER.error("Can't parse response from server when register new account");
        }
    }

    /**
     * Checks if the given username is valid by making an HTTP GET request to the server.
     *
     * @param username The username to validate.
     * @return True if the username is valid, false otherwise.
     */
    public boolean isUsernameValid(String username) {
        String response = this.webClientService.unauthorizedHttpGetJson("/api/register/usernameValidation?username=" + username);
        return response != null;
    }
}
