package com.training.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public record Employee(
    long id,
    String name,
    String department,
    String title,
    BigDecimal salary,
    List<String> skills
) {
    public Employee {
        Objects.requireNonNull(name);
        Objects.requireNonNull(department);
        Objects.requireNonNull(title);
        Objects.requireNonNull(salary);
        Objects.requireNonNull(skills);
    }
    
    public int getAge() {
        return (int) (id % 50) + 20; // Simplified age based on id
    }
}
