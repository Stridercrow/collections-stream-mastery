package com.training.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public record Order(
    long id,
    long customerId,
    List<Product> products,
    LocalDateTime orderDate,
    OrderStatus status
) {
    public Order {
        Objects.requireNonNull(products);
        Objects.requireNonNull(orderDate);
        Objects.requireNonNull(status);
    }

    public enum OrderStatus {
        PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED
    }

}
