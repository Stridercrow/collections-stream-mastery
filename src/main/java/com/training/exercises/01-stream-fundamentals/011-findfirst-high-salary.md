# Exercise 011 — Find First Employee with Salary > 100000

## Objective
Train recognition of `Stream.findFirst()` returning `Optional` for short-circuit search.

## Ask
Given a list of employees, find the first employee whose salary is greater than 100000. Return `Optional<Employee>` to handle the case where none exists.

## Input
List<Employee> employees = [Alice(60000), Bob(120000), Charlie(150000)]

## Expected Output
Optional[Bob(120000)]

## API Category
Stream / findFirst + Optional

## Hints

### Hint 1
First filter employees whose salary exceeds the threshold.

### Hint 2
`Stream` has a short-circuiting method that returns the first element wrapped in an `Optional`.

### Hint 3
The return type is `Optional<Employee>`, not `Employee` — this forces callers to handle the empty case.

## What to investigate in the Java API
- Class: `java.util.stream.Stream`
- Look for:
  - `filter(Predicate)`
  - `findFirst()` — short-circuiting terminal operation returning `Optional<T>`
- Related: `findAny()` — same contract but allows parallel streams to return any match

## Four Implementation Passes

### 1. Imperative Java
```java
public static Optional<Employee> exercise011Imperative(List<Employee> employees) {
    for (Employee e : employees) {
        if (e.salary().compareTo(BigDecimal.valueOf(100000)) > 0) {
            return Optional.of(e);
        }
    }
    return Optional.empty();
}
```

### 2. Stream without Collectors
```java
public static Optional<Employee> exercise011(List<Employee> employees) {
    return employees.stream()
        .filter(e -> e.salary().compareTo(BigDecimal.valueOf(100000)) > 0)
        .findFirst();
}
```

### 3. Stream + Collectors
Alternative using filtering within a collector (used inside groupingBy contexts):
```java
public static List<Employee> exercise011Collect(List<Employee> employees) {
    return employees.stream()
        .filter(e -> e.salary().compareTo(BigDecimal.valueOf(100000)) > 0)
        .limit(1)
        .collect(Collectors.toList());
}
```

### 4. Downstream Collector Composition
Not applicable — findFirst() is a terminal stream operation, not a collector.

### Edge cases
- No match -> Optional.empty()
- Empty list -> Optional.empty()
- Multiple matches -> only the first is returned (encounter order)
- Never return null — always Optional.empty()

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Stream.filter(predicate).findFirst()
```

### Recognition pattern
- "Find the first X matching a condition"
- filter() + findFirst()
- Returns Optional — empty when no match

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement findFirstHighPaid(List<Employee>) using filter().findFirst()
4. Check if you got it right
