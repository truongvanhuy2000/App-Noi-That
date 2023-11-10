package com.huy.appnoithat.Service.UserDetail;

import com.huy.appnoithat.Entity.Account;
import com.huy.appnoithat.Entity.AccountInformation;
import com.huy.appnoithat.Service.RestService.AccountRestService;

public class UserDetailService {
    private final AccountRestService accountRestService;

    public UserDetailService() {
        this.accountRestService = AccountRestService.getInstance();
    }
    public Account getAccountInformation() {
        return accountRestService.getAccountInformation();
    }

    public boolean updateAccountInformation(AccountInformation accountInfo) {
        return accountRestService.updateInformation(accountInfo);
    }
    public boolean updatePassword(String oldPassword, String newPassword) {
        return accountRestService.changePassword(oldPassword, newPassword);
    }
}
