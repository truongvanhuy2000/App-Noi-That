package com.huy.appnoithat.Service.UsersManagement;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Entity.Account;
import com.huy.appnoithat.Service.RestService.AccountRestService;

import java.util.List;

public class UsersManagementService {
    private final AccountRestService accountRestService;
    /**
     * Initializes the UsersManagementService with required dependencies.
     */
    public UsersManagementService() {
        accountRestService = AccountRestService.getInstance();
    }


    /**
     * Retrieves a list of all enabled accounts from the server.
     *
     * @return List of enabled accounts.
     * @throws RuntimeException If there is an error while retrieving or parsing the response.
     */
    public List<Account> findAllAccountEnable() {
        return accountRestService.findAllEnabledAccount();
    }

    /**
     * Enables an account with the specified ID on the server.
     *
     * @param id The ID of the account to be enabled.
     * @throws RuntimeException If there is an error while sending the request.
     */
    public void enableAccount(int id) {
        accountRestService.enableAccount(id);
    }

    /**
     * Retrieves a list of all accounts that are not enabled from the server.
     *
     * @return A list of Account objects representing the not enabled accounts.
     * @throws RuntimeException If there is an error while retrieving the data from the server.
     */
    public List<Account> findAllNotEnabledAccount() {
        return accountRestService.findAllNotEnabledAccount();
    }

    /**
     * Deletes an account with the specified ID from the server.
     *
     * @param id The ID of the account to be deleted.
     * @throws RuntimeException If there is an error while deleting the account from the server.
     */
    public void deleteAccount(int id) {
        accountRestService.deleteById(id);
    }

    /**
     * Retrieves the account information for the specified account ID from the server.
     *
     * @param id The ID of the account to be retrieved.
     * @return The account object corresponding to the specified ID, or null if no account is found.
     * @throws RuntimeException If there is an error while retrieving the account information from the server.
     */
    public Account findAccountById(int id) {
//        return tempAccountList.stream().filter(account -> id == account.getId()).findFirst().orElse(null);
        return accountRestService.findById(id);
    }

    /**
     * Adds a new account to the server.
     *
     * @param account The account object to be added.
     * @throws RuntimeException If there is an error while adding the account to the server.
     */
    public void addNewAccount(Account account) {
        accountRestService.save(account);
    }

    /**
     * Updates an existing account on the server.
     *
     * @param account The updated account object.
     * @throws RuntimeException If there is an error while updating the account on the server.
     */
    public void EditAccount(Account account) {
        if (accountRestService.update(account) != null) {
            PopupUtils.throwSuccessNotification("Cập nhật thành công");
        } else {
            PopupUtils.throwErrorNotification("Cập nhật thất bại");
        }
    }


    /**
     * Activates an account by its ID on the server.
     *
     * @param id The ID of the account to be activated.
     * @throws RuntimeException If there is an error while activating the account on the server.
     */
    public void ActiveAccount(int id) {
        accountRestService.activateAccount(id);
    }

    /**
     * Deactivates an account by its ID on the server.
     *
     * @param id The ID of the account to be deactivated.
     * @throws RuntimeException If there is an error while deactivating the account on the server.
     */
    public void InActiveAccount(int id) {
        accountRestService.deactivateAccount(id);
    }

    /**
     * Finds an account by its username on the server.
     *
     * @param username The username of the account to be found.
     * @return The Account object representing the found account, or null if not found.
     * @throws RuntimeException If there is an error while retrieving the account from the server.
     */
    public Account findAccountByUsername(String username) {
        return accountRestService.findByUsername(username);
    }

}
