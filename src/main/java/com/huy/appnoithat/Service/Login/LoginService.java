package com.huy.appnoithat.Service.Login;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.DataModel.Entity.Account;
import com.huy.appnoithat.DataModel.Token;
import com.huy.appnoithat.Service.RestService.AccountRestService;
import com.huy.appnoithat.Session.UserSessionManagerImpl;
import com.huy.appnoithat.Session.UserSessionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginService {
    final static Logger LOGGER = LogManager.getLogger(LoginService.class);
    private final UserSessionManager sessionService;
    private final AccountRestService accountRestService;

    public LoginService() {
        this.sessionService = new UserSessionManagerImpl();
        accountRestService = new AccountRestService();
    }

    public boolean basicAuthorization(String username, String password) {
        Token token = login(username, password);
        if (token != null) {
            LOGGER.info("Login with account: " + username);
            this.sessionService.saveToken(token);
            Account account = getAccountInformation();
            sessionService.saveAccount(account);
            return true;
        }
        PopupUtils.throwErrorNotification("Không thể đăng nhập, vui lòng kiểm tra lại thông tin");
        return false;
    }

    public boolean reAuthorize(String password) {
        Account loginAccount = this.sessionService.getAccount();
        Token token = login(loginAccount.getUsername(), password);
        return token != null;
    }

    private Token login(String username, String password) {
        return accountRestService.login(username, password);
    }

    public boolean authorizeWithToken(Token token) {
        sessionService.saveToken(token);
        if (accountRestService.sessionCheck() != null) {
            sessionService.saveToken(token);
            Account account = getAccountInformation();
            sessionService.saveAccount(account);
            return true;
        }
        return false;
    }

    private Account getAccountInformation() {
        Account account = accountRestService.getAccountInformation();
        if (account == null) {
            LOGGER.error("Account is null");
            throw new RuntimeException("Account is null");
        }
        return account;
    }
}
