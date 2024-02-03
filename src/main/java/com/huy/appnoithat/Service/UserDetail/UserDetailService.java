package com.huy.appnoithat.Service.UserDetail;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.DataModel.Entity.Account;
import com.huy.appnoithat.DataModel.Entity.AccountInformation;
import com.huy.appnoithat.Service.RestService.AccountRestService;

public class UserDetailService {
    private final AccountRestService accountRestService;

    public UserDetailService() {
        this.accountRestService = AccountRestService.getInstance();
    }
    public Account getAccountInformation() {
        return accountRestService.getAccountInformation();
    }

    public void updateAccountInformation(AccountInformation accountInfo) {
        if (accountRestService.updateInformation(accountInfo)) {
            PopupUtils.throwSuccessNotification("Cập nhật thông tin thành công");
        } else {
            PopupUtils.throwErrorNotification("Cập nhật thông tin thất bại");
        }
    }
    public void updatePassword(String oldPassword, String newPassword) {
        if (accountRestService.changePassword(oldPassword, newPassword)) {
            PopupUtils.throwSuccessNotification("Đổi mật khẩu thành công");
        } else {
            PopupUtils.throwErrorNotification("Đổi mật khẩu thất bại");
        }
    }
}
