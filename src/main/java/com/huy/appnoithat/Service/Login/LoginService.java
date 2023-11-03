package com.huy.appnoithat.Service.Login;

import com.huy.appnoithat.Entity.Account;
import com.huy.appnoithat.Service.RestService.AccountRestService;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class LoginService {
    final static Logger LOGGER = LogManager.getLogger(LoginService.class);
    private final UserSessionService sessionService;
    private final AccountRestService accountRestService;

    public LoginService() {
        this.sessionService = new UserSessionService();
        accountRestService = AccountRestService.getInstance();
    }

    public boolean Authorization(String username, String password) {
        String token = login(username, password);
        if (token != null) {
            saveSession(username, token);
            return true;
        }
        return false;
    }
    public void saveSession(String username, String token) {
        this.sessionService.setSession(username, token);
        this.sessionService.saveSessionToDisk();
    }
    public boolean reauthorize(String password) {
        String token = login(this.sessionService.getUsername(), password);
        return token != null && !token.isEmpty();
    }
    public String login(String username, String password) {
        return accountRestService.login(username, password);
    }
    public boolean authorizeWithToken(String token) {
        Account account = accountRestService.getAccountInformation();
        if (account == null) {
            return false;
        }
        saveSession(account.getUsername(), token);
        return true;
    }
}
