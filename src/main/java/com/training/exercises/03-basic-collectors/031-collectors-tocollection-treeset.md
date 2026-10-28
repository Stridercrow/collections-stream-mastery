# Exercise 031 — Collectors.toCollection with TreeSet

## Objective
Train recognition of `Collectors.toCollection()` when a specific collection implementation is required.

## Ask
Given a list of employees, return the distinct department names in a `TreeSet` (sorted, deduplicated).

## Input
List<Employee> employees = [Alice(Sales), Bob(Engineering), Charlie(Sales), Diana(HR)]

## Expected Output
A TreeSet containing: [Engineering, HR, Sales] — sorted iteration order

## API Category
Basic Collectors

## Hints

### Hint 1
`Collectors.toList()` and `toSet()` don't let you choose the implementation.

### Hint 2
`Collectors` has a method that takes a `Supplier` of the target collection.

### Hint 3
Pass `TreeSet::new` and you get sorted, unique departments in one shot.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for: `toCollection(Supplier<C>)` — where C is any Collection
- Class: `java.util.TreeSet` — sorted, duplicate-free

## Four Implementation Passes

### 1. Imperative Java
```java
public static TreeSet<String> exercise031Imperative(List<Employee> employees) {
    TreeSet<String> departments = new TreeSet<>();
    for (Employee e : employees) {
        departments.add(e.department());
    }
    return departments;
}
```

### 2. Stream without Collectors
```java
public static TreeSet<String> exercise031Sorted(List<Employee> employees) {
    return new TreeSet<>(employees.stream()
        .map(Employee::department)
        .toList());
}
```

### 3. Stream + Collectors
```java
public static TreeSet<String> exercise031(List<Employee> employees) {
    return employees.stream()
        .map(Employee::department)
        .collect(Collectors.toCollection(TreeSet::new));
}
```

### 4. Downstream Collector Composition
```java
public static Map<String, TreeSet<String>> exercise031Downstream(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::title,
            Collectors.mapping(Employee::department,
                Collectors.toCollection(TreeSet::new))
        ));
}
```

### Edge cases
- Empty list -> empty TreeSet
- TreeSet sorts by natural ordering (or the Comparator given to its constructor)
- TreeSet rejects null elements
- Need insertion order instead? Use LinkedHashSet::new as the supplier

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Stream.collect(Collectors.toCollection(TreeSet::new))
```

### Recognition pattern
- "I need a specific collection type" (TreeSet, LinkedHashSet, ArrayDeque...)
- toCollection(Supplier) is the universal collector
- toList()/toSet() are just shortcuts when the implementation doesn't matter

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement sortedDepartments(List<Employee>) using toCollection(TreeSet::new)
4. Check if you got it right
