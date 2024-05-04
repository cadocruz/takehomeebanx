package org.cadocruz.takehomeebanx.service.handler;

import org.cadocruz.takehomeebanx.domain.TypeOperation;
import org.cadocruz.takehomeebanx.service.AccountService;

import java.util.HashMap;
import java.util.Map;

import static org.cadocruz.takehomeebanx.domain.TypeOperation.*;

public class GetTransactionCommands {

    private final Map<TypeOperation, TransactionHandlerCommand> commandMap = new HashMap<>();

    private GetTransactionCommands(final AccountService accountService) {
        commandMap.put(DEPOSIT, new DepositHandler(accountService));
        commandMap.put(WITHDRAW, new WithdrawHandler(accountService));
        commandMap.put(TRANSFER, new TransferHandler(accountService));
    }

    public static Map<TypeOperation, TransactionHandlerCommand> getCommands(final AccountService accountService) {
        GetTransactionCommands getTransactionCommands = new GetTransactionCommands(accountService);
        return getTransactionCommands.commandMap;
    }
}
