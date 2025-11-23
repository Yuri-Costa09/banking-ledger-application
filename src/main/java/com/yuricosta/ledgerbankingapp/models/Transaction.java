package com.yuricosta.ledgerbankingapp.models;

import com.yuricosta.ledgerbankingapp.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    // adicionar validacoes
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TransactionType type; // DEPOSIT, WITHDRAWAL, TRANSFER

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // CONTA DE ORIGEM (Quem paga)
    // Pode ser NULL se for um DEPÓSITO (vem de fora)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_account_id", nullable = true)
    private Account sourceAccount;

    // CONTA DE DESTINO (Quem recebe)
    // Pode ser NULL se for um SAQUE (vai para fora)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_account_id", nullable = true)
    private Account targetAccount;

    protected Transaction() { }

    public static Transaction createTransaction(Account source, Account target, BigDecimal amount) {
        // 1. Validações de Integridade (Invariantes)
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser positivo.");
        }
        if (source.equals(target)) {
            throw new IllegalArgumentException("Conta de origem e destino não podem ser iguais.");
        }
        if (source == null || target == null) {
            throw new IllegalArgumentException("Transferência exige duas contas.");
        }

        // 2. Construção
        Transaction tx = new Transaction();
        tx.id = UUID.randomUUID();
        tx.sourceAccount = source;
        tx.targetAccount = target;
        tx.amount = amount;
        tx.type = TransactionType.TRANSFER;
        tx.createdAt = LocalDateTime.now();

        return tx;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public Account getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(Account targetAccount) {
        this.targetAccount = targetAccount;
    }

    public UUID getId() {
        return id;
    }
}