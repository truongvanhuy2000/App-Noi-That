package com.huy.appnoithat.Service.RestService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.huy.appnoithat.Entity.Account;
import com.huy.appnoithat.Entity.AccountInformation;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountRestService {
    final static Logger LOGGER = LogManager.getLogger(AccountRestService.class);
    private static AccountRestService instance;
    private final UserSessionService sessionService;
    private final ObjectMapper objectMapper;
    private final WebClientService webClientService;
    private static final String BASE_ENDPOINT = "/api";
    public static synchronized AccountRestService getInstance() {
        if (instance == null) {
            instance = new AccountRestService();
        }
        return instance;
    }

    private AccountRestService() {
        this.sessionService = new UserSessionService();
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        webClientService = new WebClientServiceImpl();
    }

    public Account getAccountInformation() {
        String token = this.sessionService.getToken();
        String response = this.webClientService.authorizedHttpGetJson(BASE_ENDPOINT + "/info?username=" + this.sessionService.getUsername(), token);
        try {
            return this.objectMapper.readValue(response, Account.class);
        } catch (IOException e) {
            LOGGER.error("Error when find account by username: " + this.sessionService.getUsername());
            throw new RuntimeException(e);
        }
    }
    public Account getAccountInformation(String token) {
        String response = this.webClientService.authorizedHttpGetJson(BASE_ENDPOINT + "/info", token);
        try {
            return this.objectMapper.readValue(response, Account.class);
        } catch (IOException e) {
            LOGGER.error("Error when find account by username: " + this.sessionService.getUsername());
            throw new RuntimeException(e);
        }
    }

    public Account findByUsername(String username) {
        String token = this.sessionService.getToken();
        String response = this.webClientService.authorizedHttpGetJson(BASE_ENDPOINT + "/accounts/search?username=" + username, token);
        try {
            return this.objectMapper.readValue(response, Account.class);
        } catch (IOException e) {
            LOGGER.error("Error when find account by username: " + username);
            throw new RuntimeException(e);
        }
    }

    public Account findById(int id) {
        String token = this.sessionService.getToken();
        String response = this.webClientService.authorizedHttpGetJson(BASE_ENDPOINT + "/accounts/" + id, token);
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
            this.webClientService.authorizedHttpPostJson(BASE_ENDPOINT + "/accounts", this.objectMapper.writeValueAsString(account), token);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error when save account: " + account.getUsername());
            throw new RuntimeException(e);
        }
    }
    public void update(Account account) {
        String token = this.sessionService.getToken();
        try {
            this.webClientService.authorizedHttpPutJson(BASE_ENDPOINT + "/accounts/" + account.getId(),
                    this.objectMapper.writeValueAsString(account), token);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error when update account: " + account.getUsername());
            throw new RuntimeException(e);
        }
    }
    public void deleteById(int id) {
        String token = this.sessionService.getToken();
        this.webClientService.authorizedHttpDeleteJson(BASE_ENDPOINT + "/accounts/" + id, " ", token);
    }
    public void activateAccount(int id) {
        String token = this.sessionService.getToken();
        this.webClientService.authorizedHttpPutJson(BASE_ENDPOINT + "/accounts/activate/" + id, " ", token);
    }
    public void deactivateAccount(int id) {
        String token = this.sessionService.getToken();
        this.webClientService.authorizedHttpPutJson(BASE_ENDPOINT + "/accounts/deactivate/" + id, " ", token);
    }
    public List<Account> findAllEnabledAccount() {
        String token = this.sessionService.getToken();
        String response2 = this.webClientService.authorizedHttpGetJson(BASE_ENDPOINT + "/accounts/enabled", token);
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
        String response2 = this.webClientService.authorizedHttpGetJson(BASE_ENDPOINT + "/accounts/notEnabled", token);
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
        this.webClientService.authorizedHttpPutJson(BASE_ENDPOINT + "/accounts/enable/" + id, "long", token);
    }
    public String sessionCheck() {
        String token = this.sessionService.getToken();
        return webClientService.authorizedHttpGetJson(BASE_ENDPOINT + "/index", token);
    }
    public String login(String username, String password) {
        Account account = new Account(0, username, password,
                true, null, null, false, null);
        try {
            return webClientService.unauthorizedHttpPostJson(BASE_ENDPOINT + "/login", objectMapper.writeValueAsString(account));
        } catch (JsonProcessingException e) {
            LOGGER.error("Login failed");
            throw new RuntimeException(e);
        }
    }
    public void register(Account account) {
        String token = this.sessionService.getToken();
        try {
            this.webClientService.authorizedHttpPostJson(BASE_ENDPOINT + "/register",
                    objectMapper.writeValueAsString(account), token);
        } catch (IOException e) {
            LOGGER.error("Can't parse response from server when register new account");
        }
    }
    public boolean isUsernameValid(String username) {
        String response = this.webClientService.unauthorizedHttpGetJson("/api/register/usernameValidation?username=" + username);
        return response != null;
    }
    public boolean changePassword(String oldPassword, String newPassword) {
        String token = this.sessionService.getToken();
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("oldPassword", oldPassword);
        requestBody.put("newPassword", newPassword);
        try {
            String response = this.webClientService.authorizedHttpPutJson(BASE_ENDPOINT + "/changePassword",
                    objectMapper.writeValueAsString(requestBody), token);
            if (response != null) {
                return true;
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public boolean updateInformation(AccountInformation accountInformation) {
        String token = this.sessionService.getToken();
        try {
            String response = this.webClientService.authorizedHttpPutJson(BASE_ENDPOINT + "/updateInfo",
                    objectMapper.writeValueAsString(accountInformation), token);
            if (response != null) {
                return true;
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
