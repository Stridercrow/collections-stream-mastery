# Exercise 030 — Collectors.toSet

## Objective
Train recognition of `Collectors.toSet()` for deduplicating into a `Set`.

## Ask
Given a list of employees, return a `Set` of the unique department names.

## Input
List<Employee> employees = [Alice(Engineering), Bob(Sales), Charlie(Engineering), Diana(Sales)]

## Expected Output
A set containing exactly: [Engineering, Sales]

## API Category
Basic Collectors

## Hints

### Hint 1
First extract the department from each employee.

### Hint 2
`Collectors` has a static method that accumulates elements into a `Set` — duplicates disappear automatically.

### Hint 3
The returned Set type is not specified — in practice it's a HashSet.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for: `toSet()` — Collector producing a Set (HashSet in practice)
- Related: `toCollection(TreeSet::new)` when you need a specific Set implementation

## Four Implementation Passes

### 1. Imperative Java
```java
public static Set<String> exercise030Imperative(List<Employee> employees) {
    Set<String> departments = new HashSet<>();
    for (Employee e : employees) {
        departments.add(e.department());
    }
    return departments;
}
```

### 2. Stream without Collectors
```java
public static Set<String> exercise030ToList(List<Employee> employees) {
    return employees.stream()
        .map(Employee::department)
        .collect(java.util.stream.Collectors.toSet());
}
```

### 3. Stream + Collectors
```java
public static Set<String> exercise030(List<Employee> employees) {
    return employees.stream()
        .map(Employee::department)
        .collect(Collectors.toSet());
}
```

### 4. Downstream Collector Composition
```java
public static Map<String, Set<String>> exercise030Downstream(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::title,
            Collectors.mapping(Employee::department, Collectors.toSet())
        ));
}
```

### Edge cases
- Empty list -> empty set
- Duplicates vanish by definition of Set
- toSet() makes no ordering guarantee — need sorted output? Use toCollection(TreeSet::new)

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Stream.map(...).collect(Collectors.toSet())
```

### Recognition pattern
- "Unique values of X"
- map() to extract + toSet() to deduplicate
- Specific Set type needed -> Collectors.toCollection(TreeSet::new)

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement uniqueDepartments(List<Employee>) using map().collect(toSet())
4. Check if you got it right
