package org.cadocruz.takehomeebanx.service.handler;

import org.cadocruz.takehomeebanx.domain.Account;
import org.cadocruz.takehomeebanx.domain.TransactionInput;
import org.cadocruz.takehomeebanx.domain.TransactionOutput;
import org.cadocruz.takehomeebanx.domain.TransactionResponse;
import org.cadocruz.takehomeebanx.service.AccountService;

public class TransferHandler extends TransactionHandlerCommand {

    public TransferHandler(AccountService accountService) {
        super(accountService);
    }

    @Override
    public TransactionOutput execute(TransactionInput anInput) {
        TransactionResponse anOutput = new TransactionResponse();
        anInput.getOrigin().orElseThrow(RuntimeException::new);
        anInput.getDestination().orElseThrow(RuntimeException::new);

        Account accountOrigin = accountService.findById(anInput.getOrigin().get());
        if (accountOrigin == null) {
            throw new RuntimeException("Origin Account not found");
        }
        Account accountDestination = accountService.findById(anInput.getDestination().get());
        if (accountDestination == null) {
            accountDestination = accountService.createAccount(anInput.getDestination().get());
        }
        withdraw(accountOrigin, anInput.getAmount());
        deposit(accountDestination, anInput.getAmount());
        anOutput.setOrigin(accountOrigin);
        anOutput.setDestination(accountDestination);
        return anOutput;

    }
}
