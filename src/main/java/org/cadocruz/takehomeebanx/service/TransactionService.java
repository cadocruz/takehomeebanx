package org.cadocruz.takehomeebanx.service;

import org.cadocruz.takehomeebanx.domain.TransactionOutput;
import org.cadocruz.takehomeebanx.domain.TransactionRequest;
import org.cadocruz.takehomeebanx.domain.TypeOperation;
import org.cadocruz.takehomeebanx.service.handler.GetTransactionCommands;
import org.cadocruz.takehomeebanx.service.handler.TransactionHandlerCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private AccountService accountService;

    public TransactionOutput createTransaction(final TransactionRequest transaction) {
        TypeOperation type = transaction.getType();
        if (type == null) throw new RuntimeException("Transaction type is null");
        Map<TypeOperation, TransactionHandlerCommand> commandMap = GetTransactionCommands.getCommands(accountService);
        TransactionHandlerCommand transactionHandlerCommand = commandMap.get(type);
        return transactionHandlerCommand.execute(transaction);
    }
 }
