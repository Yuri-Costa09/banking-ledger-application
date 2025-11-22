CREATE TABLE transactions
(
    id                UUID                        NOT NULL,
    amount            DECIMAL(10, 2)              NOT NULL,
    type              VARCHAR(255)                NOT NULL,
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    source_account_id UUID,
    target_account_id UUID,
    CONSTRAINT pk_transactions PRIMARY KEY (id)
);

ALTER TABLE transactions
    ADD CONSTRAINT FK_TRANSACTIONS_ON_SOURCE_ACCOUNT FOREIGN KEY (source_account_id) REFERENCES accounts (id);

ALTER TABLE transactions
    ADD CONSTRAINT FK_TRANSACTIONS_ON_TARGET_ACCOUNT FOREIGN KEY (target_account_id) REFERENCES accounts (id);
