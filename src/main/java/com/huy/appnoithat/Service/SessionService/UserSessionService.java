package com.huy.appnoithat.Service.SessionService;

import com.huy.appnoithat.DataModel.Session.PersistenceUserSession;
import com.huy.appnoithat.DataModel.Token;
import com.huy.appnoithat.Entity.Account;
import com.huy.appnoithat.Service.PersistenceStorage.PersistenceStorageService;
import com.huy.appnoithat.Service.RestService.AccountRestService;
import com.huy.appnoithat.Session.UserSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;

public class UserSessionService {
    final static Logger LOGGER = LogManager.getLogger(UserSessionService.class);
    private final PersistenceStorageService persistenceStorageService;
    /**
     * Constructor for UserSessionService. Initializes required services and objects.
     */
    public UserSessionService() {
        persistenceStorageService = PersistenceStorageService.getInstance();
    }

    /**
     * Clears the user session data, effectively logging the user out.
     */
    public void cleanUserSession() {
        UserSession session = UserSession.getInstance();
        session.setAccount(new Account(0, "", "", false,
                null, new ArrayList<>(), false, null));
        session.setJwtToken("");
        session.setRefreshToken("");
        saveSessionToDisk();
    }

    /**
     * Sets the user session with the provided username and JWT token.
     *
     * @param jwtToken The JWT token received upon successful login.
     */
    public void setSession(Token token) {
        setToken(token);
        AccountRestService accountRestService = AccountRestService.getInstance();
        Account account = accountRestService.getAccountInformation();
        if (account == null) {
            LOGGER.error("Account is null");
            return;
        }
        setLoginAccount(account);
    }
    public void saveSession(Token token) {
        setSession(token);
        saveSessionToDisk();
    }

    /**
     * Sets the JWT token for the current user session.
     *
     * @param jwtToken The JWT token to set for the user session.
     */
    public void setToken(Token token) {
        UserSession session = UserSession.getInstance();
        session.setJwtToken(token.getToken());
        session.setRefreshToken(token.getRefreshToken());
    }

    /**
     * Retrieves the JWT token from the user session.
     *
     * @return The JWT token from the user session.
     */
    public String getJwtToken() {
        UserSession session = UserSession.getInstance();
        return session.getJwtToken();
    }
    public Token getToken() {
        UserSession session = UserSession.getInstance();
        return new Token(session.getJwtToken(), session.getRefreshToken());
    }

    public String getRefreshToken() {
        UserSession session = UserSession.getInstance();
        return session.getRefreshToken();
    }
    /**
     * Sets the logged-in user's account information in the user session.
     *
     * @param account The logged-in user's account information.
     */
    public void setLoginAccount(Account account) {
        UserSession session = UserSession.getInstance();
        session.setAccount(account);
    }
    /**
     * Sets the logged-in user's account information in the user session.
     *
     * @param account The logged-in user's account information.
     */
    public Account getLoginAccount() {
        UserSession session = UserSession.getInstance();
        return session.getAccount();
    }

    /**
     * Gets the username of the currently logged-in user.
     *
     * @return The username of the currently logged-in user.
     */
    public String getUsername() {
        return getLoginAccount().getUsername();
    }
    public String getPassword() {
        return getLoginAccount().getPassword();
    }

    /**
     * Checks if the user session is valid by making a request to the server.
     *
     * @return True if the user session is valid, false otherwise.
     * @throws RuntimeException If an error occurs while checking the session validity.
     */
    public boolean isSessionValid() {
        if (getJwtToken().isEmpty()) {
            loadSessionFromDisk();
        }
        AccountRestService accountRestService = AccountRestService.getInstance();
        String response = accountRestService.sessionCheck();
        return response != null;
    }

    /**
     * Loads the user session data from the disk.
     *
     * @throws IOException If an error occurs while reading the session data from the disk.
     */
    public void loadSessionFromDisk() {
        PersistenceUserSession persistenceUserSession = persistenceStorageService.getUserSession();
        if (persistenceUserSession == null) {
            setToken(new Token("", ""));
            return;
        }
        setSession(new Token(persistenceUserSession.getToken(), persistenceUserSession.getRefreshToken()));
    }

    /**
     * Saves the user session data to the disk.
     *
     * @throws IOException If an error occurs while writing the session data to the disk.
     */
    public void saveSessionToDisk() {
        persistenceStorageService.saveUserSession(new PersistenceUserSession(getJwtToken(), getRefreshToken()));
    }
}
