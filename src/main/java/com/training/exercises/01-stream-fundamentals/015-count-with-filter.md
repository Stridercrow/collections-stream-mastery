# Exercise 015 — Count Employees with Salary > 75000

## Objective
Train recognition of `Stream.count()` for counting elements that pass a filter.

## Ask
Given a list of employees, count how many have a salary greater than 75000. The result is a `long`.

## Input
List<Employee> employees = [Alice(60000), Bob(90000), Charlie(80000), Diana(70000)]

## Expected Output
2

## API Category
Stream / count

## Hints

### Hint 1
Filter first, then count — no need to collect to a list just to call size().

### Hint 2
`Stream` has a terminal method that returns the number of elements as a `long`.

### Hint 3
`count()` requires no arguments and never returns null.

## What to investigate in the Java API
- Class: `java.util.stream.Stream`
- Look for:
  - `filter(Predicate)`
  - `count()` — terminal operation returning `long`

## Four Implementation Passes

### 1. Imperative Java
```java
public static long exercise015Imperative(List<Employee> employees) {
    long count = 0;
    for (Employee e : employees) {
        if (e.salary().compareTo(BigDecimal.valueOf(75000)) > 0) {
            count++;
        }
    }
    return count;
}
```

### 2. Stream without Collectors
```java
public static long exercise015(List<Employee> employees) {
    return employees.stream()
        .filter(e -> e.salary().compareTo(BigDecimal.valueOf(75000)) > 0)
        .count();
}
```

### 3. Stream + Collectors
Alternative using `Collectors.counting()` (needed as a downstream collector):
```java
public static long exercise015Collect(List<Employee> employees) {
    return employees.stream()
        .filter(e -> e.salary().compareTo(BigDecimal.valueOf(75000)) > 0)
        .collect(Collectors.counting());
}
```

### 4. Downstream Collector Composition
This is where counting() really shines — counting per group:
```java
public static Map<String, Long> exercise015Downstream(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.filtering(
                e -> e.salary().compareTo(BigDecimal.valueOf(75000)) > 0,
                Collectors.counting()
            )
        ));
}
```

### Edge cases
- Empty list -> 0
- No match -> 0
- count() returns long, not int

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Stream.filter(predicate).count()
```

### Recognition pattern
- "How many elements...?"
- filter() + count()
- In grouping contexts use Collectors.counting() downstream
- Avoid .collect(toList()).size() — count() is cleaner and cheaper

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement countHighPaid(List<Employee>) using filter().count()
4. Check if you got it right
