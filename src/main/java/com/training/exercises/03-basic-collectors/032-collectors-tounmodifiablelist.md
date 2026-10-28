# Exercise 032 — Collectors.toUnmodifiableList

## Objective
Train recognition of `Collectors.toUnmodifiableList()` for immutable collection results.

## Ask
Given a list of employees, return an unmodifiable list of them sorted by name. Any attempt to modify the result must throw `UnsupportedOperationException`.

## Input
List<Employee> employees = [Charlie, Alice, Bob]

## Expected Output
An unmodifiable list: [Alice, Bob, Charlie]

## API Category
Basic Collectors

## Hints

### Hint 1
Sort by name first — `Comparator.comparing(Employee::name)`.

### Hint 2
`Collectors` has a variant of `toList()` whose result cannot be modified.

### Hint 3
The method name says it all: toUnmodifiableList.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for:
  - `toUnmodifiableList()`
  - Related: `toUnmodifiableSet()`, `toUnmodifiableMap(...)` 
- Compare with: `Stream.toList()` (also unmodifiable, Java 16+)

## Four Implementation Passes

### 1. Imperative Java
```java
public static List<Employee> exercise032Imperative(List<Employee> employees) {
    List<Employee> copy = new ArrayList<>(employees);
    copy.sort(Comparator.comparing(Employee::name));
    return Collections.unmodifiableList(copy);
}
```

### 2. Stream without Collectors
```java
public static List<Employee> exercise032ToList(List<Employee> employees) {
    return employees.stream()
        .sorted(Comparator.comparing(Employee::name))
        .toList(); // also unmodifiable, Java 16+
}
```

### 3. Stream + Collectors
```java
public static List<Employee> exercise032(List<Employee> employees) {
    return employees.stream()
        .sorted(Comparator.comparing(Employee::name))
        .collect(Collectors.toUnmodifiableList());
}
```

### 4. Downstream Collector Composition
```java
public static Map<String, List<Employee>> exercise032Downstream(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.collectingAndThen(
                Collectors.toList(),
                Collections::unmodifiableList
            )
        ));
}
```

### Edge cases
- Result throws UnsupportedOperationException on add/remove/set
- Null elements are FORBIDDEN — toUnmodifiableList() throws NPE on nulls
- Stream.toList() also forbids nulls and is unmodifiable; Collectors.toList() is modifiable and allows nulls

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Stream.collect(Collectors.toUnmodifiableList())
```

### Recognition pattern
- "Immutable / unmodifiable / defensive copy" requirement
- toUnmodifiableList / toUnmodifiableSet / toUnmodifiableMap family
- Downstream in groupingBy: use collectingAndThen(toList(), Collections::unmodifiableList)

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement immutableSortedList(List<Employee>) using toUnmodifiableList()
4. Check if you got it right
