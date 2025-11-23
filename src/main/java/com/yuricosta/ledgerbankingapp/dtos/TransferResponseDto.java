package com.yuricosta.ledgerbankingapp.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferResponseDto(
        UUID transactionId,
        String type,
        BigDecimal amount,
        UUID sourceAccountId,
        UUID targetAccountId,
        BigDecimal sourceAccountBalanceAfter,
        BigDecimal targetAccountBalanceAfter,
        String status
) {
}
