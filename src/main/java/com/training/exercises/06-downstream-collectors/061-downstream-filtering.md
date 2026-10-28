# Exercise 061 — Downstream filtering: high paid per department

## Objective
Train recognition of `Collectors.filtering()` and how it differs from upstream `filter()`.

## Ask
Given a list of employees, return a `Map<String, List<Employee>>` of departments to employees earning MORE than 90000. Departments with NO qualifying employee must still appear with an empty list.

## Input
List<Employee> employees = [Alice(Engineering, 60000), Bob(Engineering, 95000), Charlie(Sales, 50000)]

## Expected Output
{Engineering=[Bob(95000)], Sales=[]} — Sales appears with an empty list

## API Category
Downstream Collectors

## Hints

### Hint 1
If you filter BEFORE groupingBy, departments with no matches vanish from the map.

### Hint 2
`Collectors.filtering(Predicate, Collector)` applies the predicate per bucket, AFTER grouping.

### Hint 3
Every key the classifier produces stays in the map — empty buckets stay as empty lists.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for: `filtering(Predicate predicate, Collector downstream)`
- Key contrast: stream.filter() removes elements before grouping; filtering() removes them after

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, List<Employee>> exercise061Imperative(List<Employee> employees) {
    Map<String, List<Employee>> result = new HashMap<>();
    for (Employee e : employees) {
        result.computeIfAbsent(e.department(), k -> new ArrayList<>());
        if (e.salary().compareTo(BigDecimal.valueOf(90000)) > 0) {
            result.get(e.department()).add(e);
        }
    }
    return result;
}
```

### 2. Stream without Collectors
This UPSTREAM filter version is subtly DIFFERENT — Sales disappears:
```java
public static Map<String, List<Employee>> exercise061Upstream(List<Employee> employees) {
    return employees.stream()
        .filter(e -> e.salary().compareTo(BigDecimal.valueOf(90000)) > 0)
        .collect(Collectors.groupingBy(Employee::department));
    // {Engineering=[Bob]} — no Sales key!
}
```

### 3. Stream + Collectors
```java
public static Map<String, List<Employee>> exercise061(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.filtering(
                e -> e.salary().compareTo(BigDecimal.valueOf(90000)) > 0,
                Collectors.toList()
            )
        ));
}
```

### 4. Downstream Collector Composition
filtering + counting for presence-aware counts:
```java
public static Map<String, Long> exercise061Counts(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.filtering(
                e -> e.salary().compareTo(BigDecimal.valueOf(90000)) > 0,
                Collectors.counting()
            )
        ));
}
```

### Edge cases
- THE exam question: which keys exist in the map? downstream filtering keeps all; upstream filter drops empty groups
- Empty bucket values: [] with toList, 0 with counting
- filtering() needs Java 9+

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.groupingBy(Classifier, Collectors.filtering(Predicate, terminal))
```

### Recognition pattern
- "Group but keep groups with no matches"
- Requirement mentions empty groups / all categories present -> downstream filtering()
- Requirement only wants matching groups -> upstream filter()
- Same distinction applies to partitioningBy

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement highPaidByDept(List<Employee>) keeping empty departments, with filtering()
4. Check if you got it right
