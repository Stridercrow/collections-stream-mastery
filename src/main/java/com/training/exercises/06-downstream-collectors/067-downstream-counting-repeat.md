# Exercise 067 — Downstream counting (repetition drill)

## Objective
Rebuild `groupingBy + counting()` from memory — this is a deliberate repetition of Exercise 039 for muscle memory.

## Ask
Given a list of employees, return a `Map<String, Long>` with the number of employees per department. Do it without looking anything up.

## Input
List<Employee> employees = [Alice(Engineering), Bob(Sales), Charlie(Engineering)]

## Expected Output
{Engineering=2, Sales=1}

## API Category
Downstream Collectors

## Hints

### Hint 1
(You should not need any — that's the point of the drill. Peek only after a genuine attempt.)

### Hint 2
Classifier: department. Downstream: counting.

### Hint 3
Return type value is Long.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors` — `groupingBy`, `counting()`

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, Long> exercise067Imperative(List<Employee> employees) {
    Map<String, Long> counts = new HashMap<>();
    for (Employee e : employees) {
        counts.merge(e.department(), 1L, Long::sum);
    }
    return counts;
}
```

### 2. Stream without Collectors
Not applicable — grouped counting needs a collector.

### 3. Stream + Collectors
```java
public static Map<String, Long> exercise067(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(Employee::department, Collectors.counting()));
}
```

### 4. Downstream Collector Composition
Same shape with a different classifier — count by title:
```java
public static Map<String, Long> exercise067ByTitle(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(Employee::title, Collectors.counting()));
}
```

### Edge cases
- Empty list -> empty map
- Long, not Integer, in the value type

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.groupingBy(Classifier, Collectors.counting())
```

### Recognition pattern
- "Count per group" — one of the top-three most-used downstream patterns
- Classifier choice is the only thing that changes between use cases

## Repetition Challenge
THIS exercise IS the repetition challenge:
1. Close the file (and Exercise 039)
2. Open a scratch file
3. Implement countByDept(List<Employee>) from memory
4. Verify against exercise039 in MasterySolutions.java
