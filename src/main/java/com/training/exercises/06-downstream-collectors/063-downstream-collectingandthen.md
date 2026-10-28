# Exercise 063 — Downstream collectingAndThen: unmodifiable lists per department

## Objective
Train recognition of `Collectors.collectingAndThen()` as the finisher-applying collector.

## Ask
Given a list of employees, return a `Map<String, List<Employee>>` where each department's list is UNMODIFIABLE.

## Input
List<Employee> employees = [Alice(Engineering), Bob(Sales), Charlie(Engineering)]

## Expected Output
{Engineering=[Alice, Charlie], Sales=[Bob]} — each list throws UnsupportedOperationException on add()

## API Category
Downstream Collectors

## Hints

### Hint 1
groupingBy can produce the lists, but it can't make them unmodifiable by itself.

### Hint 2
`Collectors.collectingAndThen(Collector, Function finisher)` runs a function on the FINISHED result.

### Hint 3
First collect to a list, then wrap it with Collections.unmodifiableList.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for: `collectingAndThen(Collector downstream, Function finisher)`
- Class: `java.util.Collections` — `unmodifiableList(List)`

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, List<Employee>> exercise063Imperative(List<Employee> employees) {
    Map<String, List<Employee>> result = new HashMap<>();
    for (Employee e : employees) {
        result.computeIfAbsent(e.department(), k -> new ArrayList<>()).add(e);
    }
    result.replaceAll((k, v) -> Collections.unmodifiableList(v));
    return result;
}
```

### 2. Stream without Collectors
Not applicable — the finisher step is a collector concept.

### 3. Stream + Collectors
```java
public static Map<String, List<Employee>> exercise063(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.collectingAndThen(
                Collectors.toList(),
                Collections::unmodifiableList
            )
        ));
}
```

### 4. Downstream Collector Composition
Finishers compose with ANY downstream — unwrap Optionals from maxBy:
```java
public static Map<String, Employee> exercise063MaxUnwrapped(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.collectingAndThen(
                Collectors.maxBy(Comparator.comparing(Employee::salary)),
                opt -> opt.orElseThrow()
            )
        ));
}
```

### Edge cases
- The finisher runs once per bucket, on the completed downstream result
- Collectors.toUnmodifiableList() can't be nested as a downstream — collectingAndThen(toList(), unmodifiableList) is the way
- Finishers can also change the TYPE (e.g., List -> Integer)

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.collectingAndThen(downstream, finisher)
```

### Recognition pattern
- "Do a final transformation on the collected result"
- collectingAndThen — the universal finisher wrapper
- Unwrapping Optionals, making unmodifiable, converting types — classic finisher jobs

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement immutableByDept(List<Employee>) using collectingAndThen
4. Check if you got it right
