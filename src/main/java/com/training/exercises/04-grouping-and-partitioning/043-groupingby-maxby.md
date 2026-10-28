# Exercise 043 — groupingBy with maxBy: highest paid per department

## Objective
Train recognition of `Collectors.maxBy()` as a downstream collector.

## Ask
Given a list of employees, return a `Map<String, Optional<Employee>>` with the highest paid employee of each department.

## Input
List<Employee> employees = [Alice(Engineering, 60000), Bob(Engineering, 90000), Charlie(Sales, 70000)]

## Expected Output
{Engineering=Optional[Bob(90000)], Sales=Optional[Charlie(70000)]}

## API Category
Grouping and Partitioning

## Hints

### Hint 1
Per bucket, you need the maximum element — not a numeric value, the ELEMENT itself.

### Hint 2
`Collectors` has maxBy/minBy downstream collectors that take a Comparator.

### Hint 3
The value type wraps in Optional — even though a group is never empty, the API keeps the shape of Stream.max().

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for:
  - `groupingBy(Function classifier, Collector downstream)`
  - `maxBy(Comparator)` — downstream returning Optional<T>
  - Related: `minBy(Comparator)`

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, Optional<Employee>> exercise043Imperative(List<Employee> employees) {
    Map<String, Employee> best = new HashMap<>();
    for (Employee e : employees) {
        best.merge(e.department(), e,
            (current, candidate) ->
                candidate.salary().compareTo(current.salary()) > 0 ? candidate : current);
    }
    Map<String, Optional<Employee>> result = new HashMap<>();
    best.forEach((k, v) -> result.put(k, Optional.of(v)));
    return result;
}
```

### 2. Stream without Collectors
Not applicable — grouped max needs a collector.

### 3. Stream + Collectors
```java
public static Map<String, Optional<Employee>> exercise043(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.maxBy(Comparator.comparing(Employee::salary))
        ));
}
```

### 4. Downstream Collector Composition
Unwrap the Optional with collectingAndThen for a cleaner Map<String, Employee>:
```java
public static Map<String, Employee> exercise043Unwrapped(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.collectingAndThen(
                Collectors.maxBy(Comparator.comparing(Employee::salary)),
                opt -> opt.orElseThrow()
            )
        ));
}
```

### Edge cases
- Values are Optional even though groups are non-empty — unwrap downstream when the type annoys you
- Ties: any one of the tied elements wins
- maxBy takes a Comparator — build it with Comparator.comparing(KeyExtractor)

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.groupingBy(Classifier, Collectors.maxBy(Comparator))
```

### Recognition pattern
- "Best/worst element per group"
- groupingBy + maxBy/minBy(Comparator)
- Optional in the value type is the giveaway signature
- collectingAndThen(..., Optional::orElseThrow) to flatten

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement topPaidByDept(List<Employee>) using groupingBy + maxBy
4. Check if you got it right
