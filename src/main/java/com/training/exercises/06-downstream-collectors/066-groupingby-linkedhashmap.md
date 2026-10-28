# Exercise 066 — groupingBy with LinkedHashMap: insertion-ordered groups

## Objective
Train recognition of LinkedHashMap as the "keep encounter order" map factory.

## Ask
Given a list of employees, group them by department into a `LinkedHashMap<String, List<Employee>>` so departments appear in the order they were first encountered.

## Input
List<Employee> employees = [Alice(Sales), Bob(Engineering), Charlie(Sales), Diana(HR)]

## Expected Output
{Sales=[Alice, Charlie], Engineering=[Bob], HR=[Diana]} — first-seen order

## API Category
Downstream Collectors

## Hints

### Hint 1
Same 3-argument groupingBy shape as the TreeMap exercise — only the supplier changes.

### Hint 2
`LinkedHashMap` preserves insertion order of keys.

### Hint 3
Pass LinkedHashMap::new as the map factory.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for: `groupingBy(Function classifier, Supplier<M> mapFactory, Collector downstream)`
- Class: `java.util.LinkedHashMap` — insertion-ordered

## Four Implementation Passes

### 1. Imperative Java
```java
public static LinkedHashMap<String, List<Employee>> exercise066Imperative(List<Employee> employees) {
    LinkedHashMap<String, List<Employee>> result = new LinkedHashMap<>();
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
public static LinkedHashMap<String, List<Employee>> exercise066(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(Employee::department, LinkedHashMap::new, Collectors.toList()));
}
```

### 4. Downstream Collector Composition
Insertion order PLUS a transformed value:
```java
public static LinkedHashMap<String, Long> exercise066Counts(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            LinkedHashMap::new,
            Collectors.counting()
        ));
}
```

### Edge cases
- HashMap: no order. LinkedHashMap: insertion order. TreeMap: sorted order — pick deliberately
- Parallel streams + groupingBy: encounter order is NOT guaranteed even with LinkedHashMap
- "In the order they appear" requirements -> LinkedHashMap

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.groupingBy(Classifier, LinkedHashMap::new, downstream)
```

### Recognition pattern
- "Preserve first-seen order of groups"
- LinkedHashMap::new as map factory
- Order-selection matrix: HashMap (fast, unordered), LinkedHashMap (insertion), TreeMap (sorted)

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement orderedGrouping(List<Employee>) with groupingBy + LinkedHashMap::new
4. Check if you got it right
