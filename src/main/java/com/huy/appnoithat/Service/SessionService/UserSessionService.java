package com.huy.appnoithat.Service.SessionService;

import com.huy.appnoithat.DataModel.Session.PersistenceUserSession;
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
     * Checks if the user is logged in by validating the session.
     *
     * @return true if the session is valid and the user is logged in, false otherwise.
     */
    public boolean isLogin() {
        return isSessionValid();
    }

    /**
     * Clears the user session data, effectively logging the user out.
     */
    public void cleanUserSession() {
        UserSession session = UserSession.getInstance();
        session.setAccount(new Account(0, "", "", false,
                null, new ArrayList<>(), false, null));
        session.setJwtToken("");
        saveSessionToDisk();
    }

    /**
     * Sets the user session with the provided username and JWT token.
     *
     * @param username The username of the logged-in user.
     * @param jwtToken The JWT token received upon successful login.
     */
    public void setSession(String username, String jwtToken) {
        setToken(jwtToken);
        if (!username.isEmpty()) {
            AccountRestService accountRestService = AccountRestService.getInstance();
            Account account = accountRestService.getAccountInformation();
            if (account == null) {
                LOGGER.error("Account is null");
                return;
            }
            setLoginAccount(account);
        }
    }

    /**
     * Sets the JWT token for the current user session.
     *
     * @param jwtToken The JWT token to set for the user session.
     */
    public void setToken(String jwtToken) {
        UserSession session = UserSession.getInstance();
        session.setJwtToken(jwtToken);
    }

    /**
     * Retrieves the JWT token from the user session.
     *
     * @return The JWT token from the user session.
     */
    public String getToken() {
        UserSession session = UserSession.getInstance();
        return session.getJwtToken();
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
     * Retrieves the user session of the currently logged-in user.
     *
     * @return The user session of the currently logged-in user.
     * @throws RuntimeException If no session is found (user is not logged in).
     */
    public UserSession getSession() {
        if (!isLogin()) {
            LOGGER.error("No session found");
            throw new RuntimeException("User is not login");
        }
        return UserSession.getInstance();
    }


    /**
     * Checks if the user session is valid by making a request to the server.
     *
     * @return True if the user session is valid, false otherwise.
     * @throws RuntimeException If an error occurs while checking the session validity.
     */
    public boolean isSessionValid() {
        if (getToken().isEmpty()) {
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
            setToken("");
            return;
        }
        setSession(persistenceUserSession.getUsername(), persistenceUserSession.getToken());
    }


    /**
     * Saves the user session data to the disk.
     *
     * @throws IOException If an error occurs while writing the session data to the disk.
     */
    public void saveSessionToDisk() {
        persistenceStorageService.setUserSession(new PersistenceUserSession(getUsername(), getToken()));
    }
}
