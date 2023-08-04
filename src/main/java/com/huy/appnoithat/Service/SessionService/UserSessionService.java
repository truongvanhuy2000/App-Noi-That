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
        UserSession.getInstance().setAccount(null);
    }
    public void setSession(Account account) {
        UserSession.getInstance().setAccount(account);
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
