package org.cadocruz.takehomeebanx.service;

import org.cadocruz.takehomeebanx.domain.Account;
import org.cadocruz.takehomeebanx.domain.TransactionRequest;
import org.cadocruz.takehomeebanx.domain.TransactionResponse;
import org.cadocruz.takehomeebanx.domain.TypeOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.cadocruz.takehomeebanx.domain.TypeOperation.*;

@Service
public class TransactionService {

    @Autowired
    private AccountService accountService;

    public TransactionResponse createTransaction(final TransactionRequest transaction) {
        TransactionResponse transactionResponse = new TransactionResponse();
        TypeOperation type = transaction.getType();
        if (type == DEPOSIT) {
            return makeDeposit(transaction, transactionResponse);
        } else if (type == WITHDRAW) {
            return makeWithdraw(transaction, transactionResponse);
        } else if (type == TRANSFER) {
            return makeTransfer(transaction, transactionResponse);
        } else {
            return null;
        }
    }

    private TransactionResponse makeDeposit(TransactionRequest transaction, TransactionResponse transactionResponse) {
        if (transaction.getDestination() == null) {
            return null;
        }
        Account account = accountService.findById(transaction.getDestination());
        if (account == null) {
            account = accountService.createAccount(transaction.getDestination());
        }
        deposit(account, transaction.getAmount());
        transactionResponse.setDestination(account);
        return transactionResponse;
    }

    private TransactionResponse makeWithdraw(TransactionRequest transaction, TransactionResponse transactionResponse) {
        if (transaction.getOrigin() == null) {
            return null;
        }
        Account account = accountService.findById(transaction.getOrigin());
        if (account == null) {
            return null;
        }
        withdraw(account, transaction.getAmount());
        transactionResponse.setOrigin(account);
        return transactionResponse;
    }

    private TransactionResponse makeTransfer(TransactionRequest transaction, TransactionResponse transactionResponse) {
        if (transaction.getOrigin() == null || transaction.getDestination() == null) {
            return null;
        }
        Account accountOrigin = accountService.findById(transaction.getOrigin());
        if (accountOrigin == null) {
            return null;
        }
        Account accountDestination = accountService.findById(transaction.getDestination());
        if (accountDestination == null) {
            accountDestination = accountService.createAccount(transaction.getDestination());
        }
        withdraw(accountOrigin, transaction.getAmount());
        deposit(accountDestination, transaction.getAmount());
        transactionResponse.setOrigin(accountOrigin);
        transactionResponse.setDestination(accountDestination);
        return transactionResponse;
    }

    private void deposit(final Account account, final Long amount) {
        account.setBalance(account.getBalance() + amount);
    }

    private void withdraw(final Account account, final Long amount) {
        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }
        account.setBalance(account.getBalance() - amount);
    }
}
