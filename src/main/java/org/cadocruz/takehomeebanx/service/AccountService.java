package org.cadocruz.takehomeebanx.service;

import org.cadocruz.takehomeebanx.domain.Account;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {

    private final Map<String, Account> accounts = new HashMap<>();

    public Account findById(String id) {
        return accounts.getOrDefault(id, null);
    }

    public Account createAccount(String id) {
        Account account = findById(id);
        if (account != null) {
            return account;
        }
        Account newAccount = new Account(id, 0L);
        accounts.put(id, newAccount);
        return newAccount;
    }

    public void cleanAccounts() {
        this.accounts.clear();
    }
}
