package com.newbei.developerJdbc.repository;

import com.newbei.developerJdbc.model.Account;

public interface AccountRepository extends GenericRepository<Account,Long> {
    long authentication(String login, String password);
}