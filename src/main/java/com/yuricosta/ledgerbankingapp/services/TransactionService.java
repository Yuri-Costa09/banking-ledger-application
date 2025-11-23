package com.yuricosta.ledgerbankingapp.services;

import com.yuricosta.ledgerbankingapp.dtos.TransferRequestDto;
import com.yuricosta.ledgerbankingapp.dtos.TransferResponseDto;
import com.yuricosta.ledgerbankingapp.enums.TransactionType;
import com.yuricosta.ledgerbankingapp.models.Account;
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
    public TransferResponseDto performTransfer(TransferRequestDto transferRequest) {
        // Validações de domínio
        BigDecimal amount = BigDecimal.valueOf(transferRequest.amount());
        validateTransfer(transferRequest.sourceAccountId(), transferRequest.destinationAccountId(), amount);

        // Buscar contas validadas e verificar se existem
        Account sourceAccount = _accountRepository.findById(transferRequest.sourceAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Source account not found"));
        Account destinationAccount = _accountRepository.findById(transferRequest.destinationAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Destination account not found"));

        // Criar e persistir transação
        Transaction tx = Transaction.createTransaction(sourceAccount, destinationAccount, amount);
        tx.setType(TransactionType.TRANSFER);
        _transactionRepository.save(tx);

        // Atualizar saldos
        sourceAccount.withdraw(amount);
        destinationAccount.deposit(amount);

        return new TransferResponseDto(
                tx.getId(),
                tx.getType().name(),
                tx.getAmount(),
                sourceAccount.getId(),
                destinationAccount.getId(),
                sourceAccount.getBalance(),
                destinationAccount.getBalance(),
                "SUCCESS"
        );
    }

    private void validateTransfer(UUID sourceAccountId, UUID destinationAccountId, BigDecimal amount) {
        // Validar amount
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        // Verificar se contas são diferentes
        if (sourceAccountId.equals(destinationAccountId)) {
            throw new IllegalArgumentException("Source and destination accounts must be different");
        }

        // Verificar saldo suficiente (conta já foi buscada e validada existir)
        Account sourceAccount = _accountRepository.findById(sourceAccountId).get();
        if (sourceAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds in source account");
        }
    }

}
