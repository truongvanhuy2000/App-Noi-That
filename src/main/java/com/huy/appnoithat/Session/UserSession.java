package com.huy.appnoithat.Session;

import com.huy.appnoithat.DataModel.Entity.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

// This class is have to be used only by SessionService, if you want to use it, please use SessionService instead
@Getter
@Setter
@Builder
public class UserSession {
    private static UserSession instance;
    private Account account;
    private String jwtToken;
    private String refreshToken;
    private UserSession(Account account, String jwtToken, String refreshToken) {
        this.account = account;
        this.jwtToken = jwtToken;
        this.refreshToken = refreshToken;
    }
    public static synchronized UserSession getInstance() {
        if (instance == null) {
            Account account = Account.builder()
                    .id(0)
                    .username("")
                    .password("")
                    .active(false)
                    .roleList(new ArrayList<>())
                    .enabled(false).build();

            instance = UserSession.builder()
                    .account(account)
                    .jwtToken("")
                    .refreshToken("").build();
        }
        return instance;
    }
}
