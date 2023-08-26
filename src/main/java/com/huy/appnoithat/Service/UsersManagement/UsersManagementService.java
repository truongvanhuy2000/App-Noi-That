package com.huy.appnoithat.Service.UsersManagement;

import com.huy.appnoithat.Entity.Account;

import java.util.ArrayList;
import java.util.List;

public class UsersManagementService {
    private final List<Account> tempAccountList = new ArrayList<>();

    public UsersManagementService() {
    }

    public List<Account> findAllAccount(){
        return tempAccountList;
    }

    void deactivateAccount(int id){
        findAccountById(id).setActive(false);
    }

    void activateAccount(int id){
        findAccountById(id).setActive(false);
    }

    void deleteAccount(int id){
        tempAccountList.remove(findAccountById(id));
    }
    Account findAccountById(int id){
        return tempAccountList.stream().filter(account -> id == account.getId()).findFirst().orElse(null);
    }
}
