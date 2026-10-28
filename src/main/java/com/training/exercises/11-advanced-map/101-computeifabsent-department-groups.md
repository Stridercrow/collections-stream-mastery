# Exercise 101 — Build Map with computeIfAbsent for department groups

## Objective
Section opener for Advanced Map: build a complete grouping map with an imperative loop powered by computeIfAbsent.

## Ask
Given a list of employees, build a `Map<String, List<Employee>>` of department -> employees using a loop and `computeIfAbsent` (NOT groupingBy).

## Input
List<Employee> employees = [Alice(Engineering), Bob(Sales), Charlie(Engineering)]

## Expected Output
{Engineering=[Alice, Charlie], Sales=[Bob]}

## API Category
Advanced Map Exercises

## Hints

### Hint 1
Loop over each employee; the map accumulates as you go.

### Hint 2
`computeIfAbsent` creates the list for new departments and returns the existing one otherwise.

### Hint 3
One line inside the loop: map.computeIfAbsent(dept, k -> new ArrayList<>()).add(emp).

## What to investigate in the Java API
- Interface: `java.util.Map` — `computeIfAbsent(K, Function<K, V>)`
- Compare with the declarative: `Collectors.groupingBy` — this exercise builds the same thing by hand

## Four Implementation Passes

### 1. Imperative Java (with the idiom)
```java
public static Map<String, List<Employee>> exercise101(List<Employee> employees) {
    Map<String, List<Employee>> result = new HashMap<>();
    for (Employee emp : employees) {
        result.computeIfAbsent(emp.department(), k -> new ArrayList<>()).add(emp);
    }
    return result;
}
```

### 2. Stream without Collectors
forEach is a stream-flavored loop — same imperative semantics:
```java
public static Map<String, List<Employee>> exercise101ForEach(List<Employee> employees) {
    Map<String, List<Employee>> result = new HashMap<>();
    employees.forEach(e -> result.computeIfAbsent(e.department(), k -> new ArrayList<>()).add(e));
    return result;
}
```

### 3. Stream + Collectors (the declarative twin — know both)
```java
public static Map<String, List<Employee>> exercise101Grouping(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(Employee::department));
}
```

### 4. Downstream Collector Composition
The imperative approach extends trivially to complex keys — here, department + title prefix:
```java
public static Map<String, List<Employee>> exercise101CompositeKey(List<Employee> employees) {
    Map<String, List<Employee>> result = new HashMap<>();
    for (Employee e : employees) {
        String key = e.department() + "/" + e.title();
        result.computeIfAbsent(key, k -> new ArrayList<>()).add(e);
    }
    return result;
}
```

### Edge cases
- Empty list -> empty map
- computeIfAbsent executes the mapping function ONLY for new keys — cheaper than containsKey+put+get
- This shape is the standard "manual grouping" — recognizable in every legacy codebase

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Map.computeIfAbsent(key, k -> new ArrayList<>()).add(element)
```

### Recognition pattern
- "Build groups in a loop"
- computeIfAbsent inside for/forEach
- Declarative alternative: groupingBy — when is the manual version better? Composite keys, custom list types, side effects

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement groupManually(List<Employee>) with a loop + computeIfAbsent
4. Check if you got it right
