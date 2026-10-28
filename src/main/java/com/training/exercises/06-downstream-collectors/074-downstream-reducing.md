# Exercise 074 — Downstream reducing: total salaries by department

## Objective
Train recognition of the 3-argument `Collectors.reducing()` with an identity, mapper, and operator.

## Ask
Given a list of employees, return a `Map<String, BigDecimal>` with the exact total salary per department (no precision loss).

## Input
List<Employee> employees = [Alice(Engineering, 60000.25), Bob(Engineering, 70000.75), Charlie(Sales, 50000)]

## Expected Output
{Engineering=130001.00, Sales=50000}

## API Category
Downstream Collectors

## Hints

### Hint 1
BigDecimal isn't int/long/double, so the summingX family doesn't apply directly.

### Hint 2
`Collectors.reducing(identity, mapper, operator)` folds each bucket's mapped values.

### Hint 3
Identity: BigDecimal.ZERO. Mapper: Employee::salary. Operator: BigDecimal::add.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for: `reducing(U identity, Function mapper, BinaryOperator op)`
- Overloads: reducing(BinaryOperator), reducing(identity, BinaryOperator)

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, BigDecimal> exercise074Imperative(List<Employee> employees) {
    Map<String, BigDecimal> totals = new HashMap<>();
    for (Employee e : employees) {
        totals.merge(e.department(), e.salary(), BigDecimal::add);
    }
    return totals;
}
```

### 2. Stream without Collectors
Not applicable — grouped folding needs a collector.

### 3. Stream + Collectors
```java
public static Map<String, BigDecimal> exercise074(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.reducing(BigDecimal.ZERO, Employee::salary, BigDecimal::add)
        ));
}
```

### 4. Downstream Collector Composition
Compare with the toMap aggregation — identical result, different framing:
```java
public static Map<String, BigDecimal> exercise074ToMap(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.toMap(
            Employee::department,
            Employee::salary,
            BigDecimal::add
        ));
}
```

### Edge cases
- The identity (BigDecimal.ZERO) is also the value for... nothing here — groups are never empty, but the identity seeds every fold
- reducing(BinaryOperator) alone returns Optional — prefer the 3-arg form downstream
- Money sums: reducing with BigDecimal is the precision-safe pattern

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.groupingBy(Classifier, Collectors.reducing(identity, mapper, operator))
```

### Recognition pattern
- "Fold per group with a non-primitive accumulator"
- reducing(identity, mapper, operator) — the general-purpose downstream fold
- Equivalent toMap(key, value, merge) — choose by readability

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement exactSalaryTotals(List<Employee>) with reducing + BigDecimal
4. Check if you got it right
