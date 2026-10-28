# Exercise 008 — Sort Employees by Salary (Descending)

## Objective
Train recognition of `Stream.sorted()` with a `Comparator` built via `Comparator.comparing()`.

## Ask
Given a list of employees, return a list sorted by salary from highest to lowest.

## Input
List<Employee> employees = [Alice(50000), Bob(90000), Charlie(70000)]

## Expected Output
[Bob(90000), Charlie(70000), Alice(50000)]

## API Category
Stream / sorted with Comparator

## Hints

### Hint 1
`Stream.sorted()` has an overload that takes a `Comparator`.

### Hint 2
`Comparator` has a static method that builds a comparator from a key extractor function.

### Hint 3
The comparator can be reversed with `.reversed()` for descending order.

## What to investigate in the Java API
- Class: `java.util.stream.Stream` — `sorted(Comparator)`
- Class: `java.util.Comparator`
- Look for:
  - `comparing(Function<T, U> keyExtractor)`
  - `reversed()` to invert the order
- Note: salary is a `BigDecimal`, so it implements `Comparable` — no manual compare needed

## Four Implementation Passes

### 1. Imperative Java
```java
public static List<Employee> exercise008Imperative(List<Employee> employees) {
    List<Employee> copy = new ArrayList<>(employees);
    copy.sort((a, b) -> b.salary().compareTo(a.salary()));
    return copy;
}
```

### 2. Stream without Collectors
```java
public static List<Employee> exercise008(List<Employee> employees) {
    return employees.stream()
        .sorted(Comparator.comparing(Employee::salary).reversed())
        .toList();
}
```

### 3. Stream + Collectors
```java
public static List<Employee> exercise008Collect(List<Employee> employees) {
    return employees.stream()
        .sorted(Comparator.comparing(Employee::salary).reversed())
        .collect(Collectors.toList());
}
```

### 4. Downstream Collector Composition
Not applicable — sorting is an intermediate operation, not a collector.

### Edge cases
- Empty list -> empty list
- Ties in salary -> stable sort keeps original relative order
- Never mutate the input list — sort a copy or use a stream

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Stream.sorted(Comparator.comparing(KeyExtractor).reversed()).toList()
```

### Recognition pattern
- "Sort by property X"
- Comparator.comparing(Extractor) for ascending
- .reversed() for descending
- sorted() does NOT mutate the source list

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement sortBySalaryDesc(List<Employee>) using sorted(Comparator.comparing(...).reversed())
4. Check if you got it right
