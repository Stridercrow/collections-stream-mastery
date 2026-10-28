# Exercise 004 — Filter Employees by Department

## Objective
Train recognition of `Stream.filter()` for selecting elements that match a condition.

## Ask
Given a list of employees and a department name, return a list of employees who work in that department.

## Input
List<Employees> employees = [Alice(Engineering), Bob(Engineering), Charlie(Sales)]
String department = "Engineering"

### Expected output
[Alice, Bob]

### API Gateway
Stream / Filter

### Hints

### Hint 1
You need to kepp only elements that satisfy a condition.

### Hint 2
`Stream` has a method that takes a `Predicate` and returns only elements that match.

### Hint 3
The method is an intermediate operation, followed by a terminal operation like `toList()`

### What to investigate in the Java API
- Class: `java.util.stream.Stream`
- Look for a method that:
    - Takes a `Predicate<T>`
    - Returns a `Stream<T>`
    - Filters elements based on the predicate

### Four implementation Passes

### 1. Imperative Java
```java
public static List<Employee> exercise004Imperative(List<Employee> employees, String dept) {
    List<Employee> result = new ArrayList<>();
    for(Employee e : employess) {
        if (e.department().equals(dept)) {
            result.add(e);
        }
    }
}
```
### 2. Stream without Collectors
```java
public static List<Employee> exercise004(List<Employee> employees, String dept) {
    return employees.stream()
        .filter(e -> e.department().equals(dept))
        .toList();
}
```

### 3. Stream + Collectors
```java
public static List<Employee> exercise004Collect(List<Employee> employees, String dept) {
    return employees.stream()
        .filter(e -> e.department().equals(dept))
        .collect(Collectors.toList());
}
```

### Downstram Collector Composition
Not applicable -- this is a basic filter operation

### Edge cases
- Empty list -> empty list
- No employees in department -> empty list
- Null department -> handle appropriately

### API Recognition
What Java API should I immediately think about when I see this requirement?
Stream.filter(Predicate).toList()

### Recognition pattern:
- Filter by condition
- Keep only matching elements
- Use filter() with a predicate
