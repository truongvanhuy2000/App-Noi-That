package com.huy.appnoithat.Service.SessionService;

import com.huy.appnoithat.Entity.Account;
import com.huy.appnoithat.Session.UserSession;

public class UserSessionService {
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
    public Account getSession() {
        if (!isLogin()) {
            throw new RuntimeException("User is not login");
        }
        return UserSession.getInstance().getAccount();
    }
    // Haven't implemented yet
    public void loadInstanceFromDisk() {
        return;
    }
}
