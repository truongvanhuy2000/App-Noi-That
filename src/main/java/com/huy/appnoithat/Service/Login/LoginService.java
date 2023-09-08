package com.huy.appnoithat.Service.Login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.appnoithat.Entity.Account;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.UsersManagement.UsersManagementService;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class LoginService {
    final static Logger LOGGER = LogManager.getLogger(LoginService.class);
    private final WebClientService webClientService;
    private UsersManagementService usersManagementService;
    private final ObjectMapper objectMapper;
    private final UserSessionService sessionService;


    public LoginService() {
        this.webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        this.usersManagementService = new UsersManagementService();
        this.objectMapper = new ObjectMapper();
        this.sessionService = new UserSessionService();
    }
    public boolean Authorization(String username, String password) {

        Account account = new Account(0, username, password, true, null,null, false);
        try {
            String token = webClientService.unauthorizedHttpPostJson("/api/login", objectMapper.writeValueAsString(account));
            if (!token.isEmpty()){
                this.sessionService.setToken(token);
                Account loginAccount = this.usersManagementService.findAccountByUsername(username);
                this.sessionService.setLoginAccount(loginAccount);
                return true;
            }
        } catch (RuntimeException | JsonProcessingException e) {
            LOGGER.error("Login failed");
            return false;
        }
        return false;
    }


}
