package com.huy.appnoithat.Service.UsersManagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.appnoithat.Entity.Account;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsersManagementService {
    private List<Account> tempAccountList = new ArrayList<>();

    private WebClientService webClientService;
    private ObjectMapper objectMapper;
    private String token;

    private final UserSessionService sessionService = new UserSessionService();

    public UsersManagementService() {
    }

    public List<Account> findAllAccount(){
        token = this.sessionService.getSession().getJwtToken();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        String response2 = this.webClientService.authorizedHttpGetJson("/api/accounts", token);
        objectMapper = new ObjectMapper();
        try {
            tempAccountList = objectMapper.readValue(response2, objectMapper.getTypeFactory()
                .constructCollectionType(List.class, Account.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempAccountList;
    }

    void deactivateAccount(int id){
        findAccountById(id).setActive(false);
    }

    void activateAccount(int id){
        findAccountById(id).setActive(false);
    }

    public void deleteAccount(int id){
        token = this.sessionService.getSession().getJwtToken();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        objectMapper = new ObjectMapper();
        this.webClientService.authorizedHttpDeleteJson("/api/accounts/"+id,  "",token);
    }
    Account findAccountById(int id){
        return tempAccountList.stream().filter(account -> id == account.getId()).findFirst().orElse(null);
    }

    public void addNewAccount(Account account){
        token = this.sessionService.getSession().getJwtToken();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        objectMapper = new ObjectMapper();
        try {
            this.webClientService.authorizedHttpPostJson("/api/accounts",  objectMapper.writeValueAsString(account),token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
