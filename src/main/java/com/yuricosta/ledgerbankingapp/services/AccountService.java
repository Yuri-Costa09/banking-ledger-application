package com.yuricosta.ledgerbankingapp.services;

import com.yuricosta.ledgerbankingapp.models.Account;
import com.yuricosta.ledgerbankingapp.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository _accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this._accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return _accountRepository.findAll();
    }

    public Optional<Account> getAccountById(java.util.UUID accountId) {
        return _accountRepository.findById(accountId);
    }

    public Account createAccount(String accountName) {
        var newAccount = Account.createAccount(accountName);
        return _accountRepository.save(newAccount);
    }
}
