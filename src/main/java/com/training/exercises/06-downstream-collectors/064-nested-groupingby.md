# Exercise 064 — Nested groupingBy: department then title

## Objective
Train recognition of hierarchical grouping via groupingBy inside groupingBy.

## Ask
Given a list of employees, return a `Map<String, Map<String, List<Employee>>>` grouping first by department, then by job title within each department.

## Input
List<Employee> employees = [Alice(Engineering, Dev), Bob(Engineering, Lead), Charlie(Sales, Rep), Diana(Engineering, Dev)]

## Expected Output
{Engineering={Dev=[Alice, Diana], Lead=[Bob]}, Sales={Rep=[Charlie]}}

## API Category
Downstream Collectors

## Hints

### Hint 1
The outer map's VALUE is itself another grouped map.

### Hint 2
Use groupingBy as the DOWNSTREAM of another groupingBy.

### Hint 3
The inner groupingBy's classifier is the second key (title); its default downstream is toList.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for: `groupingBy(Function classifier, Collector downstream)` where downstream is itself groupingBy
- The generic shape: Map<K1, Map<K2, List<T>>>

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, Map<String, List<Employee>>> exercise064Imperative(List<Employee> employees) {
    Map<String, Map<String, List<Employee>>> result = new HashMap<>();
    for (Employee e : employees) {
        result.computeIfAbsent(e.department(), k -> new HashMap<>())
              .computeIfAbsent(e.title(), t -> new ArrayList<>())
              .add(e);
    }
    return result;
}
```

### 2. Stream without Collectors
Not applicable — nested map building is collector territory.

### 3. Stream + Collectors
```java
public static Map<String, Map<String, List<Employee>>> exercise064(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.groupingBy(Employee::title)
        ));
}
```

### 4. Downstream Collector Composition
Deep nesting with a counting leaf — counts per department/title:
```java
public static Map<String, Map<String, Long>> exercise064Counts(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.groupingBy(Employee::title, Collectors.counting())
        ));
}
```

### Edge cases
- Nested groupingBy leaves are List by default — add a leaf collector (counting, mapping...) for other shapes
- Three levels work identically: Map<K1, Map<K2, Map<K3, List<T>>>>
- Deep nesting hurts readability — sometimes a composite key record is cleaner

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.groupingBy(k1, Collectors.groupingBy(k2))
```

### Recognition pattern
- "Group by A, then by B" — hierarchical drill-down
- groupingBy nested in groupingBy — the downstream IS another groupingBy
- Composite key alternative: groupingBy(e -> e.a() + "|" + e.b())

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement deptThenTitle(List<Employee>) with nested groupingBy
4. Check if you got it right
