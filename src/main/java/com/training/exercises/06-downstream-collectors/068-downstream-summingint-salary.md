# Exercise 068 — Downstream summingInt: salary totals by department

## Objective
Reinforce `Collectors.summingInt()` with a property requiring conversion (BigDecimal salary -> int).

## Ask
Given a list of employees, return a `Map<String, Integer>` with the total salary (as int) per department.

## Input
List<Employee> employees = [Alice(Engineering, 60000.50), Bob(Engineering, 70000.50), Charlie(Sales, 50000)]

## Expected Output
{Engineering=130000, Sales=50000} — decimal parts truncated by intValue()

## API Category
Downstream Collectors

## Hints

### Hint 1
summingInt needs a ToIntFunction — a function producing an int.

### Hint 2
Salary is a BigDecimal: `e.salary().intValue()` extracts the int part.

### Hint 3
The lambda or method chain goes directly into summingInt(...).

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for: `summingInt(ToIntFunction<T>)`
- Class: `java.math.BigDecimal` — `intValue()`

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, Integer> exercise068Imperative(List<Employee> employees) {
    Map<String, Integer> totals = new HashMap<>();
    for (Employee e : employees) {
        totals.merge(e.department(), e.salary().intValue(), Integer::sum);
    }
    return totals;
}
```

### 2. Stream without Collectors
Not applicable — grouped numeric aggregation needs a collector.

### 3. Stream + Collectors
```java
public static Map<String, Integer> exercise068(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.summingInt(e -> e.salary().intValue())
        ));
}
```

### 4. Downstream Collector Composition
Preserve full precision with reducing + BigDecimal instead:
```java
public static Map<String, BigDecimal> exercise068Precise(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.reducing(BigDecimal.ZERO, Employee::salary, BigDecimal::add)
        ));
}
```

### Edge cases
- intValue() TRUNCATES decimals — for money, prefer the BigDecimal reducing variant (see pass 4)
- Large int sums can overflow — summingLong if totals may exceed Integer.MAX_VALUE
- The ToIntFunction can be any lambda, not just a method reference

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.groupingBy(Classifier, Collectors.summingInt(toIntFunction))
```

### Recognition pattern
- "Sum of a derived numeric value per group"
- summingInt(e -> e.property().intValue()) — conversion inside the extractor
- Money math -> consider BigDecimal + reducing instead

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement salaryTotalsByDept(List<Employee>) with summingInt
4. Check if you got it right
