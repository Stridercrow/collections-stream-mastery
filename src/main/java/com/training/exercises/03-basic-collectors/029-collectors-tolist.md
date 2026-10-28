# Exercise 029 — Collectors.toList

## Objective
Train recognition of `Collectors.toList()` as the classic collection terminal operation.

## Ask
Given a list of employees, return a list of those earning more than 80000, collected explicitly with a `Collector`.

## Input
List<Employee> employees = [Alice(60000), Bob(90000), Charlie(80000), Diana(95000)]

## Expected Output
[Bob(90000), Diana(95000)]

## API Category
Basic Collectors

## Hints

### Hint 1
Filter first, then collect.

### Hint 2
`Collectors` has a static method that accumulates elements into a `List`.

### Hint 3
Use it inside `stream().collect(...)`. Note that Java 16+ offers `stream().toList()` for the common case.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for:
  - `toList()` — returns a Collector producing a modifiable ArrayList
- Compare with: `Stream.toList()` (Java 16+, unmodifiable result)

## Four Implementation Passes

### 1. Imperative Java
```java
public static List<Employee> exercise029Imperative(List<Employee> employees) {
    List<Employee> result = new ArrayList<>();
    for (Employee e : employees) {
        if (e.salary().compareTo(BigDecimal.valueOf(80000)) > 0) {
            result.add(e);
        }
    }
    return result;
}
```

### 2. Stream without Collectors
```java
public static List<Employee> exercise029ToList(List<Employee> employees) {
    return employees.stream()
        .filter(e -> e.salary().compareTo(BigDecimal.valueOf(80000)) > 0)
        .toList();
}
```

### 3. Stream + Collectors
```java
public static List<Employee> exercise029(List<Employee> employees) {
    return employees.stream()
        .filter(e -> e.salary().compareTo(BigDecimal.valueOf(80000)) > 0)
        .collect(Collectors.toList());
}
```

### 4. Downstream Collector Composition
toList() is the most common downstream terminal inside groupingBy:
```java
public static Map<String, List<Employee>> exercise029Downstream(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(Employee::department, Collectors.toList()));
}
```

### Edge cases
- No match -> empty list
- Collectors.toList() returns a MUTABLE list; Stream.toList() returns an UNMODIFIABLE one
- Collectors.toList() is required as a downstream collector — Stream.toList() can't be nested

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Stream.filter(...).collect(Collectors.toList())
```

### Recognition pattern
- Build a list from a stream with explicit collector
- collect(Collectors.toList()) — classic form
- stream.toList() — modern shortcut (unmodifiable)
- Inside groupingBy/mapping — only Collectors.toList() applies

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement highPaidList(List<Employee>) using collect(Collectors.toList())
4. Check if you got it right
