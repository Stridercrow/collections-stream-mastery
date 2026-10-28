# Exercise 062 — Downstream flatMapping: unique skills per department

## Objective
Train recognition of `Collectors.flatMapping()` for flattening nested collections inside buckets.

## Ask
Given a list of employees, return a `Map<String, Set<String>>` of departments to the UNIQUE skills of all employees in that department.

## Input
List<Employee> employees = [Alice(Engineering, [Java, SQL]), Bob(Engineering, [Java, Kotlin]), Charlie(Sales, [Negotiation])]

## Expected Output
{Engineering=[Java, SQL, Kotlin], Sales=[Negotiation]}

## API Category
Downstream Collectors

## Hints

### Hint 1
Each element carries a LIST of skills — grouping by department means each bucket holds many lists.

### Hint 2
`Collectors.flatMapping(Function<T, Stream<R>>, Collector)` flattens each bucket's elements into one stream.

### Hint 3
The function returns a Stream (e -> e.skills().stream()); the inner collector gathers them into a Set.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for: `flatMapping(Function mapper, Collector downstream)` — Java 9+
- The mapper returns a Stream per element; the downstream collects the flattened elements

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, Set<String>> exercise062Imperative(List<Employee> employees) {
    Map<String, Set<String>> result = new HashMap<>();
    for (Employee e : employees) {
        result.computeIfAbsent(e.department(), k -> new HashSet<>())
              .addAll(e.skills());
    }
    return result;
}
```

### 2. Stream without Collectors
Not applicable — the grouped shape requires collectors.

### 3. Stream + Collectors
```java
public static Map<String, Set<String>> exercise062(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.flatMapping(
                e -> e.skills().stream(),
                Collectors.toSet()
            )
        ));
}
```

### 4. Downstream Collector Composition
Same pattern collecting to a List (duplicates kept):
```java
public static Map<String, List<String>> exercise062List(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.flatMapping(
                e -> e.skills().stream(),
                Collectors.toList()
            )
        ));
}
```

### Edge cases
- flatMapping is the downstream twin of stream.flatMap() — same relationship as filtering/filter
- Empty skills list contributes nothing to its bucket
- Needs Java 9+ (like mapping-overloads with filtering and flatMapping)

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.groupingBy(Classifier, Collectors.flatMapping(e -> e.list().stream(), terminal))
```

### Recognition pattern
- "Group by X, where each element has a collection you must merge"
- flatMapping(extractor-to-stream, toSet/toList)
- Nested lists inside grouped elements -> flatMapping

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement skillsByDept(List<Employee>) using groupingBy + flatMapping + toSet
4. Check if you got it right
