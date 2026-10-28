# Exercise 035 — Collectors.toMap with merge function: sum salaries by department

## Objective
Train recognition of the merge function as an AGGREGATOR, not just a tie-breaker.

## Ask
Given a list of employees, build a `Map<String, BigDecimal>` with the TOTAL salary per department.

## Input
List<Employee> employees = [Alice(Engineering, 60000), Bob(Sales, 50000), Charlie(Engineering, 70000)]

## Expected Output
{Engineering=130000, Sales=50000}

## API Category
Basic Collectors

## Hints

### Hint 1
The key is the department; the value is each employee's salary.

### Hint 2
When the same key appears twice, you don't want to keep one — you want to ADD the values.

### Hint 3
`BigDecimal` has an `add` method whose signature matches `BinaryOperator`.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors` — `toMap(keyMapper, valueMapper, BinaryOperator mergeFunction)`
- Class: `java.math.BigDecimal` — `add(BigDecimal)`
- The merge function receives (existingValue, newValue) and returns the combined value

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, BigDecimal> exercise035Imperative(List<Employee> employees) {
    Map<String, BigDecimal> totals = new HashMap<>();
    for (Employee e : employees) {
        totals.merge(e.department(), e.salary(), BigDecimal::add);
    }
    return totals;
}
```

### 2. Stream without Collectors
Not applicable — building a Map requires a collector.

### 3. Stream + Collectors
```java
public static Map<String, BigDecimal> exercise035(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.toMap(
            Employee::department,
            Employee::salary,
            BigDecimal::add
        ));
}
```

### 4. Downstream Collector Composition
The groupingBy equivalent — both are idiomatic:
```java
public static Map<String, BigDecimal> exercise035Downstream(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.reducing(BigDecimal.ZERO, Employee::salary, BigDecimal::add)
        ));
}
```

### Edge cases
- Single employee per department -> merge never called, value = salary alone
- toMap-with-merge vs groupingBy: toMap is compact for "sum per key"; groupingBy generalizes to any downstream
- BigDecimal addition is order-independent, so parallel streams are safe here

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.toMap(keyMapper, valueMapper, BinaryOperator)
```

### Recognition pattern
- "Aggregate per key" (sum/max/min/merge)
- Merge function IS the aggregation: BigDecimal::add, Integer::sum, Long::max...
- Same shape as Map.merge(k, v, BinaryOperator) — same mental model

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement salaryTotalsByDept(List<Employee>) with 3-arg toMap and BigDecimal::add
4. Check if you got it right
