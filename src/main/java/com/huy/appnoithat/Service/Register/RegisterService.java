package com.huy.appnoithat.Service.Register;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.DataModel.Entity.Account;
import com.huy.appnoithat.Service.RestService.AccountRestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegisterService {
    final static Logger LOGGER = LogManager.getLogger(RegisterService.class);
    private final AccountRestService accountRestService;

    public RegisterService(AccountRestService accountRestService) {
        this.accountRestService = accountRestService;
    }

    /**
     * Registers a new account by sending a POST request to the server.
     * Requires an authorized session token.
     *
     * @param account The Account object to register.
     */
    public void registerNewAccount(Account account) {
        accountRestService.register(account);
    }

    /**
     * Checks if the given username is valid by making an HTTP GET request to the server.
     *
     * @param username The username to validate.
     * @return True if the username is valid, false otherwise.
     */
    public boolean isUsernameValid(String username) {
        boolean result = accountRestService.isUsernameValid(username);
        if (!result) {
            PopupUtils.throwErrorNotification("Tài khoản đã tồn tài");
        }
        return result;
    }
}
