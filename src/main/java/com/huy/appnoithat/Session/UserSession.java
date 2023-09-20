package com.huy.appnoithat.Session;

import com.huy.appnoithat.Entity.Account;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

// This class is have to be used only by SessionService, if you want to use it, please use SessionService instead
@Getter
@Setter
public class UserSession {
    private static UserSession instance;
    private Account account;
    private String jwtToken;

    private UserSession(Account account, String jwtToken) {
        this.account = account;
        this.jwtToken = jwtToken;
    }

    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession(
                    new Account(0, "", "", false, null, new ArrayList<>(), false, null), "");
        }
        return instance;
    }
}
