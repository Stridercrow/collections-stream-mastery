package com.training.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public record Transaction(
        long id,
        long accountId,
        BigDecimal amount,
        LocalDateTime timestamp,
        TransactionType type
) {
    public Transaction {
        Objects.requireNonNull(amount);
        Objects.requireNonNull(timestamp);
        Objects.requireNonNull(type);
    }

    public enum TransactionType {
        DEPOSIT, WITHDRAWAL, TRANSFER, PAYMENT
    }
}
