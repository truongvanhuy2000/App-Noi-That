package com.huy.appnoithat.Service.RestService;

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
import java.util.ArrayList;
import java.util.List;

public class AccountRestService {
    final static Logger LOGGER = LogManager.getLogger(AccountRestService.class);
    private static AccountRestService instance;
    private final UserSessionService sessionService;
    private final ObjectMapper objectMapper;
    private final WebClientService webClientService;
    public static synchronized AccountRestService getInstance() {
        if (instance == null) {
            instance = new AccountRestService();
        }
        return instance;
    }

    private AccountRestService() {
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

    public Account findByUsername(String username) {
        String token = this.sessionService.getToken();
        String response = this.webClientService.authorizedHttpGetJson("/api/accounts/search?username=" + username, token);
        try {
            return this.objectMapper.readValue(response, Account.class);
        } catch (IOException e) {
            LOGGER.error("Error when find account by username: " + username);
            throw new RuntimeException(e);
        }
    }

    public Account findById(int id) {
        String token = this.sessionService.getToken();
        String response = this.webClientService.authorizedHttpGetJson("/api/accounts/" + id, token);
        try {
            return this.objectMapper.readValue(response, Account.class);
        } catch (IOException e) {
            LOGGER.error("Error when find account by id: " + id);
            throw new RuntimeException(e);
        }
    }
    public void save(Account account) {
        String token = this.sessionService.getToken();
        try {
            this.webClientService.authorizedHttpPostJson("/api/accounts", this.objectMapper.writeValueAsString(account), token);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error when save account: " + account.getUsername());
            throw new RuntimeException(e);
        }
    }
    public void update(Account account) {
        String token = this.sessionService.getToken();
        try {
            this.webClientService.authorizedHttpPutJson("/api/accounts/" + account.getId(), this.objectMapper.writeValueAsString(account), token);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error when update account: " + account.getUsername());
            throw new RuntimeException(e);
        }
    }
    public void deleteById(int id) {
        String token = this.sessionService.getToken();
        this.webClientService.authorizedHttpDeleteJson("/api/accounts/" + id, "", token);
    }
    public void activateAccount(int id) {
        String token = this.sessionService.getToken();
        this.webClientService.authorizedHttpPutJson("/api/accounts/activate/" + id, " ", token);
    }
    public void deactivateAccount(int id) {
        String token = this.sessionService.getToken();
        this.webClientService.authorizedHttpPutJson("/api/accounts/deactivate/" + id, "", token);
    }
    public List<Account> findAllEnabledAccount() {
        String token = this.sessionService.getToken();
        String response2 = this.webClientService.authorizedHttpGetJson("/api/accounts/enabled", token);
        try {
            return this.objectMapper.readValue(response2, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, Account.class));
        } catch (IOException e) {
            LOGGER.error("Can't parse response from server when get all account");
            throw new RuntimeException(e);
        }
    }
    public List<Account> findAllNotEnabledAccount() {
        String token = this.sessionService.getToken();
        String response2 = this.webClientService.authorizedHttpGetJson("/api/accounts/notEnabled", token);
        try {
            return this.objectMapper.readValue(response2, this.objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, Account.class));
        } catch (IOException e) {
            LOGGER.error("Can't parse response from server when get all account");
            throw new RuntimeException(e);
        }
    }
    public void enableAccount(int id) {
        String token = this.sessionService.getToken();
        this.webClientService.authorizedHttpPutJson("/api/accounts/enable/" + id, "long", token);
    }
    public String sessionCheck() {
        String token = this.sessionService.getToken();
        return webClientService.authorizedHttpGetJson("/api/index", token);
    }
}
