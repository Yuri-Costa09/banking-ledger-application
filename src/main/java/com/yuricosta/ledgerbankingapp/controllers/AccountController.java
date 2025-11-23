package com.yuricosta.ledgerbankingapp.controllers;

import com.yuricosta.ledgerbankingapp.dtos.CreateAccountRequestDto;
import com.yuricosta.ledgerbankingapp.models.Account;
import com.yuricosta.ledgerbankingapp.services.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/accounts")
public class AccountController {

    private final AccountService _accountService;

    public AccountController(AccountService accountService) {
        this._accountService = accountService;
    }

    @PostMapping("/create") // not using dtos cuz no needs
    public ResponseEntity<Account> createAccount(
            @RequestBody @Valid CreateAccountRequestDto request
            ) {
        Account account = _accountService.createAccount(request.accountName());
        return ResponseEntity.status(HttpStatus.CREATED).body(account);

    }
}
