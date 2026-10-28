# Exercise 099 — Mixed: computeIfAbsent with stream processing

## Objective
Bridge exercise: insert into a multi-map with computeIfAbsent, where the ELEMENT itself carries the key — the manual twin of groupingBy.

## Ask
Given a map of department -> employees and a single employee, add the employee to their department's list, creating the list if needed. Return the map.

## Input
Map<String, List<Employee>> map = {Engineering=[Alice]}
Employee employee = Bob(Engineering)

## Expected Output
{Engineering=[Alice, Bob]}

## API Category
Mixed API Challenges

## Hints

### Hint 1
The key comes FROM the employee: employee.department().

### Hint 2
`Map.computeIfAbsent` creates and returns the list in one call.

### Hint 3
Chain .add(employee) directly onto the computeIfAbsent result.

## What to investigate in the Java API
- Interface: `java.util.Map` — `computeIfAbsent(K, Function<K, V>)`
- This method is the imperative heart of what groupingBy automates

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, List<Employee>> exercise099Imperative(Map<String, List<Employee>> map, Employee employee) {
    if (!map.containsKey(employee.department())) {
        map.put(employee.department(), new ArrayList<>());
    }
    map.get(employee.department()).add(employee);
    return map;
}
```

### 2. Stream without Collectors
Not applicable — incremental Map mutation.

### 3. Stream + Collectors
For a WHOLE list at once, the stream version replaces the loop:
```java
public static Map<String, List<Employee>> exercise099Bulk(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(Employee::department));
}
```

### 4. Idiomatic Map API
```java
public static Map<String, List<Employee>> exercise099(Map<String, List<Employee>> map, Employee employee) {
    map.computeIfAbsent(employee.department(), k -> new ArrayList<>()).add(employee);
    return map;
}
```

### Edge cases
- Incremental ingestion (events, streaming data) -> computeIfAbsent per element
- Batch transformation of a complete collection -> groupingBy
- Knowing which of the two to reach for is the real skill this exercise tests

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Map.computeIfAbsent(key, k -> new ArrayList<>()).add(element)
// whole-collection equivalent:
Collectors.groupingBy(Classifier)
```

### Recognition pattern
- "Add to a Map of lists"
- computeIfAbsent — imperative one-at-a-time
- groupingBy — declarative all-at-once
- Two halves of the same concept — recognize both directions

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement addEmployee(Map, Employee) with computeIfAbsent, then bulk with groupingBy
4. Check if you got it right
