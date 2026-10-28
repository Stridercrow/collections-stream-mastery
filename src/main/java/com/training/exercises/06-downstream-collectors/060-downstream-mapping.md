# Exercise 060 — Downstream mapping: department to employee names

## Objective
Train recognition of `Collectors.mapping()` to transform elements INSIDE a groupingBy bucket.

## Ask
Given a list of employees, return a `Map<String, Set<String>>` mapping each department to the set of employee NAMES in that department.

## Input
List<Employee> employees = [Alice(Engineering), Bob(Sales), Charlie(Engineering)]

## Expected Output
{Engineering=[Alice, Charlie], Sales=[Bob]}

## API Category
Downstream Collectors

## Hints

### Hint 1
Plain groupingBy would give Map<String, List<Employee>> — you need names, not employees.

### Hint 2
`Collectors.mapping(Function, Collector)` transforms each element before the final collector.

### Hint 3
The mapping transform extracts the name; the inner collector gathers into a Set.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for: `mapping(Function mapper, Collector downstream)`
- The mapper transforms each element; the downstream collects transformed elements

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, Set<String>> exercise060Imperative(List<Employee> employees) {
    Map<String, Set<String>> result = new HashMap<>();
    for (Employee e : employees) {
        result.computeIfAbsent(e.department(), k -> new HashSet<>()).add(e.name());
    }
    return result;
}
```

### 2. Stream without Collectors
Not applicable — the grouped shape requires collectors.

### 3. Stream + Collectors
```java
public static Map<String, Set<String>> exercise060(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.mapping(Employee::name, Collectors.toSet())
        ));
}
```

### 4. Downstream Collector Composition
mapping chains naturally — names joined into one string per department:
```java
public static Map<String, String> exercise060Joined(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.mapping(Employee::name, Collectors.joining(", "))
        ));
}
```

### Edge cases
- mapping() is USELESS at the top level of a stream (map() already does it) — it exists for DOWNSTREAM use
- toSet() dedupes names; use toList() to keep duplicates
- Empty department lists can't occur (keys derive from elements)

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.groupingBy(Classifier, Collectors.mapping(Extractor, terminal))
```

### Recognition pattern
- "Group by X, but collect only property Y of each element"
- mapping(extractor, toList()/toSet()/joining())
- map() transforms the stream; mapping() transforms a bucket

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement namesByDept(List<Employee>) using groupingBy + mapping + toSet
4. Check if you got it right
