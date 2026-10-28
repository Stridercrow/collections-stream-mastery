# Exercise 073 — Downstream joining: names per department

## Objective
Reinforce `mapping + joining` as the "group to display string" pattern.

## Ask
Given a list of employees, return a `Map<String, String>` mapping each department to a comma-separated string of its employees' names.

## Input
List<Employee> employees = [Alice(Engineering), Bob(Sales), Charlie(Engineering)]

## Expected Output
{Engineering="Alice, Charlie", Sales="Bob"}

## API Category
Downstream Collectors

## Hints

### Hint 1
joining() works on Strings, but the stream elements are Employees.

### Hint 2
You need mapping() to transform each element to a name INSIDE the bucket.

### Hint 3
The chain: groupingBy(department, mapping(name, joining(", "))).

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for:
  - `mapping(Function, Collector)`
  - `joining(CharSequence delimiter)` as the innermost collector

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, String> exercise073Imperative(List<Employee> employees) {
    Map<String, StringJoiner> joiners = new HashMap<>();
    for (Employee e : employees) {
        joiners.computeIfAbsent(e.department(), k -> new StringJoiner(", "))
               .add(e.name());
    }
    Map<String, String> result = new HashMap<>();
    joiners.forEach((k, v) -> result.put(k, v.toString()));
    return result;
}
```

### 2. Stream without Collectors
Not applicable — grouped string building needs collectors.

### 3. Stream + Collectors
```java
public static Map<String, String> exercise073(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.mapping(Employee::name, Collectors.joining(", "))
        ));
}
```

### 4. Downstream Collector Composition
Prefix/suffix formatting per department:
```java
public static Map<String, String> exercise073Formatted(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.mapping(Employee::name, Collectors.joining(", ", "[", "]"))
        ));
}
```

### Edge cases
- Empty department lists can't occur (keys come from elements) — joining never sees an empty bucket
- mapping + joining is THE pattern for "one display string per group"
- StringJoiner is the manual-twin of joining()

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.groupingBy(Classifier, Collectors.mapping(Extractor, Collectors.joining(", ")))
```

### Recognition pattern
- "Names/emails/IDs as one string per group"
- mapping(extractor, joining(delimiter))
- joining's prefix/suffix overloads for formatted output

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement namesByDeptJoined(List<Employee>) with mapping + joining
4. Check if you got it right
