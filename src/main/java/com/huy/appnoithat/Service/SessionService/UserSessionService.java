package com.huy.appnoithat.Service.SessionService;

import com.huy.appnoithat.Entity.Account;
import com.huy.appnoithat.Service.WebClient.WebClientService;
import com.huy.appnoithat.Service.WebClient.WebClientServiceImpl;
import com.huy.appnoithat.Session.UserSession;

public class UserSessionService {
    WebClientService webClientService;
    public UserSessionService() {
        webClientService = new WebClientServiceImpl("http://localhost:8080", 10);
    }
    public boolean isLogin() {
        if (UserSession.getInstance().getAccount() != null) {
            return true;
        }
        return false;
    }
    public void cleanUserSession() {
        UserSession session = UserSession.getInstance();
        session.setAccount(null);
        session.setJwtToken(null);
    }
    public void setSession(Account account, String jwtToken) {
        UserSession session = UserSession.getInstance();
        session.setAccount(account);
        session.setJwtToken(jwtToken);
    }
    public UserSession getSession() {
        if (!isLogin()) {
            throw new RuntimeException("User is not login");
        }
        return UserSession.getInstance();
    }
    private boolean isSessionValid() {

    }
    // Haven't implemented yet
    public void loadInstanceFromDisk() {
        return;
    }
}
