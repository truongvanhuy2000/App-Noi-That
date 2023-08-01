package com.huy.appnoithat.Service.UsersManagement;

import com.huy.appnoithat.Entity.Account;
import com.huy.appnoithat.Entity.AccountInformation;

import java.util.ArrayList;
import java.util.List;

public class UsersManagementService {
    private final List<Account> tempAccountList = new ArrayList<>();

    public UsersManagementService() {
//        Account account1 = new Account(1, "john_doe", "password123", 1, new AccountInformation());
//        Account account2 = new Account(2, "jane_smith", "ilovecoding", 1, new AccountInformation());
//        Account account3 = new Account(3, "mike_jackson", "12345pass", 0, new AccountInformation());
//        tempAccountList.add(account1);
//        tempAccountList.add(account2);
//        tempAccountList.add(account2);
    }

//    List<Account> findAllAccount(){
//        return tempAccountList;
//    }
//
//    void deactivateAccount(int id){
//        findAccountById(id).setActive(0);
//    }
//
//    void activateAccount(int id){
//        findAccountById(id).setActive(1);
//    }
//
//    void deleteAccount(int id){
//        tempAccountList.remove(findAccountById(id));
//    }
//    Account findAccountById(int id){
//        return tempAccountList.stream().filter(account -> id == account.getId()).findFirst().orElse(null);
//    }
}
