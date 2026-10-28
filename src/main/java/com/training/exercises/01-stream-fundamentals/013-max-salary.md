# Exercise 013 — Find Employee with Max Salary

## Objective
Train recognition of `Stream.max()` with a `Comparator` returning `Optional`.

## Ask
Given a list of employees, find the employee with the highest salary. Return `Optional<Employee>` to handle the empty case.

## Input
List<Employee> employees = [Alice(60000), Bob(120000), Charlie(90000)]

## Expected Output
Optional[Bob(120000)]

## API Category
Stream / max + Comparator

## Hints

### Hint 1
You don't need to sort the whole list to find the maximum.

### Hint 2
`Stream` has terminal methods that take a `Comparator` and return the extreme element as `Optional`.

### Hint 3
`max(Comparator)` returns the element for which the comparator says it's greatest.

## What to investigate in the Java API
- Class: `java.util.stream.Stream`
- Look for:
  - `max(Comparator<T>)` — returns `Optional<T>` with the maximum element
  - Related: `min(Comparator<T>)` — returns `Optional<T>` with the minimum element

## Four Implementation Passes

### 1. Imperative Java
```java
public static Optional<Employee> exercise013Imperative(List<Employee> employees) {
    Employee best = null;
    for (Employee e : employees) {
        if (best == null || e.salary().compareTo(best.salary()) > 0) {
            best = e;
        }
    }
    return Optional.ofNullable(best);
}
```

### 2. Stream without Collectors
```java
public static Optional<Employee> exercise013(List<Employee> employees) {
    return employees.stream()
        .max(Comparator.comparing(Employee::salary));
}
```

### 3. Stream + Collectors
Alternative using `Collectors.maxBy` (needed when used as a downstream collector):
```java
public static Optional<Employee> exercise013Collect(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.maxBy(Comparator.comparing(Employee::salary)));
}
```

### 4. Downstream Collector Composition
The collector form is exactly what you use downstream:
```java
public static Map<String, Optional<Employee>> exercise013Downstream(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.maxBy(Comparator.comparing(Employee::salary))
        ));
}
```

### Edge cases
- Empty list -> Optional.empty()
- Ties -> returns one of the tied elements (any one)
- max() needs a Comparator; natural ordering requires the type to implement Comparable and you'd still pass Comparator.naturalOrder()

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Stream.max(Comparator.comparing(Extractor))
```

### Recognition pattern
- "Find the X with the highest/lowest property"
- max(comparator) / min(comparator) — no sorting of the whole stream
- Returns Optional
- Inside groupingBy, use Collectors.maxBy / Collectors.minBy instead

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement highestPaid(List<Employee>) using max(Comparator.comparing(...))
4. Check if you got it right
