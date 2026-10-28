# Exercise 072 — Downstream maxBy: youngest employee per department

## Objective
Deepen Comparator direction fluency: maxBy with a REVERSED int comparator selects the minimum.

## Ask
Given a list of employees, return a `Map<String, Optional<Employee>>` identifying, per department, the employee selected by `maxBy(comparingInt(getAge).reversed())` — which is effectively the YOUNGEST employee.

## Input
List<Employee> employees = [Alice(30), Bob(45), Charlie(28)] — all Engineering

## Expected Output
{Engineering=Optional[Charlie(28)]} — youngest, found via reversed maxBy

## API Category
Downstream Collectors

## Hints

### Hint 1
maxBy picks the element the comparator ranks HIGHEST.

### Hint 2
Reversing the comparator makes "highest" mean "smallest age".

### Hint 3
This is functionally identical to minBy(comparingInt(getAge)) — the drill trains seeing through both shapes.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for: `maxBy(Comparator<T>)`
- Class: `java.util.Comparator` — `comparingInt(...)`, `reversed()`

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, Optional<Employee>> exercise072Imperative(List<Employee> employees) {
    Map<String, Employee> best = new HashMap<>();
    for (Employee e : employees) {
        best.merge(e.department(), e,
            (current, candidate) ->
                candidate.getAge() < current.getAge() ? candidate : current);
    }
    Map<String, Optional<Employee>> result = new HashMap<>();
    best.forEach((k, v) -> result.put(k, Optional.of(v)));
    return result;
}
```

### 2. Stream without Collectors
Not applicable — grouped extreme needs a collector.

### 3. Stream + Collectors
```java
public static Map<String, Optional<Employee>> exercise072(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.maxBy(Comparator.comparingInt(Employee::getAge).reversed())
        ));
}
```

### 4. Downstream Collector Composition
The direct equivalent — minBy without reversal gymnastics:
```java
public static Map<String, Optional<Employee>> exercise072Direct(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.minBy(Comparator.comparingInt(Employee::getAge))
        ));
}
```

### Edge cases
- maxBy(cmp.reversed()) == minBy(cmp) — provable equivalence worth internalizing
- Ties: arbitrary winner among equal elements
- Read comparator chains left to right; reversed() applies to the WHOLE chain built so far

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.maxBy(Comparator.comparingInt(...).reversed())
// identical outcome:
Collectors.minBy(Comparator.comparingInt(...))
```

### Recognition pattern
- Comparator direction puzzles appear in interviews constantly
- reversed() inverts whatever precedes it in the chain
- When you see reversed + maxBy, mentally substitute minBy

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement youngestByDept(List<Employee>) two ways: maxBy+reversed and minBy
4. Check if you got it right
