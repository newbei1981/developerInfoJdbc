package com.newbei.developerJdbc.controller;


import com.newbei.developerJdbc.model.Account;
import com.newbei.developerJdbc.model.AccountStatus;
import com.newbei.developerJdbc.repository.AccountRepository;
import com.newbei.developerJdbc.repository.JdbcAccountRepositoryImpl;

import static com.newbei.developerJdbc.view.DeveloperView.saveOrUpdateRecord;

public class AccountController {
    private AccountRepository accountRepository = new JdbcAccountRepositoryImpl();

    public Account saveAccount(){
        Account account = new Account();
        account.setLogin(saveOrUpdateRecord("Login"));
        account.setPassword(saveOrUpdateRecord("Password"));
        account.setAccountStatus(AccountStatus.ACTIVE);
        account = accountRepository.save(account);
        return account;
    }

    public Account updateAccountLogin(Account account){
        account.setLogin(saveOrUpdateRecord("Login",account.getLogin()));
        accountRepository.update(account);
        return account;
    }
    public Account updateAccountPassword(Account account){
        account.setPassword(saveOrUpdateRecord("Account",account.getPassword()));
        accountRepository.update(account);
        return account;
    }

}
