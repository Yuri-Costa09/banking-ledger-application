package com.yuricosta.ledgerbankingapp.dtos;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record TransferRequestDto(
        @NotNull
        UUID sourceAccountId,

        @NotNull
        UUID destinationAccountId,

        @NotNull
        @Positive
        @Digits(integer = 10, fraction = 2)
        Double amount
) {
}
