# Exercise 034 — Collectors.toMap with duplicate keys: keep first

## Objective
Train recognition of the 3-argument `toMap()` merge function to survive duplicate keys.

## Ask
Given a list of employees, build a `Map<String, Employee>` mapping each department to an employee. When a department appears more than once, keep the FIRST employee encountered.

## Input
List<Employee> employees = [Alice(Engineering), Bob(Sales), Charlie(Engineering)]

## Expected Output
{Engineering=Alice, Sales=Bob}  — Alice wins because she comes first

## API Category
Basic Collectors

## Hints

### Hint 1
The plain 2-arg `toMap` throws `IllegalStateException` when two elements produce the same key.

### Hint 2
There is a 3-argument overload whose third parameter decides how to combine conflicting values.

### Hint 3
The merge function takes (existing, replacement) and returns the winner — to keep the first, return the existing one.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for: `toMap(Function keyMapper, Function valueMapper, BinaryOperator mergeFunction)`
- `Function.identity()` — useful when the element itself is the value

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, Employee> exercise034Imperative(List<Employee> employees) {
    Map<String, Employee> result = new HashMap<>();
    for (Employee e : employees) {
        result.putIfAbsent(e.department(), e);
    }
    return result;
}
```

### 2. Stream without Collectors
Not applicable — building a Map requires a collector.

### 3. Stream + Collectors
```java
public static Map<String, Employee> exercise034(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.toMap(
            Employee::department,
            Function.identity(),
            (existing, replacement) -> existing
        ));
}
```

### 4. Downstream Collector Composition
```java
public static Map<String, Map<String, Employee>> exercise034Downstream(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::title,
            Collectors.toMap(
                Employee::department,
                Function.identity(),
                (existing, replacement) -> existing
            )
        ));
}
```

### Edge cases
- Keep LAST instead -> (existing, replacement) -> replacement
- Merge functions are also where you throw to enforce uniqueness: (a, b) -> { throw new IllegalStateException("dup"); }
- putIfAbsent() is the imperative equivalent of keep-first

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.toMap(keyMapper, valueMapper, (existing, replacement) -> existing)
```

### Recognition pattern
- Duplicate keys are possible -> ALWAYS think merge function
- keep first: return existing
- keep last: return replacement
- The IllegalStateException from 2-arg toMap is a classic production bug

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement firstEmployeeByDept(List<Employee>) with 3-arg toMap keeping the first
4. Check if you got it right
