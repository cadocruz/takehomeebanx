package org.cadocruz.takehomeebanx.api.controllers;

import org.cadocruz.takehomeebanx.api.BalanceAPI;
import org.cadocruz.takehomeebanx.domain.Account;
import org.cadocruz.takehomeebanx.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController implements BalanceAPI {

    @Autowired
    private AccountService accountService;

    @Override
    public ResponseEntity<String> getBalance(@RequestParam("account_id") String accountId) {
        Account account = accountService.findById(accountId);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("0");
        }
        return ResponseEntity.status(HttpStatus.OK).body(account.getBalance().toString());
    }
}
