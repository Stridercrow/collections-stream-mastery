# Exercise 088 — Collectors.toConcurrentMap

## Objective
Train recognition of `Collectors.toConcurrentMap()` — the parallel-friendly Map builder.

## Ask
Given a list of employees, build a `ConcurrentMap<String, Long>` counting employees per department, using the CONCURRENT collector.

## Input
List<Employee> employees = [Alice(Engineering), Bob(Sales), Charlie(Engineering)]

## Expected Output
{Engineering=2, Sales=1}

## API Category
Concurrent Collectors

## Hints

### Hint 1
Same shape as toMap(key, value, merge) — but from the Concurrent family.

### Hint 2
`Collectors.toConcurrentMap` returns a ConcurrentMap (ConcurrentHashMap in practice).

### Hint 3
Value type Long: seed each entry with 1L and merge with Long::sum.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for:
  - `toConcurrentMap(Function keyMapper, Function valueMapper)` 
  - `toConcurrentMap(keyMapper, valueMapper, BinaryOperator mergeFunction)`
  - `toConcurrentMap(keyMapper, valueMapper, mergeFunction, Supplier<M> mapSupplier)`
- Interface: `java.util.concurrent.ConcurrentMap`

## Four Implementation Passes

### 1. Imperative Java
```java
public static ConcurrentMap<String, Long> exercise088Imperative(List<Employee> employees) {
    ConcurrentMap<String, Long> counts = new ConcurrentHashMap<>();
    for (Employee e : employees) {
        counts.merge(e.department(), 1L, Long::sum);
    }
    return counts;
}
```

### 2. Stream without Collectors
Not applicable — Map building requires a collector.

### 3. Stream + Collectors
```java
public static Map<String, Long> exercise088(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.toConcurrentMap(
            Employee::department,
            e -> 1L,
            Long::sum
        ));
}
```

### 4. Downstream Collector Composition
Best with parallel streams — same code, parallel execution:
```java
public static Map<String, Long> exercise088Parallel(List<Employee> employees) {
    return employees.parallelStream()
        .collect(Collectors.toConcurrentMap(
            Employee::department,
            e -> 1L,
            Long::sum
        ));
}
```

### Edge cases
- toConcurrentMap CANNOT take a downstream (no nesting like groupingBy) — it's a flat map builder
- On a sequential stream it still works — just no concurrency benefit
- mergeFunction is REQUIRED whenever duplicate keys are possible (same as toMap)
- No encounter-order guarantee on the resulting map

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.toConcurrentMap(keyMapper, valueMapper, mergeFunction)
```

### Recognition pattern
- "Large dataset + parallel stream + Map result"
- toConcurrentMap pairs with parallelStream
- No thread-safety issues: the collector merges concurrently by design

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement deptCountsConcurrent(List<Employee>) with toConcurrentMap
4. Check if you got it right
