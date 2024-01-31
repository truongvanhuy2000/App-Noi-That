package com.huy.appnoithat.Service.Login;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.DataModel.Token;
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
    public boolean basicAuthorization(String username, String password) {
        Token token = login(username, password);
        if (token != null) {
            LOGGER.info("Login with account: " + username);
            this.sessionService.saveSession(token);
            return true;
        }
        PopupUtils.throwErrorNotification("Không thể đăng nhập, vui lòng kiểm tra lại thông tin");
        return false;
    }
    public boolean reAuthorize(String password) {
        Token token = login(this.sessionService.getUsername(), password);
        return token != null;
    }
    private Token login(String username, String password) {
        return accountRestService.login(username, password);
    }
    public boolean authorizeWithToken(Token token) {
        sessionService.setToken(token);
        if (accountRestService.sessionCheck() != null) {
            sessionService.saveSession(token);
            return true;
        }
        return false;
    }
}
