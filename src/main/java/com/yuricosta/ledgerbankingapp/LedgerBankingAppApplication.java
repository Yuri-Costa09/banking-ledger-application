package com.yuricosta.ledgerbankingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LedgerBankingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(LedgerBankingAppApplication.class, args);
    }

}
