package org.cadocruz.takehomeebanx.service.handler;

import org.cadocruz.takehomeebanx.domain.Account;
import org.cadocruz.takehomeebanx.domain.TransactionInput;
import org.cadocruz.takehomeebanx.domain.TransactionOutput;
import org.cadocruz.takehomeebanx.domain.TransactionResponse;
import org.cadocruz.takehomeebanx.service.AccountService;

public class WithdrawHandler extends TransactionHandlerCommand {

    public WithdrawHandler(AccountService accountService) {
        super(accountService);
    }

    @Override
    public TransactionOutput execute(TransactionInput anInput) {
        TransactionResponse anOutput = new TransactionResponse();
        anInput.getOrigin().orElseThrow(RuntimeException::new);
        Account account = accountService.findById(anInput.getOrigin().get());
        if (account == null) {
            throw new RuntimeException("Account not found");
        }
        withdraw(account, anInput.getAmount());
        anOutput.setOrigin(account);
        return anOutput;
    }
}
