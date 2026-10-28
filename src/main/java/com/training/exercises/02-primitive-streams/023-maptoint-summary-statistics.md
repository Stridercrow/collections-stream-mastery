# Exercise 023 — mapToInt: Summary Statistics of Ages

## Objective
Train recognition of `summaryStatistics()` as the one-shot stats collector for primitive streams.

## Ask
Given a list of employees, return an `IntSummaryStatistics` with count, sum, min, average, and max of all ages.

## Input
List<Employee> employees (age derived from id via getAge())

## Expected Output
IntSummaryStatistics{count=..., sum=..., min=..., average=..., max=...}

## API Category
Primitive Streams

## Hints

### Hint 1
Computing count, sum, min, average, max separately means traversing the data five times.

### Hint 2
`IntStream` has a single terminal method that computes ALL statistics in one pass.

### Hint 3
The return type is `IntSummaryStatistics`.

## What to investigate in the Java API
- Class: `java.util.stream.Stream` — `mapToInt(ToIntFunction<T>)`
- Class: `java.util.stream.IntStream` — `summaryStatistics()`
- Class: `java.util.IntSummaryStatistics` — the result object

## Four Implementation Passes

### 1. Imperative Java
```java
public static IntSummaryStatistics exercise023Imperative(List<Employee> employees) {
    IntSummaryStatistics stats = new IntSummaryStatistics();
    for (Employee e : employees) {
        stats.accept(e.getAge());
    }
    return stats;
}
```

### 2. Stream without Collectors
```java
public static IntSummaryStatistics exercise023(List<Employee> employees) {
    return employees.stream()
        .mapToInt(Employee::getAge)
        .summaryStatistics();
}
```

### 3. Stream + Collectors
Alternative with a collector:
```java
public static IntSummaryStatistics exercise023Collect(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.summarizingInt(Employee::getAge));
}
```

### 4. Downstream Collector Composition
The collector form is what you use inside groupingBy:
```java
public static Map<String, IntSummaryStatistics> exercise023Downstream(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.summarizingInt(Employee::getAge)
        ));
}
```

### Edge cases
- Empty list -> count=0, sum=0, min=Integer.MAX_VALUE, max=Integer.MIN_VALUE, average=0.0
- Single element -> min = max = that element
- stats.accept(value) adds a value manually — useful in imperative loops

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Stream.mapToInt(ToIntFunction).summaryStatistics()
```

### Recognition pattern
- "Need count AND sum AND min AND max AND average"
- One call gives everything — don't make five passes
- In grouping contexts: Collectors.summarizingInt()

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement ageStatistics(List<Employee>) using mapToInt().summaryStatistics()
4. Check if you got it right
