# Exercise 075 — Downstream mapping: department to employee IDs

## Objective
Drill the mapping downstream with a List result and a Long extractor.

## Ask
Given a list of employees, return a `Map<String, List<Long>>` mapping each department to the list of its employees' IDs.

## Input
List<Employee> employees = [Alice(id=1, Engineering), Bob(id=2, Sales), Charlie(id=3, Engineering)]

## Expected Output
{Engineering=[1, 3], Sales=[2]}

## API Category
Downstream Collectors

## Hints

### Hint 1
Same skeleton as Exercise 060 — only the extractor and the terminal collector change.

### Hint 2
mapping() extracts the id; toList() keeps them in encounter order.

### Hint 3
Method reference: Employee::id works directly on the record component.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for: `mapping(Function mapper, Collector downstream)`

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, List<Long>> exercise075Imperative(List<Employee> employees) {
    Map<String, List<Long>> result = new HashMap<>();
    for (Employee e : employees) {
        result.computeIfAbsent(e.department(), k -> new ArrayList<>()).add(e.id());
    }
    return result;
}
```

### 2. Stream without Collectors
Not applicable — grouped shape requires collectors.

### 3. Stream + Collectors
```java
public static Map<String, List<Long>> exercise075(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.mapping(Employee::id, Collectors.toList())
        ));
}
```

### 4. Downstream Collector Composition
Sorted IDs per department via collectingAndThen:
```java
public static Map<String, List<Long>> exercise075Sorted(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.collectingAndThen(
                Collectors.mapping(Employee::id, Collectors.toList()),
                ids -> ids.stream().sorted().toList()
            )
        ));
}
```

### Edge cases
- toList() downstream keeps encounter order (HashMap groups: key order unspecified, but WITHIN a list the order follows the source stream)
- List<Long> vs List<long>: generics need the boxed type — no choice here
- Repeated drill: mapping + {toList, toSet, joining} covers 90% of downstream reshaping

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.groupingBy(Classifier, Collectors.mapping(Extractor, Collectors.toList()))
```

### Recognition pattern
- "IDs per group" — classic lookup-table construction
- mapping(extractor, toList())
- Third repetition of the same skeleton — that's deliberate

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement idsByDept(List<Employee>) with mapping + toList
4. Check if you got it right
