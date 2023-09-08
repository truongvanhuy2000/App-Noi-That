package com.huy.appnoithat.Service.Login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.appnoithat.Entity.Account;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class LoginService {
    // Just let them in :))
    private List<Account> accounts;
    private WebClientService webClientService;
    private ObjectMapper objectMapper;
    private String token;

    private final UserSessionService sessionService = new UserSessionService();

    public LoginService() {
        accounts = new ArrayList<>();


    }
    public boolean Authorization(String username, String password) {
//        Account tempAccount = accounts.stream()
//                .filter(account -> account.getUsername().equals(username) && account.getPassword().equals(password))
//                .findFirst().orElse(null);
//        if ( tempAccount != null) {
//            sessionService.setSession(tempAccount, ":))");
//
//            return true;
//        }
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        objectMapper = new ObjectMapper();
        Account account = new Account(0, username, password, true, null,null, false);
        try {
            token = webClientService.unauthorizedHttpPostJson("/api/login", objectMapper.writeValueAsString(account));
            if (!token.isEmpty()){
                this.sessionService.setSession(account,token);
                return true;
            }
        } catch (RuntimeException | JsonProcessingException e) {
            return false;
        }
        return false;
    }


}
