# Exercise 096 — Optional with findFirst: first Engineering employee

## Objective
Train the filter + findFirst pipeline — the most common Optional-producing combo.

## Ask
Given a list of employees, find the FIRST employee working in the Engineering department. Return `Optional<Employee>`.

## Input
List<Employee> employees = [Alice(Sales), Bob(Engineering), Charlie(Engineering)]

## Expected Output
Optional[Bob] — the first Engineering employee in encounter order

## API Category
Optional

## Hints

### Hint 1
Narrow the stream to matching elements first.

### Hint 2
`Stream` has a short-circuiting method that stops at the first element.

### Hint 3
It returns Optional — empty when nothing matched.

## What to investigate in the Java API
- Class: `java.util.stream.Stream`
- Look for:
  - `filter(Predicate)`
  - `findFirst()` — short-circuiting, returns Optional<T>
- Compare with `findAny()` — Exercise 097

## Four Implementation Passes

### 1. Imperative Java
```java
public static Optional<Employee> exercise096Imperative(List<Employee> employees) {
    for (Employee e : employees) {
        if (e.department().equals("Engineering")) {
            return Optional.of(e);
        }
    }
    return Optional.empty();
}
```

### 2. Stream without Collectors
```java
public static Optional<Employee> exercise096(List<Employee> employees) {
    return employees.stream()
        .filter(e -> e.department().equals("Engineering"))
        .findFirst();
}
```

### 3. Stream + Collectors
Not applicable — search terminals don't use collectors.

### 4. Downstream Collector Composition
Need the match plus a fallback in one expression? Chain Optional methods:
```java
public static Employee exercise096OrFallback(List<Employee> employees) {
    return employees.stream()
        .filter(e -> e.department().equals("Engineering"))
        .findFirst()
        .orElse(employees.getFirst()); // caller guarantees non-empty list
}
```

### Edge cases
- No match -> Optional.empty()
- findFirst respects ENCOUNTER ORDER — deterministic results
- filter + findFirst short-circuits: stops scanning after the match

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Stream.filter(...).findFirst()
```

### Recognition pattern
- "First element matching a condition"
- filter + findFirst — the canonical search idiom
- Optional.empty() IS the answer when nothing matches — not an error

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement firstEngineer(List<Employee>) using filter().findFirst()
4. Check if you got it right
