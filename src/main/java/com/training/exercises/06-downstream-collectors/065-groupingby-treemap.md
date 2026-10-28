# Exercise 065 — groupingBy with TreeMap: sorted keys

## Objective
Train recognition of the 3-argument groupingBy with a map factory.

## Ask
Given a list of employees, group them by department into a `TreeMap<String, List<Employee>>` so the map iterates with departments in alphabetical order.

## Input
List<Employee> employees = [Alice(Sales), Bob(Engineering), Charlie(HR)]

## Expected Output
{Engineering=[Bob], HR=[Charlie], Sales=[Alice]} — sorted key order

## API Category
Downstream Collectors

## Hints

### Hint 1
The 1-arg groupingBy always gives a HashMap.

### Hint 2
There's a 3-argument overload: classifier, map factory (Supplier), downstream collector.

### Hint 3
Pass TreeMap::new as the middle argument; keep toList() as the downstream.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for: `groupingBy(Function classifier, Supplier<M> mapFactory, Collector downstream)`
- Class: `java.util.TreeMap` — sorted keys

## Four Implementation Passes

### 1. Imperative Java
```java
public static TreeMap<String, List<Employee>> exercise065Imperative(List<Employee> employees) {
    TreeMap<String, List<Employee>> result = new TreeMap<>();
    for (Employee e : employees) {
        result.computeIfAbsent(e.department(), k -> new ArrayList<>()).add(e);
    }
    return result;
}
```

### 2. Stream without Collectors
Not applicable — map type selection is a collector concern.

### 3. Stream + Collectors
```java
public static TreeMap<String, List<Employee>> exercise065(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(Employee::department, TreeMap::new, Collectors.toList()));
}
```

### 4. Downstream Collector Composition
TreeMap + sorted lists per department (sorting the VALUES too):
```java
public static TreeMap<String, List<Employee>> exercise065SortedValues(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            TreeMap::new,
            Collectors.collectingAndThen(
                Collectors.toList(),
                list -> list.stream()
                    .sorted(Comparator.comparing(Employee::name))
                    .collect(Collectors.toList())
            )
        ));
}
```

### Edge cases
- Parameter order matters: (classifier, mapFactory, downstream) — the supplier goes in the MIDDLE
- TreeMap rejects null keys
- Want insertion order instead? LinkedHashMap::new

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.groupingBy(Classifier, TreeMap::new, downstream)
```

### Recognition pattern
- "Group with sorted keys" -> groupingBy + TreeMap::new
- The 3-arg shape: classifier, map supplier, downstream
- Mirrors 4-arg toMap(key, value, merge, supplier)

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement sortedGrouping(List<Employee>) with groupingBy + TreeMap::new
4. Check if you got it right
