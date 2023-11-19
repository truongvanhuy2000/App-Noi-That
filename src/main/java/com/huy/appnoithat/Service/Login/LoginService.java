package com.huy.appnoithat.Service.Login;

import com.huy.appnoithat.DataModel.Token;
import com.huy.appnoithat.Entity.Account;
import com.huy.appnoithat.Service.RestService.AccountRestService;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginService {
    final static Logger LOGGER = LogManager.getLogger(LoginService.class);
    private final UserSessionService sessionService;
    private final AccountRestService accountRestService;

    public LoginService() {
        this.sessionService = new UserSessionService();
        accountRestService = AccountRestService.getInstance();
    }

    public boolean Authorization(String username, String password) {
        Token token = login(username, password);
        if (token != null) {
            this.sessionService.setSession(username, token);
            this.sessionService.saveSessionToDisk();
            return true;
        }
        return false;
    }
    public boolean reauthorize(String password) {
        Token token = login(this.sessionService.getUsername(), password);
        return token != null;
    }
    public Token login(String username, String password) {
        return accountRestService.login(username, password);
    }
    public boolean authorizeWithToken(Token token) {
        sessionService.setToken(token);
        Account account = accountRestService.getAccountInformation();
        if (account == null) {
            return false;
        }
        sessionService.setLoginAccount(account);
        this.sessionService.saveSessionToDisk();
        return true;
    }
}
