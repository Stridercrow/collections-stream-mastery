# Exercise 069 — Downstream averagingInt: average salary by department

## Objective
Reinforce `Collectors.averagingInt()` with a derived int extractor.

## Ask
Given a list of employees, return a `Map<String, Double>` with the average salary (as int) per department.

## Input
List<Employee> employees = [Alice(Engineering, 60000), Bob(Engineering, 80000), Charlie(Sales, 50000)]

## Expected Output
{Engineering=70000.0, Sales=50000.0}

## API Category
Downstream Collectors

## Hints

### Hint 1
Same skeleton as summingInt — swap the downstream collector.

### Hint 2
averagingInt takes a ToIntFunction; extract the int from BigDecimal salary.

### Hint 3
The value type is Double — averages are always doubles.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for: `averagingInt(ToIntFunction<T>)` — returns Double
- Related: averagingLong, averagingDouble

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, Double> exercise069Imperative(List<Employee> employees) {
    Map<String, List<Integer>> byDept = new HashMap<>();
    for (Employee e : employees) {
        byDept.computeIfAbsent(e.department(), k -> new ArrayList<>()).add(e.salary().intValue());
    }
    Map<String, Double> result = new HashMap<>();
    byDept.forEach((dept, list) -> {
        int sum = 0;
        for (int v : list) sum += v;
        result.put(dept, (double) sum / list.size());
    });
    return result;
}
```

### 2. Stream without Collectors
Not applicable — grouped averaging needs a collector.

### 3. Stream + Collectors
```java
public static Map<String, Double> exercise069(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.averagingInt(e -> e.salary().intValue())
        ));
}
```

### 4. Downstream Collector Composition
Full-precision average with averagingDouble over the BigDecimal doubleValue:
```java
public static Map<String, Double> exercise069Precise(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.averagingDouble(e -> e.salary().doubleValue())
        ));
}
```

### Edge cases
- averagingInt computes sum and count in one pass
- Money averages: doubleValue() loses precision — acceptable for reporting, not for accounting
- averagingInt on huge int values: internally uses a long sum, so mid-size totals are safe

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.groupingBy(Classifier, Collectors.averagingInt(toIntFunction))
```

### Recognition pattern
- "Average per group" -> averagingInt/Long/Double
- Extractor can convert (intValue(), doubleValue()) inline
- Returns Double — no Optional downstream

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement avgSalaryByDept(List<Employee>) with averagingInt
4. Check if you got it right
