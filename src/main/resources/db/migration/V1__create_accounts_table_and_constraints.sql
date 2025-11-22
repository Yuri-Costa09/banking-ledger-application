CREATE TABLE accounts
(
    id             UUID         NOT NULL,
    account_number VARCHAR(255) NOT NULL,
    owner_name     VARCHAR(255) NOT NULL,
    balance        DECIMAL(10, 2)      NOT NULL,
    created_at     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_accounts PRIMARY KEY (id)
);

ALTER TABLE accounts
    ADD CONSTRAINT uc_accounts_account_number UNIQUE (account_number);