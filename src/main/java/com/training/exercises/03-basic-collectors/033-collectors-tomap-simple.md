# Exercise 033 — Collectors.toMap: id to name

## Objective
Train recognition of `Collectors.toMap()` with key and value extractors.

## Ask
Given a list of employees, build a `Map<Long, String>` mapping each employee's id to their name.

## Input
List<Employee> employees = [Alice(id=1), Bob(id=2), Charlie(id=3)]

## Expected Output
{1=Alice, 2=Bob, 3=Charlie}

## API Category
Basic Collectors

## Hints

### Hint 1
`Collectors` has a method that builds a Map from stream elements.

### Hint 2
It takes two functions: one producing the key, one producing the value.

### Hint 3
For a direct property-to-property map: `toMap(Employee::id, Employee::name)`.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for: `toMap(Function keyMapper, Function valueMapper)`
- Related overloads:
  - `toMap(keyMapper, valueMapper, mergeFunction)` — duplicate handling
  - `toMap(keyMapper, valueMapper, mergeFunction, mapSupplier)` — Map type

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<Long, String> exercise033Imperative(List<Employee> employees) {
    Map<Long, String> result = new HashMap<>();
    for (Employee e : employees) {
        result.put(e.id(), e.name());
    }
    return result;
}
```

### 2. Stream without Collectors
Not applicable — building a Map requires a collector.

### 3. Stream + Collectors
```java
public static Map<Long, String> exercise033(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.toMap(Employee::id, Employee::name));
}
```

### 4. Downstream Collector Composition
toMap() can itself be a downstream collector:
```java
public static Map<String, Map<Long, String>> exercise033Downstream(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.toMap(Employee::id, Employee::name)
        ));
}
```

### Edge cases
- DUPLICATE KEYS throw IllegalStateException with the 2-arg overload!
- Null VALUES are allowed; null KEYS are not
- Default Map is HashMap — no ordering guarantee
- If duplicates are possible, always provide a merge function

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Stream.collect(Collectors.toMap(KeyMapper, ValueMapper))
```

### Recognition pattern
- "Build a lookup table / dictionary from a collection"
- Key from one property, value from another
- Keys can collide? Add the third merge-function argument

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement idToName(List<Employee>) using toMap(Employee::id, Employee::name)
4. Check if you got it right
