# Exercise 076 — Downstream filtering: employees over 100k per department

## Objective
Second repetition of downstream `filtering()` — this time notice the difference from Exercise 061's threshold and predict the key set before running it.

## Ask
Given a list of employees, return a `Map<String, List<Employee>>` of departments to employees earning MORE than 100000. Departments with no qualifying employee still appear with an empty list.

## Input
List<Employee> employees = [Alice(Engineering, 120000), Bob(Sales, 50000), Charlie(HR, 90000)]

## Expected Output
{Engineering=[Alice(120000)], Sales=[], HR=[]} — all three keys present

## API Category
Downstream Collectors

## Hints

### Hint 1
Identical mechanics to Exercise 061 — filter AFTER grouping, not before.

### Hint 2
The predicate compares BigDecimal salary with BigDecimal.valueOf(100000).

### Hint 3
All classifier keys survive because filtering() runs inside each bucket.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for: `filtering(Predicate, Collector)` — Java 9+

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, List<Employee>> exercise076Imperative(List<Employee> employees) {
    Map<String, List<Employee>> result = new HashMap<>();
    for (Employee e : employees) {
        result.computeIfAbsent(e.department(), k -> new ArrayList<>());
        if (e.salary().compareTo(BigDecimal.valueOf(100000)) > 0) {
            result.get(e.department()).add(e);
        }
    }
    return result;
}
```

### 2. Stream without Collectors (upstream — different semantics!)
```java
public static Map<String, List<Employee>> exercise076Upstream(List<Employee> employees) {
    return employees.stream()
        .filter(e -> e.salary().compareTo(BigDecimal.valueOf(100000)) > 0)
        .collect(Collectors.groupingBy(Employee::department));
    // Sales and HR would VANISH from the map
}
```

### 3. Stream + Collectors
```java
public static Map<String, List<Employee>> exercise076(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.filtering(
                e -> e.salary().compareTo(BigDecimal.valueOf(100000)) > 0,
                Collectors.toList()
            )
        ));
}
```

### 4. Downstream Collector Composition
Count instead of list — zero-count groups stay visible:
```java
public static Map<String, Long> exercise076Counts(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.filtering(
                e -> e.salary().compareTo(BigDecimal.valueOf(100000)) > 0,
                Collectors.counting()
            )
        ));
}
```

### Edge cases
- The empty-bucket guarantee is the whole point — say it out loud in interviews
- With counting() downstream, groups show 0 instead of vanishing
- Compare with Exercise 061: only threshold and dataset differ; the pattern is identical

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.groupingBy(Classifier, Collectors.filtering(Predicate, Collectors.toList()))
```

### Recognition pattern
- Repetition drill for filtering()
- "Keep all groups, filter contents" -> filtering() downstream
- "Drop groups without matches" -> filter() upstream

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file (and Exercise 061)
2. Open a scratch file
3. Implement over100kByDept(List<Employee>) keeping empty groups
4. Check if you got it right
