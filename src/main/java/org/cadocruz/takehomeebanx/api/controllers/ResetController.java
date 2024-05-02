package org.cadocruz.takehomeebanx.api.controllers;

import org.cadocruz.takehomeebanx.api.ResetAPI;
import org.cadocruz.takehomeebanx.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResetController implements ResetAPI {

    @Autowired
    private AccountService accountService;

    @Override
    public ResponseEntity<String> resetAccount() {
        accountService.cleanAccounts();
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }
}
