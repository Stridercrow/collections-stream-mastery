# Exercise 006 — FlatMap to Get All Unique Skills

## Objective
Train recognition of `Stream.flatMap()` for flattening nested collections into a single stream.

## Ask
Given a list of employees, return a list of all unique skills across all employees.

## Input
List<Employee> employees = [Alice([Java, SQL]), Bob([Java, Kotlin]), Charlie([SQL])]

## Expected Output
[Java, SQL, Kotlin]

## API Category
Stream / flatMap

## Hints

### Hint 1
Each employee has a `List<String> skills` — you need to merge all lists into one.

### Hint 2
`Stream` has a method that takes a function returning a `Stream` for each element and flattens the result.

### Hint 3
Combine the flattening method with `distinct()` to remove duplicates.

## What to investigate in the Java API
- Class: `java.util.stream.Stream`
- Look for a method that:
  - Takes a `Function<T, Stream<R>>`
  - Returns a `Stream<R>` with all nested elements flattened
- Also look at `Stream.distinct()` for removing duplicates

## Four Implementation Passes

### 1. Imperative Java
```java
public static List<String> exercise006Imperative(List<Employee> employees) {
    Set<String> unique = new LinkedHashSet<>();
    for (Employee e : employees) {
        unique.addAll(e.skills());
    }
    return new ArrayList<>(unique);
}
```

### 2. Stream without Collectors
```java
public static List<String> exercise006(List<Employee> employees) {
    return employees.stream()
        .flatMap(e -> e.skills().stream())
        .distinct()
        .toList();
}
```

### 3. Stream + Collectors
```java
public static List<String> exercise006Collect(List<Employee> employees) {
    return employees.stream()
        .flatMap(e -> e.skills().stream())
        .collect(Collectors.toSet())
        .stream()
        .toList();
}
```

### 4. Downstream Collector Composition
```java
public static Set<String> exercise006Downstream(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.flatMapping(
            e -> e.skills().stream(),
            Collectors.toSet()
        ));
}
```

### Edge cases
- Empty list -> empty list
- Employee with empty skills -> contributes nothing
- All employees with same skills -> result has one of each

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Stream.flatMap(Function<T, Stream<R>>).distinct().toList()
```

### Recognition pattern
- Nested collections (List inside List)
- Need to flatten and optionally deduplicate
- Use flatMap() to merge streams of streams

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement uniqueSkills(List<Employee>) using flatMap().distinct()
4. Check if you got it right
