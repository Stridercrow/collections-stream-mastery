# Exercise 078 — Downstream collectingAndThen: count wrapped in Optional

## Objective
Drill collectingAndThen's type-changing finisher: List-level counts rewrapped as Optional<Long>.

## Ask
Given a list of employees, return a `Map<String, Optional<Long>>` mapping each department to its employee count wrapped in an Optional.

## Input
List<Employee> employees = [Alice(Engineering), Bob(Sales), Charlie(Engineering)]

## Expected Output
{Engineering=Optional[2], Sales=Optional[1]}

## API Category
Downstream Collectors

## Hints

### Hint 1
Start from the familiar groupingBy + counting() — it yields Map<String, Long>.

### Hint 2
`collectingAndThen(downstream, finisher)` transforms the finished Long into whatever you return.

### Hint 3
The finisher here is Optional::of — note the method reference shape.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for: `collectingAndThen(Collector downstream, Function finisher)`
- Class: `java.util.Optional` — `of(T)`

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, Optional<Long>> exercise078Imperative(List<Employee> employees) {
    Map<String, Long> counts = new HashMap<>();
    for (Employee e : employees) {
        counts.merge(e.department(), 1L, Long::sum);
    }
    Map<String, Optional<Long>> result = new HashMap<>();
    counts.forEach((k, v) -> result.put(k, Optional.of(v)));
    return result;
}
```

### 2. Stream without Collectors
Not applicable — the finisher is a collector concept.

### 3. Stream + Collectors
```java
public static Map<String, Optional<Long>> exercise078(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.collectingAndThen(Collectors.counting(), Optional::of)
        ));
}
```

### 4. Downstream Collector Composition
Finishers compose freely — count as a formatted string:
```java
public static Map<String, String> exercise078Label(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.collectingAndThen(
                Collectors.counting(),
                count -> count + " employees"
            )
        ));
}
```

### Edge cases
- The finisher runs once per bucket after counting completes
- Optional::of vs Optional::ofNullable: counts are never null here, of() is fine
- Type transformation happens at the END — the downstream still sees raw Long values

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.collectingAndThen(Collectors.counting(), Optional::of)
```

### Recognition pattern
- "Wrap / convert the collected result" — finisher territory
- collectingAndThen(counting(), finisher) — counting is the most common base
- Finishers change types: Long -> Optional<Long>, List -> String, Optional -> T

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement optionalCountsByDept(List<Employee>) with collectingAndThen + Optional::of
4. Check if you got it right
