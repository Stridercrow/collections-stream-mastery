# Exercise 097 — Optional with findAny: any high earner

## Objective
Train recognition of `findAny()` vs `findFirst()` and when the distinction matters.

## Ask
Given a list of employees, find ANY employee earning more than 150000. Return `Optional<Employee>`.

## Input
List<Employee> employees = [Alice(160000), Bob(200000), Charlie(100000)]

## Expected Output
Optional[Alice or Bob] — any match is acceptable; on a sequential stream it's typically the first

## API Category
Optional

## Hints

### Hint 1
The requirement says ANY match — encounter order is irrelevant.

### Hint 2
`Stream` has a method that returns any matching element, allowing parallel streams to pick whichever completes first.

### Hint 3
On sequential streams findAny() usually behaves like findFirst() — the difference shows in parallel execution.

## What to investigate in the Java API
- Class: `java.util.stream.Stream`
- Look for:
  - `findAny()` — returns Optional<T>, non-deterministic on parallel streams
  - `findFirst()` — deterministic, respects encounter order

## Four Implementation Passes

### 1. Imperative Java
```java
public static Optional<Employee> exercise097Imperative(List<Employee> employees) {
    for (Employee e : employees) {
        if (e.salary().compareTo(BigDecimal.valueOf(150000)) > 0) {
            return Optional.of(e);
        }
    }
    return Optional.empty();
}
```

### 2. Stream without Collectors
```java
public static Optional<Employee> exercise097(List<Employee> employees) {
    return employees.stream()
        .filter(e -> e.salary().compareTo(BigDecimal.valueOf(150000)) > 0)
        .findAny();
}
```

### 3. Stream + Collectors
Not applicable — search terminals don't use collectors.

### 4. Downstream Collector Composition
Parallel context — where findAny's contract actually pays off:
```java
public static Optional<Employee> exercise097Parallel(List<Employee> employees) {
    return employees.parallelStream()
        .filter(e -> e.salary().compareTo(BigDecimal.valueOf(150000)) > 0)
        .findAny(); // any thread's match is acceptable
}
```

### Edge cases
- No match -> Optional.empty()
- On parallel streams findFirst() costs MORE (must preserve order); findAny() is cheaper
- Existence checks that don't need the element? anyMatch() returns plain boolean

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Stream.filter(...).findAny()
```

### Recognition pattern
- "Any element matching X" (existence, not identity)
- findAny() — intent-declaring; same result as findFirst on sequential streams
- Need only true/false? anyMatch()
- Order matters? findFirst()

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement anyHighEarner(List<Employee>) using filter().findAny()
4. Check if you got it right
