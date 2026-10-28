# Exercise 016 — Collect Employees to List

## Objective
Train recognition of the modern `Stream.toList()` (Java 16+) versus `Collectors.toList()`.

## Ask
Given a list of employees, return a new list containing all of them, produced through the Stream API.

## Input
List<Employee> employees = [Alice, Bob, Charlie]

## Expected Output
[Alice, Bob, Charlie] (a new list)

## API Category
Stream / toList

## Hints

### Hint 1
Java 16 added a terminal method on `Stream` itself that collects to a list — no `Collectors` import needed.

### Hint 2
The returned list is unmodifiable — you cannot add or remove elements from it.

### Hint 3
The classic alternative is `.collect(Collectors.toList())`, which returns a modifiable ArrayList.

## What to investigate in the Java API
- Class: `java.util.stream.Stream`
- Look for:
  - `toList()` — Java 16+, terminal, returns unmodifiable List
- Class: `java.util.stream.Collectors`
- Compare:
  - `Collectors.toList()` — modifiable ArrayList, allows nulls
  - `Collectors.toUnmodifiableList()` — unmodifiable, disallows nulls

## Four Implementation Passes

### 1. Imperative Java
```java
public static List<Employee> exercise016Imperative(List<Employee> employees) {
    List<Employee> result = new ArrayList<>();
    for (Employee e : employees) {
        result.add(e);
    }
    return result;
}
```

### 2. Stream without Collectors
```java
public static List<Employee> exercise016(List<Employee> employees) {
    return employees.stream().toList();
}
```

### 3. Stream + Collectors
```java
public static List<Employee> exercise016Collect(List<Employee> employees) {
    return employees.stream().collect(Collectors.toList());
}
```

### 4. Downstream Collector Composition
Stream.toList() cannot be used downstream — inside a collector you must use `Collectors.toList()`:
```java
public static Map<String, List<Employee>> exercise016Downstream(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(Employee::department, Collectors.toList()));
}
```

### Edge cases
- Stream.toList() result is unmodifiable — calling .add() throws UnsupportedOperationException
- Collectors.toList() result is modifiable
- Null elements: Collectors.toList() allows them; toList()/toUnmodifiableList() do not

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Stream.toList()                    // Java 16+, unmodifiable, no import
Stream.collect(Collectors.toList()) // modifiable, needed downstream
```

### Recognition pattern
- Default to stream.toList() in modern code
- Need to mutate the result afterwards? Use Collectors.toList()
- Inside groupingBy/mapping? Only Collectors.toList() works there

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement collectAll(List<Employee>) with both toList() and Collectors.toList()
4. Check if you got it right
