package com.huy.appnoithat.Service.UsersManagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.appnoithat.Entity.Account;
import com.huy.appnoithat.Entity.PhongCachNoiThat;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsersManagementService {
    private List<Account> tempAccountList = new ArrayList<>();
    final static Logger LOGGER = LogManager.getLogger(UsersManagementService.class);
    private final WebClientService webClientService;
    private final ObjectMapper objectMapper;
    private final UserSessionService sessionService = new UserSessionService();

    public UsersManagementService() {
        this.webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
        this.objectMapper = new ObjectMapper();
    }

    public List<Account> findAllAccountEnable(){
        String token = this.sessionService.getSession().getJwtToken();

        String response2 = this.webClientService.authorizedHttpGetJson("/api/accounts/enabled", token);
        try {
            tempAccountList = this.objectMapper.readValue(response2, objectMapper.getTypeFactory()
                .constructCollectionType(List.class, Account.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempAccountList;
    }

    public void enableAccount(int id){
        String token = this.sessionService.getSession().getJwtToken();
        this.webClientService.authorizedHttpPutJson("/api/accounts/enable/"+id,  "long",token);
    }
    public List<Account> findAllNotEnabledAccount(){
        String token = this.sessionService.getSession().getJwtToken();
        String response2 = this.webClientService.authorizedHttpGetJson("/api/accounts/notEnabled", token);
        try {
            tempAccountList = this.objectMapper.readValue(response2, this.objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, Account.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempAccountList;
    }

    public void deleteAccount(int id){
        String token = this.sessionService.getSession().getJwtToken();
        this.webClientService.authorizedHttpDeleteJson("/api/accounts/"+id,  "",token);
    }
    public Account findAccountById(int id){
//        return tempAccountList.stream().filter(account -> id == account.getId()).findFirst().orElse(null);
        Account account = new Account();
        String token = this.sessionService.getSession().getJwtToken();
        String response2 = this.webClientService.authorizedHttpGetJson("/api/accounts/"+id, token);
        try {
            account = this.objectMapper.readValue(response2,Account.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return account;
    }

    public void addNewAccount(Account account){
        String token = this.sessionService.getSession().getJwtToken();
        try {
            this.webClientService.authorizedHttpPostJson("/api/accounts",  this.objectMapper.writeValueAsString(account),token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void EditAccount(Account account){
        String token = this.sessionService.getSession().getJwtToken();
        try {
            this.webClientService.authorizedHttpPutJson("/api/accounts",  this.objectMapper.writeValueAsString(account),token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ActiveAccount(int id){
        String token = this.sessionService.getSession().getJwtToken();
        this.webClientService.authorizedHttpPutJson("/api/accounts/activate/"+id,  " ",token);
    }

    public void InActiveAccount(int id){
        String token = this.sessionService.getSession().getJwtToken();
        this.webClientService.authorizedHttpPutJson("/api/accounts/deactivate/"+id,  " ",token);
    }

    public Account findAccountByUsername(String username) {
        String token = this.sessionService.getSession().getJwtToken();
        String response = this.webClientService.authorizedHttpGetJson("/api/accounts/search?username="+username, token);
        try {
            return this.objectMapper.readValue(response, Account.class);
        } catch (IOException e) {
            LOGGER.error("Error when find account by username");
            throw new RuntimeException(e);
        }
    }
}
