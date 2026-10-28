# Exercise 040 — groupingBy with summingInt: total ages by department

## Objective
Train recognition of `Collectors.summingInt()` as a numeric downstream collector.

## Ask
Given a list of employees, return a `Map<String, Integer>` with the sum of ages per department.

## Input
List<Employee> employees (age via getAge())

## Expected Output
{Engineering=..., Sales=...} — total age per department

## API Category
Grouping and Partitioning

## Hints

### Hint 1
Group by department, then per bucket you need a numeric sum.

### Hint 2
`Collectors` has a family of downstream collectors for primitives: summingInt, summingLong, summingDouble.

### Hint 3
`summingInt(ToIntFunction)` extracts the int from each element and adds it up.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for:
  - `groupingBy(Function classifier, Collector downstream)`
  - `summingInt(ToIntFunction)` — returns Integer
  - Related: `summingLong`, `summingDouble`

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, Integer> exercise040Imperative(List<Employee> employees) {
    Map<String, Integer> totals = new HashMap<>();
    for (Employee e : employees) {
        totals.merge(e.department(), e.getAge(), Integer::sum);
    }
    return totals;
}
```

### 2. Stream without Collectors
Not applicable — grouped numeric aggregation needs a collector.

### 3. Stream + Collectors
```java
public static Map<String, Integer> exercise040(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.summingInt(Employee::getAge)
        ));
}
```

### 4. Downstream Collector Composition
Compare with toMap-style aggregation — same result, different shape:
```java
public static Map<String, Integer> exercise040ToMap(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.toMap(
            Employee::department,
            Employee::getAge,
            Integer::sum
        ));
}
```

### Edge cases
- Empty group can't happen (keys come from elements)
- Value type is Integer (boxed) — summingInt returns Collector<T, ?, Integer>
- toMap + Integer::sum is equivalent; groupingBy scales better to complex downstreams

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.groupingBy(Classifier, Collectors.summingInt(ToIntFunction))
```

### Recognition pattern
- "Total / sum of numeric property per group"
- groupingBy + summingInt/Long/Double
- Flat map aggregation alternative: toMap(key, value, BinaryOperator)

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement totalAgesByDept(List<Employee>) using groupingBy + summingInt
4. Check if you got it right
