### exercises/01-stream-fundamentals/005-map-to-names.md
```markdown
# Exercise 005 — Map Employees to Names

## Objective
Train recognition of `Stream.map()` for transforming elements.

## Ask
Given a list of employees, return a list of employee names.

## Input
List<Employee> employees = [Alice, Bob, Charlie]

### Expected output
["Alice", "Bob", "Charlie"]

## API Category
Stream / map

## Hints

### Hint 1
You need to transform each employee to their name.

### Hint 2
`Stream` has a method that applies a function to each element.

### Hint 3
The method is an intermediate operation called `map()`.

## What to investigate in the Java API
- Class: `java.util.stream.Stream`
- Look for a method that:
  - Takes a `Function<T, R>`
  - Returns a `Stream<R>`
  - Transforms each element

## Four Implementation Passes

### 1. Imperative Java
```java
public static List<String> exercise005Imperative(List<Employee> employees) {
    List<String> result = new ArrayList<>();
    for (Employee e : employees) {
        result.add(e.name());
    }
    return result;
}
```

### 2. Stream without Collectors
```java
public static List<String> exercise005(List<Employee> employees) {
    return employees.stream()
        .map(Employee::name)
        .toList();
}
```

### 3. Stream + Collectors
```java
public static List<String> exercise005Collect(List<Employee> employees) {
    return employees.stream()
        .map(Employee::name)
        .collect(Collectors.toList());
}
```

### 4. Downstream Collector Composition
Not applicable - this is a simple map operation

### Edge cases
- Empty list -> empty list
- Null name -> handle appropriately

### API Recognition
What java API should I immediately think about when I see this requirement?
Stream.map(Function).toList()

### Recognition pattern:
- Transform each element
- Extract a property from each object
- Use map() with a methor reference or lambda
