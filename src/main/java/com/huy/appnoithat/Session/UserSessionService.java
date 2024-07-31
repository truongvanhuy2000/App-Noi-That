package com.huy.appnoithat.Session;

import com.huy.appnoithat.DataModel.Entity.Account;
import com.huy.appnoithat.DataModel.Session.PersistenceUserSession;
import com.huy.appnoithat.DataModel.Token;
import com.huy.appnoithat.Service.PersistenceStorage.PersistenceStorageService;
import com.huy.appnoithat.Service.PersistenceStorage.StorageService;
import com.huy.appnoithat.Service.RestService.AccountRestService;
import com.huy.appnoithat.Service.WebClient.ApacheHttpClient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@AllArgsConstructor
@Builder
public class UserSessionService {
    final static Logger LOGGER = LogManager.getLogger(ApacheHttpClient.class);
    private UserSession session;
    public UserSessionService() {
        session = UserSession.getInstance();
    }
    /**
     * Clears the user session data, effectively logging the user out.
     */
    public void cleanUserSession() {
        Account account = Account.empty();
        session.setAccount(account);
        session.setToken(Token.empty());
        saveSessionToDisk();
    }

    public boolean isAccessTokenExpired() {
        return session.getToken().getAccessTokenExpiration().before(new Date());
    }

    public boolean isRefreshTokenExpired() {
        return session.getToken().getRefreshTokenExpiration().before(new Date());
    }
    /**
     * Sets the user session with the provided username and JWT token.
     *
     * @param jwtToken The JWT token received upon successful login.
     */
    private synchronized void setSession(Token token) {
        AccountRestService accountRestService = new AccountRestService();
        Account account = accountRestService.getAccountInformation();
        if (account == null) {
            LOGGER.error("Account is null");
            throw new RuntimeException("Account is null");
        }
        session.setAccount(account);
        session.setToken(token);
    }

    public void saveSession(Token token) {
        setSession(token);
        saveSessionToDisk();
    }

    public Token getToken() {
        return session.getToken();
    }

    /**
     * Sets the logged-in user's account information in the user session.
     *
     * @param account The logged-in user's account information.
     */
    public Account getLoginAccount() {
        return session.getAccount();
    }

    /**
     * Checks if the user session is valid by making a request to the server.
     *
     * @return True if the user session is valid, false otherwise.
     * @throws RuntimeException If an error occurs while checking the session validity.
     */
    public boolean isSessionValid() {
        if (StringUtils.isEmpty(session.getToken().getAccessToken())) {
            loadSessionFromDisk();
        }
        AccountRestService accountRestService = new AccountRestService();
        String response = accountRestService.sessionCheck();
        return response != null;
    }

    /**
     * Loads the user session data from the disk.
     *
     * @throws IOException If an error occurs while reading the session data from the disk.
     */
    public void loadSessionFromDisk() {
        StorageService persistenceStorageService = new PersistenceStorageService();
        PersistenceUserSession persistenceUserSession = persistenceStorageService.getUserSession();
        if (persistenceUserSession == null || StringUtils.isEmpty(persistenceUserSession.getRefreshToken())) {
            session.setToken(Token.empty());
            return;
        }
        setSession(Token.builder()
                .refreshToken(persistenceUserSession.getRefreshToken())
                .accessToken(persistenceUserSession.getAccessToken())
                .refreshTokenExpiration(persistenceUserSession.getRefreshTokenExpiration())
                .accessTokenExpiration(persistenceUserSession.getAccessTokenExpiration())
                .build());
    }

    /**
     * Saves the user session data to the disk.
     *
     * @throws IOException If an error occurs while writing the session data to the disk.
     */
    public void saveSessionToDisk() {
        StorageService persistenceStorageService = new PersistenceStorageService();
        Token token = session.getToken();
        persistenceStorageService.saveUserSession(PersistenceUserSession.builder()
                .refreshToken(token.getRefreshToken())
                .accessToken(token.getAccessToken())
                .refreshTokenExpiration(token.getRefreshTokenExpiration())
                .accessTokenExpiration(token.getAccessTokenExpiration())
                .build());
    }

    public void setToken(Token token) {
        session.setToken(token);
    }
}
