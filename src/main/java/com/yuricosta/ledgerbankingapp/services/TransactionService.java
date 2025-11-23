package com.yuricosta.ledgerbankingapp.services;

import com.yuricosta.ledgerbankingapp.enums.TransactionType;
import com.yuricosta.ledgerbankingapp.models.Transaction;
import com.yuricosta.ledgerbankingapp.repositories.AccountRepository;
import com.yuricosta.ledgerbankingapp.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

// TODO: Eliminar essas exceptions genéricas
@Service
public class TransactionService {

    private final TransactionRepository _transactionRepository;
    private final AccountRepository _accountRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              AccountRepository accountRepository) {
        this._accountRepository = accountRepository;
        this._transactionRepository = transactionRepository;
    }

    @Transactional
    public void performTransfer(UUID sourceAccountId,
                                   UUID destinationAccountId,
                                   BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive"); // 0
        }
        var sourceAccount = _accountRepository.findById(sourceAccountId);
        if (sourceAccount.isEmpty()) {
            throw new IllegalArgumentException("Source account not found"); // 1
        }
        var targetAccount = _accountRepository.findById(destinationAccountId);
        if (targetAccount.isEmpty()) {
            throw new IllegalArgumentException("Destination account not found"); // 2
        }
        if (sourceAccountId.equals(destinationAccountId)) {
            throw new IllegalArgumentException("Source and destination accounts must be different"); // 3
        }

        // verificar se a conta de origem tem saldo suficiente
        if (sourceAccount.get().getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds in source account"); // 4
        }

        Transaction tx = Transaction.createTransaction(
                sourceAccount.get(),
                targetAccount.get(),
                amount
        );

        // setar tipo da transacao como TRANSFER
        tx.setType(TransactionType.TRANSFER);

        _transactionRepository.save(tx);

        sourceAccount.get().withdraw(amount);
        targetAccount.get().deposit(amount);
    }

}
