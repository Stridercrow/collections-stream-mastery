# Exercise 010 — Skip and TakeWhile

## Objective
Train recognition of `Stream.skip()` and `Stream.takeWhile()` for positional windowing.

## Ask
Given a list of employees, skip the first 5 employees, then take employees while their salary is greater than 50000 (stop at the first employee whose salary is 50000 or less).

## Input
List<Employee> employees = [e1(80k), e2(70k), e3(90k), e4(60k), e5(75k), e6(95k), e7(85k), e8(30k), e9(80k)]

## Expected Output
[e6(95k), e7(85k)] — stops at e8 because 30k fails the predicate

## API Category
Stream / skip + takeWhile

## Hints

### Hint 1
`Stream` has a method that discards the first N elements of the stream.

### Hint 2
Java 9 added a method that takes elements only while a predicate holds, then stops.

### Hint 3
The pattern is: skip(N) -> takeWhile(predicate) -> collect.

## What to investigate in the Java API
- Class: `java.util.stream.Stream`
- Look for:
  - `skip(long n)` — discards the first n elements
  - `takeWhile(Predicate)` — takes elements while predicate is true, then stops
  - Related: `dropWhile(Predicate)` — the opposite, skips while predicate is true

## Four Implementation Passes

### 1. Imperative Java
```java
public static List<Employee> exercise010Imperative(List<Employee> employees) {
    List<Employee> result = new ArrayList<>();
    for (int i = 5; i < employees.size(); i++) {
        Employee e = employees.get(i);
        if (e.salary().compareTo(BigDecimal.valueOf(50000)) <= 0) {
            break;
        }
        result.add(e);
    }
    return result;
}
```

### 2. Stream without Collectors
```java
public static List<Employee> exercise010(List<Employee> employees) {
    return employees.stream()
        .skip(5)
        .takeWhile(e -> e.salary().compareTo(BigDecimal.valueOf(50000)) > 0)
        .toList();
}
```

### 3. Stream + Collectors
```java
public static List<Employee> exercise010Collect(List<Employee> employees) {
    return employees.stream()
        .skip(5)
        .takeWhile(e -> e.salary().compareTo(BigDecimal.valueOf(50000)) > 0)
        .collect(Collectors.toList());
}
```

### 4. Downstream Collector Composition
Not applicable — skip/takeWhile are stream operations, not collectors.

### Edge cases
- Fewer than 5 employees -> skip(5) consumes everything, result is empty
- All salaries above 50000 -> takes everything after the first 5
- First employee after skip fails the predicate -> empty result
- takeWhile stops at the FIRST failure; later valid elements are NOT included

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Stream.skip(n).takeWhile(predicate).toList()
```

### Recognition pattern
- "Skip the first N" -> skip(n)
- "Take until condition fails" -> takeWhile(predicate)
- "Drop until condition holds" -> dropWhile(predicate)
- Positional/ordered processing on a stream

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement skipAndTakeWhile(List<Employee>) using skip().takeWhile()
4. Check if you got it right
