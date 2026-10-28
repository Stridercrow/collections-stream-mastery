# Exercise 089 — Collectors.groupingByConcurrent

## Objective
Train recognition of `Collectors.groupingByConcurrent()` — concurrent grouping into a ConcurrentMap.

## Ask
Given a list of employees, group them by department into a `ConcurrentMap<String, List<Employee>>` suitable for parallel streams.

## Input
List<Employee> employees = [Alice(Engineering), Bob(Sales), Charlie(Engineering)]

## Expected Output
{Engineering=[Alice, Charlie], Sales=[Bob]}

## API Category
Concurrent Collectors

## Hints

### Hint 1
Same signature family as groupingBy — classifier, optional map factory, optional downstream.

### Hint 2
`Collectors.groupingByConcurrent` produces a ConcurrentMap (ConcurrentHashMap).

### Hint 3
It performs best with parallelStream() — that's its reason to exist.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for:
  - `groupingByConcurrent(Function classifier)`
  - `groupingByConcurrent(Function classifier, Collector downstream)`
  - `groupingByConcurrent(Function classifier, Supplier<M> mapFactory, Collector downstream)`
- Interface: `java.util.concurrent.ConcurrentMap`

## Four Implementation Passes

### 1. Imperative Java
```java
public static ConcurrentMap<String, List<Employee>> exercise089Imperative(List<Employee> employees) {
    ConcurrentMap<String, List<Employee>> result = new ConcurrentHashMap<>();
    for (Employee e : employees) {
        result.computeIfAbsent(e.department(), k -> Collections.synchronizedList(new ArrayList<>()))
              .add(e);
    }
    return result;
}
```

### 2. Stream without Collectors
Not applicable — grouped Map building requires a collector.

### 3. Stream + Collectors
```java
public static Map<String, List<Employee>> exercise089(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingByConcurrent(Employee::department));
}
```

### 4. Downstream Collector Composition
Parallel + counting downstream:
```java
public static Map<String, Long> exercise089Counts(List<Employee> employees) {
    return employees.parallelStream()
        .collect(Collectors.groupingByConcurrent(Employee::department, Collectors.counting()));
}
```

### Edge cases
- Downstream collectors must NOT be ordered (toList is fine; the ORDER of elements within lists is not guaranteed)
- groupingByConcurrent + groupingBy (non-concurrent) downstream throws IllegalArgumentException
- On sequential streams it works but offers no advantage over groupingBy

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.groupingByConcurrent(Classifier)
```

### Recognition pattern
- "Parallel grouping of large datasets"
- groupingByConcurrent pairs with parallelStream
- Rule of thumb: parallel + collect -> use the Concurrent collector family

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement groupConcurrent(List<Employee>) with groupingByConcurrent
4. Check if you got it right
