# Exercise 045 — partitioningBy: split by salary threshold

## Objective
Train recognition of `Collectors.partitioningBy()` — groupingBy's two-bucket Boolean sibling.

## Ask
Given a list of employees, split them into two groups: those earning more than 100000 and everyone else. Return `Map<Boolean, List<Employee>>`.

## Input
List<Employee> employees = [Alice(60000), Bob(120000), Charlie(90000), Diana(150000)]

## Expected Output
{false=[Alice(60000), Charlie(90000)], true=[Bob(120000), Diana(150000)]}

## API Category
Grouping and Partitioning

## Hints

### Hint 1
The split criterion is a yes/no question — a Predicate.

### Hint 2
`Collectors` has a method that partitions elements into exactly two lists keyed by true/false.

### Hint 3
Unlike groupingBy, this one ALWAYS has both keys, even if a bucket is empty.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for:
  - `partitioningBy(Predicate predicate)` — Map<Boolean, List<T>>
  - `partitioningBy(Predicate predicate, Collector downstream)` — custom value shape

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<Boolean, List<Employee>> exercise045Imperative(List<Employee> employees) {
    List<Employee> high = new ArrayList<>();
    List<Employee> low = new ArrayList<>();
    for (Employee e : employees) {
        if (e.salary().compareTo(BigDecimal.valueOf(100000)) > 0) {
            high.add(e);
        } else {
            low.add(e);
        }
    }
    Map<Boolean, List<Employee>> result = new HashMap<>();
    result.put(true, high);
    result.put(false, low);
    return result;
}
```

### 2. Stream without Collectors
Not applicable — two-bucket Map building is collector territory.

### 3. Stream + Collectors
```java
public static Map<Boolean, List<Employee>> exercise045(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.partitioningBy(
            e -> e.salary().compareTo(BigDecimal.valueOf(100000)) > 0
        ));
}
```

### 4. Downstream Collector Composition
Count per partition instead of listing:
```java
public static Map<Boolean, Long> exercise045Counts(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.partitioningBy(
            e -> e.salary().compareTo(BigDecimal.valueOf(100000)) > 0,
            Collectors.counting()
        ));
}
```

### Edge cases
- Empty input -> {false=[], true=[]} — both keys ALWAYS present (unlike groupingBy)
- Keys are exactly true and false — never more, never fewer
- partitioningBy(predicate) ≈ groupingBy(predicate::test) but stricter and faster

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.partitioningBy(Predicate)
```

### Recognition pattern
- "Split into two groups / eligible vs not / pass vs fail"
- Predicate as classifier -> partitioningBy
- Multi-category grouping -> groupingBy
- Need guaranteed true+false keys -> partitioningBy

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement splitBySalary(List<Employee>) using partitioningBy
4. Check if you got it right
