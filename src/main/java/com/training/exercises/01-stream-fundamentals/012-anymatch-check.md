# Exercise 012 — Check If Any Employee Has Salary > 200000

## Objective
Train recognition of `Stream.anyMatch()` for existential checks on stream contents.

## Ask
Given a list of employees, return true if at least one employee has a salary greater than 200000, false otherwise.

## Input
List<Employee> employees = [Alice(60000), Bob(250000), Charlie(90000)]

## Expected Output
true

## API Category
Stream / anyMatch

## Hints

### Hint 1
You don't need the employee itself, just a yes/no answer about existence.

### Hint 2
`Stream` has a short-circuiting method that takes a `Predicate` and returns `boolean`.

### Hint 3
`anyMatch` stops as soon as one element matches — no need to process the whole stream.

## What to investigate in the Java API
- Class: `java.util.stream.Stream`
- Look for:
  - `anyMatch(Predicate)` — true if ANY element matches (short-circuits on first match)
  - Related: `allMatch(Predicate)` — true if ALL match (short-circuits on first failure)
  - Related: `noneMatch(Predicate)` — true if NO element matches

## Four Implementation Passes

### 1. Imperative Java
```java
public static boolean exercise012Imperative(List<Employee> employees) {
    for (Employee e : employees) {
        if (e.salary().compareTo(BigDecimal.valueOf(200000)) > 0) {
            return true;
        }
    }
    return false;
}
```

### 2. Stream without Collectors
```java
public static boolean exercise012(List<Employee> employees) {
    return employees.stream()
        .anyMatch(e -> e.salary().compareTo(BigDecimal.valueOf(200000)) > 0);
}
```

### 3. Stream + Collectors
Not applicable — this is a boolean terminal operation, no collector involved.

### 4. Downstream Collector Composition
Not applicable — match operations are terminal stream operations.

### Edge cases
- Empty list -> false (nothing matches "any")
- All match -> true, stops at the first element
- anyMatch on empty stream is always false; allMatch on empty stream is always true

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Stream.anyMatch(predicate)
```

### Recognition pattern
- "Does any element...?" -> anyMatch
- "Do all elements...?" -> allMatch
- "Is there no element...?" -> noneMatch
- All return boolean and short-circuit

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement hasVeryHighPaid(List<Employee>) using anyMatch()
4. Check if you got it right
