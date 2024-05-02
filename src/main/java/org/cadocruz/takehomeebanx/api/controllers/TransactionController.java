package org.cadocruz.takehomeebanx.api.controllers;

import org.cadocruz.takehomeebanx.api.TransactionAPI;
import org.cadocruz.takehomeebanx.domain.Account;
import org.cadocruz.takehomeebanx.domain.TransactionRequest;
import org.cadocruz.takehomeebanx.domain.TransactionResponse;
import org.cadocruz.takehomeebanx.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController implements TransactionAPI {

    @Autowired
    private TransactionService transactionService;

    @Override
    public ResponseEntity<Object> createTransaction(@RequestBody TransactionRequest transaction) {
        try {
            TransactionResponse transactionResponse = transactionService.createTransaction(transaction);
            if (transactionResponse == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("0");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(transactionResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("0");
        }
    }
}
