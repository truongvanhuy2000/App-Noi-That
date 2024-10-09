package com.huy.appnoithat.Session;

import com.huy.appnoithat.DataModel.Entity.Account;
import com.huy.appnoithat.DataModel.Token;

public interface UserSessionManager {
    void cleanUserSession();

    boolean isAccessTokenExpired();

    boolean isRefreshTokenExpired();

    Token getToken();

    void saveToken(Token token);

    Account getAccount();

    void saveAccount(Account account);

    boolean isSessionValid();
}
