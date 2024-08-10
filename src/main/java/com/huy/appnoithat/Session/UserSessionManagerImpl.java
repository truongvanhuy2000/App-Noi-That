package com.huy.appnoithat.Session;

import com.huy.appnoithat.DataModel.Entity.Account;
import com.huy.appnoithat.DataModel.Session.PersistenceUserSession;
import com.huy.appnoithat.DataModel.Token;
import com.huy.appnoithat.Service.PersistenceStorage.StorageService;
import com.huy.appnoithat.Service.RestService.AccountRestService;
import com.huy.appnoithat.Service.WebClient.ApacheHttpClient;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Date;

@Builder
public class UserSessionManagerImpl implements UserSessionManager {
    final static Logger LOGGER = LogManager.getLogger(ApacheHttpClient.class);
    private final UserSession session;
    private final StorageService persistenceStorageService;
    private final AccountRestService accountRestService;

    public UserSessionManagerImpl(UserSession session,
                                  StorageService persistenceStorageService,
                                  AccountRestService accountRestService
    ) {
        this.session = session;
        this.persistenceStorageService = persistenceStorageService;
        this.accountRestService = accountRestService;
    }

    /**
     * Clears the user session data, effectively logging the user out.
     */
    @Override
    public void cleanUserSession() {
        Account account = Account.empty();
        session.setAccount(account);
        session.setToken(Token.empty());
        saveSessionToDisk();
    }

    @Override
    public boolean isAccessTokenExpired() {
        return session.getToken().getAccessTokenExpiration().before(new Date());
    }

    @Override
    public boolean isRefreshTokenExpired() {
        return session.getToken().getRefreshTokenExpiration().before(new Date());
    }

    @Override
    public Token getToken() {
        return session.getToken();
    }

    @Override
    public void saveToken(Token token) {
        session.setToken(token);
        saveSessionToDisk();
    }

    /**
     * Sets the logged-in user's account information in the user session.
     *
     * @param account The logged-in user's account information.
     */
    @Override
    public Account getAccount() {
        return session.getAccount();
    }

    @Override
    public void saveAccount(Account account) {
        session.setAccount(account);
    }

    /**
     * Checks if the user session is valid by making a request to the server.
     *
     * @return True if the user session is valid, false otherwise.
     * @throws RuntimeException If an error occurs while checking the session validity.
     */
    @Override
    public boolean isSessionValid() {
        if (StringUtils.isEmpty(session.getToken().getAccessToken())) {
            loadSessionFromDisk();
        }
        String response = accountRestService.sessionCheck();
        return response != null;
    }

    /**
     * Loads the user session data from the disk.
     *
     * @throws IOException If an error occurs while reading the session data from the disk.
     */
    private void loadSessionFromDisk() {
        PersistenceUserSession persistenceUserSession = persistenceStorageService.getUserSession();
        if (persistenceUserSession == null || StringUtils.isEmpty(persistenceUserSession.getRefreshToken())) {
            session.setToken(Token.empty());
            return;
        }
        session.setToken(Token.builder()
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
    private void saveSessionToDisk() {
        Token token = session.getToken();
        persistenceStorageService.saveUserSession(PersistenceUserSession.builder()
                .refreshToken(token.getRefreshToken())
                .accessToken(token.getAccessToken())
                .refreshTokenExpiration(token.getRefreshTokenExpiration())
                .accessTokenExpiration(token.getAccessTokenExpiration())
                .build());
    }
}
