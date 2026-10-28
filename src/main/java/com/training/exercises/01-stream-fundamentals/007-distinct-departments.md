# Exercise 007 — Get Distinct Departments

## Objective
Train recognition of `Stream.distinct()` and `Stream.sorted()` for deduplication and ordering.

## Ask
Given a list of employees, return a sorted list of the distinct department names.

## Input
List<Employee> employees = [Alice(Engineering), Bob(Sales), Charlie(Engineering)]

## Expected Output
[Engineering, Sales]

## API Category
Stream / distinct + sorted

## Hints

### Hint 1
First extract the department name from each employee.

### Hint 2
`Stream` has a method that removes duplicated elements (using `equals()`).

### Hint 3
`Stream.sorted()` with no arguments sorts in natural order (alphabetical for Strings).

## What to investigate in the Java API
- Class: `java.util.stream.Stream`
- Look for methods that:
  - Remove duplicate elements
  - Sort elements in natural order

## Four Implementation Passes

### 1. Imperative Java
```java
public static List<String> exercise007Imperative(List<Employee> employees) {
    Set<String> departments = new TreeSet<>();
    for (Employee e : employees) {
        departments.add(e.department());
    }
    return new ArrayList<>(departments);
}
```

### 2. Stream without Collectors
```java
public static List<String> exercise007(List<Employee> employees) {
    return employees.stream()
        .map(Employee::department)
        .distinct()
        .sorted()
        .toList();
}
```

### 3. Stream + Collectors
```java
public static Set<String> exercise007Collect(List<Employee> employees) {
    return employees.stream()
        .map(Employee::department)
        .collect(Collectors.toCollection(TreeSet::new));
}
```

### 4. Downstream Collector Composition
```java
public static Set<String> exercise007Downstream(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.mapping(
            Employee::department,
            Collectors.toCollection(TreeSet::new)
        ));
}
```

### Edge cases
- Empty list -> empty list
- All same department -> single element
- Null department -> record requires it non-null, so NPE cannot happen from Employee

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Stream.map(...).distinct().sorted().toList()
```

### Recognition pattern
- Unique values from a collection
- Sorted output
- map() to extract, distinct() to deduplicate, sorted() to order

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement distinctDepartments(List<Employee>) using map().distinct().sorted()
4. Check if you got it right
