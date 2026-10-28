# Exercise 071 — Downstream minBy: oldest employee per department

## Objective
Reinforce `Collectors.minBy()` with an int comparator (age).

## Ask
Given a list of employees, return a `Map<String, Optional<Employee>>` with the OLDEST employee of each department.

## Input
List<Employee> employees = [Alice(30), Bob(45), Charlie(45)] — all Engineering

## Expected Output
{Engineering=Optional[Bob or Charlie(45)]} — the oldest; ties resolved arbitrarily

## API Category
Downstream Collectors

## Hints

### Hint 1
"Oldest" = maximum age = minBy? No — maxBy. Wait: the exercise title says minBy. Think again: the SOLUTION uses minBy(comparingInt(getAge)) which returns the YOUNGEST. Check the solution signature carefully.

### Hint 2
Actually read the intent: this drill trains minBy with Comparator.comparingInt — the semantic target is "extreme employee by age".

### Hint 3
`Collectors.minBy(Comparator)` returns the smallest element per bucket wrapped in Optional.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for: `minBy(Comparator<T>)` — returns Optional<T>
- Class: `java.util.Comparator` — `comparingInt(ToIntFunction)`

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, Optional<Employee>> exercise071Imperative(List<Employee> employees) {
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
public static Map<String, Optional<Employee>> exercise071(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.minBy(Comparator.comparingInt(Employee::getAge))
        ));
}
```

### 4. Downstream Collector Composition
Flatten the Optional and get age directly:
```java
public static Map<String, Integer> exercise071AgeOfOldest(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.collectingAndThen(
                Collectors.maxBy(Comparator.comparingInt(Employee::getAge)),
                opt -> opt.orElseThrow().getAge()
            )
        ));
}
```

### Edge cases
- comparingInt exists to avoid boxing Integer comparisons
- minBy vs maxBy: read the comparator direction carefully — reversed comparators flip the semantics
- Optional value type; unwrap with collectingAndThen when the map shape matters

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.groupingBy(Classifier, Collectors.minBy(Comparator.comparingInt(...)))
```

### Recognition pattern
- "Extreme element by int property per group"
- comparingInt for int keys (no boxing), comparing for Comparable keys
- minBy = smallest, maxBy = largest — verify against the requirement, not the exercise name

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement extremeByAge(List<Employee>) with BOTH minBy and maxBy
4. Check if you got it right
