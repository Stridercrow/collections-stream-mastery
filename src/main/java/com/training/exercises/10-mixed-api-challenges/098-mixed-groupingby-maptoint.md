# Exercise 098 — Mixed: groupingBy with summingInt for total ages

## Objective
Recognition drill: total ages per department. Identical to Exercise 040 — notice how fast you recall the API chain.

## Ask
Given a list of employees, return a `Map<String, Integer>` with the sum of ages per department.

## Input
List<Employee> employees (age via getAge())

## Expected Output
{Engineering=..., Sales=...} — total age per department

## API Category
Mixed API Challenges

## Hints

### Hint 1
(Try with zero hints first — this is a recall drill.)

### Hint 2
Classifier: department. Downstream: numeric sum of an int property.

### Hint 3
groupingBy(department, summingInt(getAge)).

## What to investigate in the Java API
- Class: `java.util.stream.Collectors` — `groupingBy`, `summingInt`

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, Integer> exercise098Imperative(List<Employee> employees) {
    Map<String, Integer> totals = new HashMap<>();
    for (Employee e : employees) {
        totals.merge(e.department(), e.getAge(), Integer::sum);
    }
    return totals;
}
```

### 2. Stream without Collectors
Not applicable — grouped aggregation needs a collector.

### 3. Stream + Collectors
```java
public static Map<String, Integer> exercise098(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.summingInt(Employee::getAge)
        ));
}
```

### 4. Downstream Collector Composition
Same result via toMap — compare readability:
```java
public static Map<String, Integer> exercise098ToMap(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.toMap(
            Employee::department,
            Employee::getAge,
            Integer::sum
        ));
}
```

### Edge cases
- Empty list -> empty map
- Value type Integer (boxed sum)

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.groupingBy(Classifier, Collectors.summingInt(ToIntFunction))
```

### Recognition pattern
- Repetition of Exercise 040 — the chain should surface in under 5 seconds now
- groupingBy + summingInt: "total of numeric property per group"

## Repetition Challenge
THIS exercise IS the repetition challenge:
1. Close the file (and Exercise 040)
2. Open a scratch file
3. Implement totalAgesByDept(List<Employee>) from memory
4. Verify against exercise098 in MasterySolutions.java
