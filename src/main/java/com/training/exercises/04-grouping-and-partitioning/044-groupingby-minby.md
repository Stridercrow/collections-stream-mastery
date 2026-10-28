# Exercise 044 — groupingBy with minBy: lowest paid per department

## Objective
Reinforce `Collectors.minBy()` — the mirror of maxBy.

## Ask
Given a list of employees, return a `Map<String, Optional<Employee>>` with the lowest paid employee of each department.

## Input
List<Employee> employees = [Alice(Engineering, 60000), Bob(Engineering, 90000), Charlie(Sales, 70000)]

## Expected Output
{Engineering=Optional[Alice(60000)], Sales=Optional[Charlie(70000)]}

## API Category
Grouping and Partitioning

## Hints

### Hint 1
Identical structure to Exercise 043 — only the direction of comparison changes.

### Hint 2
`Collectors.minBy(Comparator)` returns the smallest element per bucket.

### Hint 3
Alternative trick: maxBy with a reversed comparator — but minBy is clearer.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for:
  - `minBy(Comparator)` — downstream returning Optional<T>
- Class: `java.util.Comparator` — `comparing(KeyExtractor)`, `reversed()`

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, Optional<Employee>> exercise044Imperative(List<Employee> employees) {
    Map<String, Employee> worst = new HashMap<>();
    for (Employee e : employees) {
        worst.merge(e.department(), e,
            (current, candidate) ->
                candidate.salary().compareTo(current.salary()) < 0 ? candidate : current);
    }
    Map<String, Optional<Employee>> result = new HashMap<>();
    worst.forEach((k, v) -> result.put(k, Optional.of(v)));
    return result;
}
```

### 2. Stream without Collectors
Not applicable — grouped min needs a collector.

### 3. Stream + Collectors
```java
public static Map<String, Optional<Employee>> exercise044(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.minBy(Comparator.comparing(Employee::salary))
        ));
}
```

### 4. Downstream Collector Composition
Equivalent via maxBy + reversed — recognition training:
```java
public static Map<String, Optional<Employee>> exercise044ViaMax(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.maxBy(Comparator.comparing(Employee::salary).reversed())
        ));
}
```

### Edge cases
- minBy(comparator) == maxBy(comparator.reversed()) — knowing this deepens the mental model
- Optional value type again; unwrap with collectingAndThen when needed
- Ties: any tied element may win

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.groupingBy(Classifier, Collectors.minBy(Comparator))
```

### Recognition pattern
- "Lowest/cheapest/oldest per group" -> minBy
- "Highest/most/expensive per group" -> maxBy
- Same Optional-wrapped signature

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement lowestPaidByDept(List<Employee>) using groupingBy + minBy
4. Check if you got it right
