# Exercise 038 — groupingBy: group employees by department

## Objective
Train recognition of `Collectors.groupingBy()` as the "group rows by column" collector.

## Ask
Given a list of employees, return a `Map<String, List<Employee>>` where each key is a department and each value is the list of employees in that department.

## Input
List<Employee> employees = [Alice(Engineering), Bob(Sales), Charlie(Engineering)]

## Expected Output
{Engineering=[Alice, Charlie], Sales=[Bob]}

## API Category
Grouping and Partitioning

## Hints

### Hint 1
You need one map entry per distinct department.

### Hint 2
`Collectors` has a method that classifies elements by a function and buckets them.

### Hint 3
The classifier function produces the map key; elements sharing a key land in the same list.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for:
  - `groupingBy(Function classifier)` — Map<K, List<T>>
  - `groupingBy(Function classifier, Collector downstream)` — custom value shape
  - `groupingBy(Function classifier, Supplier mapFactory, Collector downstream)` — custom Map type

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, List<Employee>> exercise038Imperative(List<Employee> employees) {
    Map<String, List<Employee>> result = new HashMap<>();
    for (Employee e : employees) {
        result.computeIfAbsent(e.department(), k -> new ArrayList<>()).add(e);
    }
    return result;
}
```

### 2. Stream without Collectors
Not applicable — multi-bucket Map building is collector territory.

### 3. Stream + Collectors
```java
public static Map<String, List<Employee>> exercise038(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(Employee::department));
}
```

### 4. Downstream Collector Composition
groupingBy IS the composition root — every downstream collector (counting, mapping, summingInt...) plugs in here.

### Edge cases
- classifier returning null -> groupingBy throws NullPointerException (unlike Map.computeIfAbsent patterns)
- Default Map is HashMap — unordered
- Empty list -> empty map
- Every element is placed in exactly ONE bucket

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Stream.collect(Collectors.groupingBy(Classifier))
```

### Recognition pattern
- "Group / bucket / categorize by property"
- Output shape Map<K, List<T>> by default
- SQL GROUP BY, pandas groupby — same concept
- Any non-list value -> add a downstream collector

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement byDepartment(List<Employee>) using groupingBy(Employee::department)
4. Check if you got it right
