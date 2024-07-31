package com.huy.appnoithat.Session;

import com.huy.appnoithat.DataModel.Entity.Account;
import com.huy.appnoithat.DataModel.Token;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;

// This class is have to be used only by SessionService, if you want to use it, please use SessionService instead
@Getter
@Setter
@Builder
public class UserSession {
    private static UserSession instance;
    private Account account;
    private Token token;

    public static synchronized UserSession getInstance() {
        if (instance == null) {
            Account account = Account.empty();
            instance = UserSession.builder()
                    .account(account)
                    .token(Token.empty()).build();
        }
        return instance;
    }
}
