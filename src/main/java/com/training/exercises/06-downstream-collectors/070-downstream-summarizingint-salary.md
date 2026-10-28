# Exercise 070 — Downstream summarizingInt: salary stats by department

## Objective
Reinforce `Collectors.summarizingInt()` for all-stats-per-group in one pass.

## Ask
Given a list of employees, return a `Map<String, IntSummaryStatistics>` with count, sum, min, average, and max of salaries (as int) per department.

## Input
List<Employee> employees = [Alice(Engineering, 60000), Bob(Engineering, 80000), Charlie(Sales, 50000)]

## Expected Output
{Engineering=IntSummaryStatistics{count=2, sum=140000, min=60000, average=70000.000000, max=80000}, Sales=IntSummaryStatistics{count=1, sum=50000, min=50000, average=50000.000000, max=50000}}

## API Category
Downstream Collectors

## Hints

### Hint 1
Five statistics, one traversal — the summarizing family exists for exactly this.

### Hint 2
summarizingInt takes a ToIntFunction; convert the BigDecimal salary inline.

### Hint 3
Each bucket value is an IntSummaryStatistics you can query with getters.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for: `summarizingInt(ToIntFunction<T>)`
- Class: `java.util.IntSummaryStatistics` — getCount(), getSum(), getMin(), getAverage(), getMax()

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, IntSummaryStatistics> exercise070Imperative(List<Employee> employees) {
    Map<String, IntSummaryStatistics> result = new HashMap<>();
    for (Employee e : employees) {
        result.computeIfAbsent(e.department(), k -> new IntSummaryStatistics())
              .accept(e.salary().intValue());
    }
    return result;
}
```

### 2. Stream without Collectors
Not applicable — grouped stats need a collector.

### 3. Stream + Collectors
```java
public static Map<String, IntSummaryStatistics> exercise070(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.summarizingInt(e -> e.salary().intValue())
        ));
}
```

### 4. Downstream Collector Composition
Report shape: map to a compact string per department:
```java
public static Map<String, String> exercise070Report(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.collectingAndThen(
                Collectors.summarizingInt(e -> e.salary().intValue()),
                stats -> String.format("n=%d avg=%.0f", stats.getCount(), stats.getAverage())
            )
        ));
}
```

### Edge cases
- One pass, five statistics — the efficiency argument
- stats objects are mutable; accept() adds more values if needed
- Need decimals preserved? summarizingDouble with doubleValue()

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.groupingBy(Classifier, Collectors.summarizingInt(toIntFunction))
```

### Recognition pattern
- "Full statistics per group"
- summarizingInt/Long/Double downstream
- Combine with collectingAndThen to reshape the report

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement salaryStatsByDept(List<Employee>) with summarizingInt
4. Check if you got it right
