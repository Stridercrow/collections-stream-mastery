# Exercise 009 — Top 3 Highest Paid Employees

## Objective
Train recognition of `Stream.limit()` for truncating a stream after sorting.

## Ask
Given a list of employees, return the top 3 highest paid employees.

## Input
List<Employee> employees = [Alice(50000), Bob(90000), Charlie(70000), Diana(120000), Eve(60000)]

## Expected Output
[Diana(120000), Bob(90000), Charlie(70000)]

## API Category
Stream / sorted + limit

## Hints

### Hint 1
First sort employees by salary in descending order (see Exercise 008).

### Hint 2
`Stream` has a method that keeps only the first N elements.

### Hint 3
The pattern is: sort -> limit -> collect.

## What to investigate in the Java API
- Class: `java.util.stream.Stream`
- Look for:
  - `sorted(Comparator)` 
  - `limit(long maxSize)` — an intermediate, short-circuiting-ish operation

## Four Implementation Passes

### 1. Imperative Java
```java
public static List<Employee> exercise009Imperative(List<Employee> employees) {
    List<Employee> copy = new ArrayList<>(employees);
    copy.sort((a, b) -> b.salary().compareTo(a.salary()));
    return copy.subList(0, Math.min(3, copy.size()));
}
```

### 2. Stream without Collectors
```java
public static List<Employee> exercise009(List<Employee> employees) {
    return employees.stream()
        .sorted(Comparator.comparing(Employee::salary).reversed())
        .limit(3)
        .toList();
}
```

### 3. Stream + Collectors
```java
public static List<Employee> exercise009Collect(List<Employee> employees) {
    return employees.stream()
        .sorted(Comparator.comparing(Employee::salary).reversed())
        .limit(3)
        .collect(Collectors.toList());
}
```

### 4. Downstream Collector Composition
Not applicable — limit() is a stream operation, not a collector.

### Edge cases
- Fewer than 3 employees -> returns all of them
- Empty list -> empty list
- limit(0) -> always empty

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Stream.sorted(...).limit(n).toList()
```

### Recognition pattern
- "Top N" / "First N" requirements
- sorted() + limit() combo
- limit() is applied AFTER sorting to keep the best N

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement top3HighestPaid(List<Employee>) using sorted().limit()
4. Check if you got it right
