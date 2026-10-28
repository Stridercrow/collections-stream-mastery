# Exercise 041 — groupingBy with averagingInt: average age by department

## Objective
Train recognition of `Collectors.averagingInt()` and that it returns Double (no Optional).

## Ask
Given a list of employees, return a `Map<String, Double>` with the average age per department.

## Input
List<Employee> employees (age via getAge())

## Expected Output
{Engineering=..., Sales=...} — average age per department

## API Category
Grouping and Partitioning

## Hints

### Hint 1
Group by department; per bucket compute a mean.

### Hint 2
`Collectors` has averagingInt, averagingLong, averagingDouble downstream collectors.

### Hint 3
Unlike IntStream.average(), the collector form returns plain Double — no Optional to unwrap.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for:
  - `groupingBy(Function classifier, Collector downstream)`
  - `averagingInt(ToIntFunction)` — returns Double

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, Double> exercise041Imperative(List<Employee> employees) {
    Map<String, List<Integer>> ages = new HashMap<>();
    for (Employee e : employees) {
        ages.computeIfAbsent(e.department(), k -> new ArrayList<>()).add(e.getAge());
    }
    Map<String, Double> result = new HashMap<>();
    for (Map.Entry<String, List<Integer>> entry : ages.entrySet()) {
        List<Integer> list = entry.getValue();
        int sum = 0;
        for (int age : list) sum += age;
        result.put(entry.getKey(), (double) sum / list.size());
    }
    return result;
}
```

### 2. Stream without Collectors
Not applicable — grouped averaging needs a collector.

### 3. Stream + Collectors
```java
public static Map<String, Double> exercise041(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.averagingInt(Employee::getAge)
        ));
}
```

### 4. Downstream Collector Composition
Downstream average plus post-processing via collectingAndThen:
```java
public static Map<String, Double> exercise041Rounded(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.collectingAndThen(
                Collectors.averagingInt(Employee::getAge),
                avg -> Math.round(avg * 100.0) / 100.0
            )
        ));
}
```

### Edge cases
- averagingInt computes sum/count in one pass — efficient
- Integer overflow: averagingInt uses long internally for the sum
- Empty overall list -> empty map (no groups at all)

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.groupingBy(Classifier, Collectors.averagingInt(ToIntFunction))
```

### Recognition pattern
- "Average per group"
- groupingBy + averagingInt/Long/Double
- Value type is Double — no Optional (that's only for IntStream.average())

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement avgAgeByDept(List<Employee>) using groupingBy + averagingInt
4. Check if you got it right
