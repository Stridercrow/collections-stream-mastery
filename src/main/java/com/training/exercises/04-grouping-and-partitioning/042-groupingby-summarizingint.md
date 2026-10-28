# Exercise 042 — groupingBy with summarizingInt: age stats by department

## Objective
Train recognition of `Collectors.summarizingInt()` for multi-statistic downstream aggregation.

## Ask
Given a list of employees, return a `Map<String, IntSummaryStatistics>` with count, sum, min, average, and max of ages per department.

## Input
List<Employee> employees (age via getAge())

## Expected Output
{Engineering=IntSummaryStatistics{count=..., sum=..., min=..., average=..., max=...}, ...}

## API Category
Grouping and Partitioning

## Hints

### Hint 1
You need five statistics per group — don't run five separate groupingBy passes.

### Hint 2
`Collectors` has summarizingInt/Long/Double downstream collectors.

### Hint 3
`summarizingInt(ToIntFunction)` produces an `IntSummaryStatistics` per bucket.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for:
  - `groupingBy(Function classifier, Collector downstream)`
  - `summarizingInt(ToIntFunction)` — returns IntSummaryStatistics
- Class: `java.util.IntSummaryStatistics` — getCount(), getSum(), getMin(), getAverage(), getMax()

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, IntSummaryStatistics> exercise042Imperative(List<Employee> employees) {
    Map<String, IntSummaryStatistics> result = new HashMap<>();
    for (Employee e : employees) {
        result.computeIfAbsent(e.department(), k -> new IntSummaryStatistics())
              .accept(e.getAge());
    }
    return result;
}
```

### 2. Stream without Collectors
Not applicable — grouped stats need a collector.

### 3. Stream + Collectors
```java
public static Map<String, IntSummaryStatistics> exercise042(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.summarizingInt(Employee::getAge)
        ));
}
```

### 4. Downstream Collector Composition
Extract a single figure from the stats with collectingAndThen:
```java
public static Map<String, Integer> exercise042MaxAge(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.collectingAndThen(
                Collectors.summarizingInt(Employee::getAge),
                stats -> stats.getMax()
            )
        ));
}
```

### Edge cases
- One pass computes everything — the performance argument for summarizing
- stats.getMax() on an empty group returns Integer.MIN_VALUE (but groups are never empty here)
- The IntSummaryStatistics object is mutable — accept() adds values

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.groupingBy(Classifier, Collectors.summarizingInt(ToIntFunction))
```

### Recognition pattern
- "Statistics per group" — count AND sum AND min AND max AND average
- groupingBy + summarizingInt — one pass, full stats
- Same family: summarizingLong, summarizingDouble

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement ageStatsByDept(List<Employee>) using groupingBy + summarizingInt
4. Check if you got it right
