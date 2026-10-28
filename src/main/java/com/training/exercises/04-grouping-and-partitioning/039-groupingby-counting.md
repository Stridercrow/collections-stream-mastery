# Exercise 039 — groupingBy with counting: employees per department

## Objective
Train recognition of `Collectors.counting()` as a downstream collector.

## Ask
Given a list of employees, return a `Map<String, Long>` with the number of employees per department.

## Input
List<Employee> employees = [Alice(Engineering), Bob(Sales), Charlie(Engineering), Diana(Engineering)]

## Expected Output
{Engineering=3, Sales=1}

## API Category
Grouping and Partitioning

## Hints

### Hint 1
Start from grouping by department — the default value would be a List.

### Hint 2
`groupingBy` accepts a second argument: a downstream collector that transforms each bucket.

### Hint 3
`Collectors.counting()` counts the elements of each bucket — note the value type is Long.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for:
  - `groupingBy(Function classifier, Collector downstream)`
  - `counting()` — downstream returning Long

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, Long> exercise039Imperative(List<Employee> employees) {
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
public static Map<String, Long> exercise039(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(Employee::department, Collectors.counting()));
}
```

### 4. Downstream Collector Composition
counting() is itself a downstream — it can nest deeper (count per title within department):
```java
public static Map<String, Map<String, Long>> exercise039Downstream(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.groupingBy(Employee::title, Collectors.counting())
        ));
}
```

### Edge cases
- Value type is Long, not long or Integer
- Empty list -> empty map
- The two-arg groupingBy ALWAYS evaluates the downstream for every key — even empty buckets get 0 (contrast with filtering())

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.groupingBy(Classifier, Collectors.counting())
```

### Recognition pattern
- "Count per group"
- groupingBy + counting — the most common downstream pair
- Word frequency, hits per day, orders per customer — same shape

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement headcountByDept(List<Employee>) using groupingBy + counting
4. Check if you got it right
