package com.huy.appnoithat.Session;

import com.huy.appnoithat.DataModel.Entity.Account;
import com.huy.appnoithat.DataModel.Token;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

// This class is have to be used only by SessionService, if you want to use it, please use SessionService instead
@Getter
@Setter
@Builder
public class UserSession {
    private Account account;
    private Token token;

    public UserSession(Account account, Token token) {
        this.account = account;
        this.token = token;
    }
}
