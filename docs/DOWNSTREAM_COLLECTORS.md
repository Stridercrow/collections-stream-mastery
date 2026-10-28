
### docs/DOWNSTREAM_COLLECTORS.md
```markdown
# Downstream Collectors Guide

## What Are Downstream Collectors?

In `Collectors.groupingBy(classifier, downstream)`, the **downstream collector** processes elements within each group.

Think of it as:
1. First, divide elements into groups (classifier)
2. Then, for each group, collect the elements using the downstream collector

## All Downstream Collectors

### 1. counting()
Counts elements in each group.
```java
Map<String, Long> countByDept = employees.stream()
    .collect(groupingBy(Employee::department, counting()));
```
### 2. summingInt(), summingLong(), summingDouble()
Sums numeric values in each group
```java
Map<String, Integer> totalSalary = employees.stream()
    .collect(groupingBy(Employee::department, summingInt(e- > e.salary().intValue())))
```    

### 3. averagingInt(), averagingLong(), averagingDouble()
Averages numeric values in each group
```java
Map<String, Double> avgSalary = employees.stream()
    .collect(groupingBy(Employee::department, averagingDouble(e -> e.salary().doubleValue())));
```

### 4. summarizingInt(), summarizingLong(), summarizingDouble()
Gets full statistics (count, sum, min, avg, max) for each group
```java
Map<String, IntSummaryStatistics> salaryStats = employee.stream()
    .collect(groupingBy(Employee::department, summarizingInt(e -> e.salary().intValue())))
```

### 5. minBy(), maxBy()
Finds min or max element by comparator in each group
```java
Map<String, Optional<Employee>> highestPaid = employees.stream()
    .collect(groupingBy(Employee::department, maxBy(Comparator.comparing(Employee::salary))));
```

### 6. mapping()
Applies a function to elements before collecting them in each group
```java
Map<String, List<String>> namesByDept = employees.stream()
    .collect(groupingBy(Employee::department, mapping(Employee::name, toList())))
```

### 7. filtering()
Filters elements within each group before collecting them
```java
Map<String, List<Employee>> highestPaidByDept = employees.stream()
    .collect(groupingBy(Employee::department,
        filtering(e -> e.salary() > 100000, toList())));
```

### 8 flatMapping()
Flattens nested colection within each group
```java
Map<String, Set<String>> skillsByDept = employees.stream()
    .collect(groupingBy(Employee::department,
        flatMapping(e -> e.skills.stream(), toSet())));
```

### 9 collectingAndThen()
Transforms the result of the downstream collector.
```java
Map<String, List<Employee>> immutable = employees.stream()
    .collects(groupingBy(Employee::department,
        collectingAndThen(toList(), Collections::unmodifiableList())))
```

### 10 reducing()
Performs reduction operation within each group
```java
Map<String, BiDecimal> totalSalary = employees.stream()
    .collect(groupingBy(Employee::department,
        reducing(BigDecimal.ZERO, Employee::salary, BigDecimal::add)))
```

### 11 joining()
Concatenates strings within each group
```java
Map<String, String> namesByDept = employees.stream()
    .collect(groupingBy(Eployee::department,
        mapping(Employee::name, joining(", "))))
```

### Grouping with Downstream composition
Downstream collectors can be nested:
```java
// Nested grouping: Department → Title → List of Employees
Map<String, Map<String, List<Employee>>> result = employees.stream()
    .collect(groupingBy(Employee::department,
         groupingBy(Employee::title)));

// Department → Title → Count
Map<String, Map<String, Long>> result = employees.stream()
    .collect(groupingBy(Employee::department,
         groupingBy(Employee::title, counting())));
```

### Downstream Collector Decision Tree
Need to collect within groups?
    ├── Need simple collection? → toList(), toSet(), toCollection()
    ├── Need numeric aggregate?
    │   ├── Count → counting()
    │   ├── Sum → summingXxx()
    │   ├── Average → averagingXxx()
    │   └── Stats → summarizingXxx()
    ├── Need element selection? → minBy(), maxBy()
    ├── Need transformation?
    │   ├── Map → mapping()
    │   ├── Filter → filtering()
    │   ├── Flatten → flatMapping()
    │   └── Custom → reducing()
    ├── Need post-processing? → collectingAndThen()
    └── Need string? → joining()

### Important distinction
Upstream filter vs Downstream filter

Upstream filter (before grouping)
```java
// Groups that don't meet the filter are removed entirely
employees.stream()
    .filter(e -> e.salary() > 100000)
    .collect(groupingBy(Employee::department));
```

Downstream filter (inside grouping)
```java
// All groups appear, even if they have no matching employees
employees.stream()
    .collect(groupingBy(Employee::department,
        filtering(e -> e.salary() > 100000, toList())));
```

### Common recognition patterns

Requirement	Collector Pattern
"Count per group"	groupingBy(..., counting())
"Sum per group"	groupingBy(..., summingInt(...))
"Average per group"	groupingBy(..., averagingInt(...))
"Stats per group"	groupingBy(..., summarizingInt(...))
"Max per group"	groupingBy(..., maxBy(...))
"Transform then collect"	groupingBy(..., mapping(...))
"Filter then collect"	groupingBy(..., filtering(...))
"Flatten then collect"	groupingBy(..., flatMapping(...))
"Unmodifiable per group"	groupingBy(..., collectingAndThen(...))