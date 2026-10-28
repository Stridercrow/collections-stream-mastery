# Exercise 036 — Collectors.toMap with TreeMap supplier

## Objective
Train recognition of the 4-argument `toMap()` with a map supplier for sorted output.

## Ask
Given a list of employees, build a `TreeMap<String, Long>` counting how many employees work in each department, with departments in sorted key order.

## Input
List<Employee> employees = [Alice(Sales), Bob(Engineering), Charlie(Sales), Diana(HR)]

## Expected Output
{Engineering=1, HR=1, Sales=2} — TreeMap iterates keys alphabetically

## API Category
Basic Collectors

## Hints

### Hint 1
Key = department. Value starts at 1 for the first occurrence.

### Hint 2
The merge function combines counts — `Long::sum`.

### Hint 3
There is a 4-argument overload where the last parameter is a Supplier of the Map implementation.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for: `toMap(keyMapper, valueMapper, mergeFunction, Supplier<M> mapSupplier)`
- Class: `java.util.TreeMap` — keeps keys in sorted order

## Four Implementation Passes

### 1. Imperative Java
```java
public static TreeMap<String, Long> exercise036Imperative(List<Employee> employees) {
    TreeMap<String, Long> counts = new TreeMap<>();
    for (Employee e : employees) {
        counts.merge(e.department(), 1L, Long::sum);
    }
    return counts;
}
```

### 2. Stream without Collectors
Not applicable — building a typed Map requires a collector.

### 3. Stream + Collectors
```java
public static TreeMap<String, Long> exercise036(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.toMap(
            Employee::department,
            e -> 1L,
            Long::sum,
            TreeMap::new
        ));
}
```

### 4. Downstream Collector Composition
groupingBy has a matching map-supplier overload:
```java
public static TreeMap<String, Long> exercise036Downstream(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            TreeMap::new,
            Collectors.counting()
        ));
}
```

### Edge cases
- Empty list -> empty TreeMap
- TreeMap sorts keys naturally — pass `new TreeMap<>(Comparator)` for custom order
- Need insertion order instead? LinkedHashMap::new
- Remember the parameter ORDER: key, value, merge, supplier

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.toMap(keyMapper, valueMapper, mergeFunction, TreeMap::new)
```

### Recognition pattern
- "Map must be sorted / LinkedHashMap / specific type"
- 4-arg toMap adds the map supplier
- groupingBy has the same pattern: groupingBy(classifier, mapSupplier, downstream)

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement deptCountsSorted(List<Employee>) with 4-arg toMap and TreeMap::new
4. Check if you got it right
