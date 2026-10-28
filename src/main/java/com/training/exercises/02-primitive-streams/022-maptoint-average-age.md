# Exercise 022 — mapToInt: Average Employee Age

## Objective
Train recognition that `IntStream.average()` returns `OptionalDouble` and needs unwrapping.

## Ask
Given a list of employees, calculate the average of their ages. Return 0.0 for an empty list.

## Input
List<Employee> employees (age derived from id via getAge())

## Expected Output
A double — the average age, or 0.0 if the list is empty

## API Category
Primitive Streams

## Hints

### Hint 1
Bridge to the primitive world with `mapToInt()` first.

### Hint 2
`IntStream` has an `average()` method — check its return type carefully.

### Hint 3
The result is `OptionalDouble`; use `orElse(0.0)` to handle the empty case.

## What to investigate in the Java API
- Class: `java.util.stream.Stream` — `mapToInt(ToIntFunction<T>)`
- Class: `java.util.stream.IntStream` — `average()`
- Class: `java.util.OptionalDouble` — `orElse(double)`

## Four Implementation Passes

### 1. Imperative Java
```java
public static double exercise022Imperative(List<Employee> employees) {
    if (employees.isEmpty()) return 0.0;
    int total = 0;
    for (Employee e : employees) {
        total += e.getAge();
    }
    return (double) total / employees.size();
}
```

### 2. Stream without Collectors
```java
public static double exercise022(List<Employee> employees) {
    return employees.stream()
        .mapToInt(Employee::getAge)
        .average()
        .orElse(0.0);
}
```

### 3. Stream + Collectors
Alternative with a collector:
```java
public static double exercise022Collect(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.averagingInt(Employee::getAge));
}
```

### 4. Downstream Collector Composition
The collector form returns Double directly (no Optional) — handy inside groupingBy:
```java
public static Map<String, Double> exercise022Downstream(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.averagingInt(Employee::getAge)
        ));
}
```

### Edge cases
- Empty list -> OptionalDouble.empty() -> orElse(0.0) gives 0.0
- averagingInt() collector never returns Optional — it handles empty by returning 0.0 in a grouping context
- Integer division pitfall in the imperative version — cast to double BEFORE dividing

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Stream.mapToInt(ToIntFunction).average().orElse(0.0)
```

### Recognition pattern
- Average of a numeric property
- IntStream.average() -> OptionalDouble -> unwrap
- In grouping contexts: Collectors.averagingInt() returns plain Double

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement averageAge(List<Employee>) using mapToInt().average().orElse()
4. Check if you got it right
