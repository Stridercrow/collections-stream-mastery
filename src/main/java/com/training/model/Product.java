package com.training.model;

import java.math.BigDecimal;
import java.util.Objects;

public record Product (
    long id,
    String name,
    String category,
    BigDecimal price,
    int rating
) {
    public Product {        
        Objects.nonNull(name);
        Objects.nonNull(category);
        Objects.nonNull(price);
    }
} 
