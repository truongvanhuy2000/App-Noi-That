package com.huy.appnoithat.Service.Register;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Entity.Account;
import com.huy.appnoithat.Entity.PhongCachNoiThat;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;

import java.io.IOException;

public class RegisterService {
    private WebClientService webClientService;
    private ObjectMapper objectMapper;
    private String token;

    private final UserSessionService sessionService = new UserSessionService();

    public RegisterService(){}

    public void registerNewAccount(Account account){
        token = this.sessionService.getToken();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();;
        try {
            this.webClientService.authorizedHttpPostJson("/api/register",  objectMapper.writeValueAsString(account),token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
