package com.yuricosta.ledgerbankingapp.controllers;

import com.yuricosta.ledgerbankingapp.dtos.TransferRequestDto;
import com.yuricosta.ledgerbankingapp.dtos.TransferResponseDto;
import com.yuricosta.ledgerbankingapp.services.AccountService;
import com.yuricosta.ledgerbankingapp.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/transfers")
public class TransactionController {

    private final TransactionService _transactionService;
    private final AccountService _accountService;

    public TransactionController(TransactionService transactionService, AccountService accountService) {
        this._transactionService = transactionService;
        this._accountService = accountService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponseDto> performTransfer(
            @RequestBody @Valid TransferRequestDto request
            ) {
        var response = _transactionService.performTransfer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
