package com.huy.appnoithat.Service.UsersManagement;

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

public class UsersManagementService {
    final static Logger LOGGER = LogManager.getLogger(UsersManagementService.class);
    private final WebClientService webClientService;
    private final ObjectMapper objectMapper;
    private final UserSessionService sessionService;
    /**
     * Initializes the UsersManagementService with required dependencies.
     */
    public UsersManagementService() {
        this.webClientService = new WebClientServiceImpl();
        this.objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        sessionService = new UserSessionService();
    }


    /**
     * Retrieves a list of all enabled accounts from the server.
     *
     * @return List of enabled accounts.
     * @throws RuntimeException If there is an error while retrieving or parsing the response.
     */
    public List<Account> findAllAccountEnable() {
        String token = this.sessionService.getToken();
        List<Account> tempAccountList = new ArrayList<>();
        String response2 = this.webClientService.authorizedHttpGetJson("/api/accounts/enabled", token);
        try {
            tempAccountList = this.objectMapper.readValue(response2, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, Account.class));
        } catch (IOException e) {
            LOGGER.error("Can't parse response from server when get all account");
            throw new RuntimeException(e);
        }
        return tempAccountList;
    }

    /**
     * Enables an account with the specified ID on the server.
     *
     * @param id The ID of the account to be enabled.
     * @throws RuntimeException If there is an error while sending the request.
     */
    public void enableAccount(int id) {
        String token = this.sessionService.getToken();
        this.webClientService.authorizedHttpPutJson("/api/accounts/enable/" + id, "long", token);
    }

    /**
     * Retrieves a list of all accounts that are not enabled from the server.
     *
     * @return A list of Account objects representing the not enabled accounts.
     * @throws RuntimeException If there is an error while retrieving the data from the server.
     */
    public List<Account> findAllNotEnabledAccount() {
        String token = this.sessionService.getToken();
        List<Account> tempAccountList = new ArrayList<>();
        String response2 = this.webClientService.authorizedHttpGetJson("/api/accounts/notEnabled", token);
        try {
            tempAccountList = this.objectMapper.readValue(response2, this.objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, Account.class));
        } catch (IOException e) {
            LOGGER.error("Can't parse response from server when get all account");
            throw new RuntimeException(e);
        }
        return tempAccountList;
    }

    /**
     * Deletes an account with the specified ID from the server.
     *
     * @param id The ID of the account to be deleted.
     * @throws RuntimeException If there is an error while deleting the account from the server.
     */
    public void deleteAccount(int id) {
        String token = this.sessionService.getToken();
        this.webClientService.authorizedHttpDeleteJson("/api/accounts/" + id, "", token);
    }

    /**
     * Retrieves the account information for the specified account ID from the server.
     *
     * @param id The ID of the account to be retrieved.
     * @return The account object corresponding to the specified ID, or null if no account is found.
     * @throws RuntimeException If there is an error while retrieving the account information from the server.
     */
    public Account findAccountById(int id) {
//        return tempAccountList.stream().filter(account -> id == account.getId()).findFirst().orElse(null);
        Account account = new Account();
        String token = this.sessionService.getToken();
        String response2 = this.webClientService.authorizedHttpGetJson("/api/accounts/" + id, token);
        try {
            account = this.objectMapper.readValue(response2, Account.class);
        } catch (IOException e) {
            LOGGER.error("Can't parse response from server when find account by id");
            throw new RuntimeException(e);
        }
        return account;
    }

    /**
     * Adds a new account to the server.
     *
     * @param account The account object to be added.
     * @throws RuntimeException If there is an error while adding the account to the server.
     */
    public void addNewAccount(Account account) {
        String token = this.sessionService.getToken();
        try {
            this.webClientService.authorizedHttpPostJson("/api/accounts", this.objectMapper.writeValueAsString(account), token);
        } catch (IOException e) {
            LOGGER.error("Can't parse response from server when add new account: " + account.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates an existing account on the server.
     *
     * @param account The updated account object.
     * @throws RuntimeException If there is an error while updating the account on the server.
     */
    public void EditAccount(Account account) {
        String token = this.sessionService.getToken();
        try {
            this.webClientService.authorizedHttpPutJson("/api/accounts", this.objectMapper.writeValueAsString(account), token);
        } catch (IOException e) {
            LOGGER.error("Can't parse response from server when edit account");
            LOGGER.info(account.toString());
            throw new RuntimeException(e);
        }
    }


    /**
     * Activates an account by its ID on the server.
     *
     * @param id The ID of the account to be activated.
     * @throws RuntimeException If there is an error while activating the account on the server.
     */
    public void ActiveAccount(int id) {
        String token = this.sessionService.getToken();
        this.webClientService.authorizedHttpPutJson("/api/accounts/activate/" + id, " ", token);
    }

    /**
     * Deactivates an account by its ID on the server.
     *
     * @param id The ID of the account to be deactivated.
     * @throws RuntimeException If there is an error while deactivating the account on the server.
     */
    public void InActiveAccount(int id) {
        String token = this.sessionService.getToken();
        this.webClientService.authorizedHttpPutJson("/api/accounts/deactivate/" + id, " ", token);
    }

    /**
     * Finds an account by its username on the server.
     *
     * @param username The username of the account to be found.
     * @return The Account object representing the found account, or null if not found.
     * @throws RuntimeException If there is an error while retrieving the account from the server.
     */
    public Account findAccountByUsername(String username) {
        String token = this.sessionService.getToken();
        String response = this.webClientService.authorizedHttpGetJson("/api/accounts/search?username=" + username, token);
        try {
            return this.objectMapper.readValue(response, Account.class);
        } catch (IOException e) {
            LOGGER.error("Error when find account by username: " + username);
            throw new RuntimeException(e);
        }
    }

}
