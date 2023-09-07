package com.huy.appnoithat.Service.UsersManagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.appnoithat.Entity.Account;
import com.huy.appnoithat.Entity.PhongCachNoiThat;
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

    public List<Account> findAllAccountEnable(){
        token = this.sessionService.getSession().getJwtToken();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        String response2 = this.webClientService.authorizedHttpGetJson("/api/accounts/enabled", token);
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

    public void enableAccount(int id){
        token = this.sessionService.getSession().getJwtToken();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        objectMapper = new ObjectMapper();
        this.webClientService.authorizedHttpPutJson("/api/accounts/enable/"+id,  "long",token);
    }
    public List<Account> findAllNotEnabledAccount(){
        token = this.sessionService.getSession().getJwtToken();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        String response2 = this.webClientService.authorizedHttpGetJson("/api/accounts/notEnabled", token);
        objectMapper = new ObjectMapper();
        try {
            tempAccountList = objectMapper.readValue(response2, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, Account.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempAccountList;
    }

    public void deleteAccount(int id){
        token = this.sessionService.getSession().getJwtToken();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        objectMapper = new ObjectMapper();
        this.webClientService.authorizedHttpDeleteJson("/api/accounts/"+id,  "",token);
    }
    public Account findAccountById(int id){
//        return tempAccountList.stream().filter(account -> id == account.getId()).findFirst().orElse(null);
        Account account = new Account();
        token = this.sessionService.getSession().getJwtToken();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        String response2 = this.webClientService.authorizedHttpGetJson("/api/accounts/"+id, token);
        objectMapper = new ObjectMapper();
        try {
            account = objectMapper.readValue(response2,Account.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return account;
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

    public void EditAccount(Account account){
        token = this.sessionService.getSession().getJwtToken();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        objectMapper = new ObjectMapper();
        try {
            this.webClientService.authorizedHttpPutJson("/api/accounts",  objectMapper.writeValueAsString(account),token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ActiveAccount(int id){
        token = this.sessionService.getSession().getJwtToken();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        objectMapper = new ObjectMapper();
        this.webClientService.authorizedHttpPutJson("/api/accounts/activate/"+id,  " ",token);
    }

    public void InActiveAccount(int id){
        token = this.sessionService.getSession().getJwtToken();
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        objectMapper = new ObjectMapper();
        this.webClientService.authorizedHttpPutJson("/api/accounts/deactivate/"+id,  " ",token);
    }
}
