package org.cadocruz.takehomeebanx.service.handler;

import org.cadocruz.takehomeebanx.domain.Account;
import org.cadocruz.takehomeebanx.domain.TransactionInput;
import org.cadocruz.takehomeebanx.domain.TransactionOutput;
import org.cadocruz.takehomeebanx.domain.TransactionResponse;
import org.cadocruz.takehomeebanx.service.AccountService;

public class DepositHandler extends TransactionHandlerCommand {

    public DepositHandler(AccountService accountService) {
        super(accountService);
    }

    @Override
    public TransactionOutput execute(TransactionInput anInput) {
        TransactionResponse anOutput = new TransactionResponse();
        anInput.getDestination().orElseThrow(RuntimeException::new);
        Account account = accountService.findById(anInput.getDestination().get());
        if (account == null) {
            account = accountService.createAccount(anInput.getDestination().get());
        }
        deposit(account, anInput.getAmount());
        anOutput.setDestination(account);
        return anOutput;
    }
}
