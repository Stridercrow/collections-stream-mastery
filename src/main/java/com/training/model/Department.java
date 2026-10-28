package com.training.model;

import java.util.List;
import java.util.Objects;

public record Department (
    String name,
    String manager,
    String location,
    List<Employee> employees

) {
    public Department {
        Objects.requireNonNull(name);
        Objects.requireNonNull(manager);
        Objects.requireNonNull(location);
        Objects.requireNonNull(employees);
    }
}
