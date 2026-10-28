# Exercise 100 — Mixed: Full API recognition challenge

## Objective
Capstone: chain groupingBy + averagingInt + entrySet().stream() + max + comparingByValue into ONE pipeline.

## Ask
Given a list of employees, find the department with the highest average salary. Return it as `Map.Entry<String, Double>` (department -> average).

## Input
List<Employee> employees = [Alice(Engineering, 60000), Bob(Engineering, 80000), Charlie(Sales, 90000)]

## Expected Output
Sales=90000.0  (average 90000 beats Engineering's 70000)

## API Category
Mixed API Challenges

## Hints

### Hint 1
Stage 1: compute average salary per department — a groupingBy with a numeric downstream.

### Hint 2
Stage 2: find the maximum entry of that map by VALUE — Exercise 083's pattern.

### Hint 3
Both stages fit one expression: collect to a Map, then entrySet().stream().max(...).

## What to investigate in the Java API
- Class: `java.util.stream.Collectors` — `groupingBy`, `averagingInt`
- Interface: `java.util.Map` — `entrySet()`
- Interface: `java.util.Map.Entry` — `comparingByValue()`
- Class: `java.util.stream.Stream` — `max(Comparator)`, `orElseThrow()`

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map.Entry<String, Double> exercise100Imperative(List<Employee> employees) {
    Map<String, List<BigDecimal>> byDept = new HashMap<>();
    for (Employee e : employees) {
        byDept.computeIfAbsent(e.department(), k -> new ArrayList<>()).add(e.salary());
    }
    String bestDept = null;
    double bestAvg = Double.NEGATIVE_INFINITY;
    for (Map.Entry<String, List<BigDecimal>> entry : byDept.entrySet()) {
        double sum = 0;
        for (BigDecimal s : entry.getValue()) sum += s.doubleValue();
        double avg = sum / entry.getValue().size();
        if (avg > bestAvg) {
            bestAvg = avg;
            bestDept = entry.getKey();
        }
    }
    return Map.entry(bestDept, bestAvg);
}
```

### 2. Stream without Collectors
Two-stage pipeline, explicit intermediate map:
```java
public static Map.Entry<String, Double> exercise100Staged(List<Employee> employees) {
    Map<String, Double> averages = new HashMap<>();
    employees.forEach(e -> averages.merge(e.department(), e.salary().doubleValue(),
        (a, b) -> (a + b) / 2)); // NOTE: flawed averaging via merge — illustration only
    return averages.entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .orElseThrow();
}
```

### 3. Stream + Collectors (the idiomatic solution)
```java
public static Map.Entry<String, Double> exercise100(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.averagingInt(e -> e.salary().intValue())
        ))
        .entrySet()
        .stream()
        .max(Map.Entry.comparingByValue())
        .orElseThrow();
}
```

### 4. Downstream Collector Composition
Stay in ONE collect using collectingAndThen as the finisher:
```java
public static Map.Entry<String, Double> exercise100OneCollect(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.collectingAndThen(
            Collectors.groupingBy(
                Employee::department,
                Collectors.averagingInt(e -> e.salary().intValue())
            ),
            map -> map.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow()
        ));
}
```

### Edge cases
- Empty input -> orElseThrow() throws NoSuchElementException — acceptable or replace with Optional return
- averagingInt truncates BigDecimal to int; full precision: reducing with BigDecimal::add then divide
- This pipeline composes FOUR exercises: 038 (groupingBy), 041 (averagingInt), 079-080 (entry comparators), 083 (max entry)

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
groupingBy(k, averagingInt(...)).entrySet().stream().max(Map.Entry.comparingByValue())
```

### Recognition pattern
- "Aggregate per group, then rank the groups"
- Two-stage shape: collect -> entrySet().stream() -> max/min
- The capstone proves the building blocks have become reflexes

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement deptWithHighestAvgSalary(List<Employee>) as one pipeline
4. Check if you got it right
