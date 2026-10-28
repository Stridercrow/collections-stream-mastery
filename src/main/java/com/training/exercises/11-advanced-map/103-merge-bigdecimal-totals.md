# Exercise 103 — Build department salary totals with merge

## Objective
Reinforce Map.merge with a non-numeric accumulator: BigDecimal::add.

## Ask
Given a list of employees, build a `Map<String, BigDecimal>` of department -> total salary using a loop and `Map.merge`, preserving full decimal precision.

## Input
List<Employee> employees = [Alice(Engineering, 60000.50), Bob(Engineering, 70000.50), Charlie(Sales, 50000)]

## Expected Output
{Engineering=130001.00, Sales=50000} — no truncation

## API Category
Advanced Map Exercises

## Hints

### Hint 1
The incoming value is the employee's salary; the accumulator must ADD BigDecimals.

### Hint 2
`Map.merge(key, value, remapping)` — remapping combines (old, incoming).

### Hint 3
BigDecimal::add matches the BiFunction signature exactly.

## What to investigate in the Java API
- Interface: `java.util.Map` — `merge(K, V, BiFunction<V, V, V>)`
- Class: `java.math.BigDecimal` — `add(BigDecimal)`

## Four Implementation Passes

### 1. Imperative Java (with the idiom)
```java
public static Map<String, BigDecimal> exercise103(List<Employee> employees) {
    Map<String, BigDecimal> totals = new HashMap<>();
    for (Employee emp : employees) {
        totals.merge(emp.department(), emp.salary(), BigDecimal::add);
    }
    return totals;
}
```

### 2. Stream without Collectors
forEach variant:
```java
public static Map<String, BigDecimal> exercise103ForEach(List<Employee> employees) {
    Map<String, BigDecimal> totals = new HashMap<>();
    employees.forEach(e -> totals.merge(e.department(), e.salary(), BigDecimal::add));
    return totals;
}
```

### 3. Stream + Collectors (declarative twin)
```java
public static Map<String, BigDecimal> exercise103Reducing(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.reducing(BigDecimal.ZERO, Employee::salary, BigDecimal::add)
        ));
}
```

### 4. Downstream Collector Composition
Append a currency formatter to each total:
```java
public static Map<String, String> exercise103Formatted(List<Employee> employees) {
    Map<String, BigDecimal> totals = exercise103(employees);
    return totals.entrySet().stream()
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            e -> "$" + e.getValue().toPlainString()
        ));
}
```

### Edge cases
- BigDecimal is immutable — merge replaces the entry with the new sum, no mutation risk
- int truncation (summingInt) vs BigDecimal::add — precision-critical money math needs the latter
- compareTo vs equals for BigDecimal scale subtleties (2.0 vs 2.00) — matters if these maps become keys elsewhere

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Map.merge(key, salary, BigDecimal::add)
```

### Recognition pattern
- "Accumulate a non-primitive type in a Map"
- merge(k, v, Type::combiner) — the combiner matches the value type
- Stream twin: reducing(identity, mapper, operator)

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement exactTotals(List<Employee>) with merge + BigDecimal::add
4. Check if you got it right
