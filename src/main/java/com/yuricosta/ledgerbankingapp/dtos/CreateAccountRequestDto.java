package com.yuricosta.ledgerbankingapp.dtos;

import jakarta.validation.constraints.NotNull;

public record CreateAccountRequestDto(
        @NotNull
        String accountName
) {
}
