package org.cadocruz.takehomeebanx.service.handler;

import org.cadocruz.takehomeebanx.domain.Account;
import org.cadocruz.takehomeebanx.service.AccountService;

public abstract class TransactionHandlerCommand implements TransactionCommand {

    protected AccountService accountService;

    public TransactionHandlerCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    protected void deposit(final Account account, final Long amount) {
        account.setBalance(account.getBalance() + amount);
    }

    protected void withdraw(final Account account, final Long amount) {
        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }
        account.setBalance(account.getBalance() - amount);
    }

}
