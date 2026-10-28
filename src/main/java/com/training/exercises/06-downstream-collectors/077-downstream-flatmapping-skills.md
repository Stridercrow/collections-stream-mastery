# Exercise 077 — Downstream flatMapping: skills list per department

## Objective
Second repetition of downstream `flatMapping()` — this time collecting to a List (duplicates kept).

## Ask
Given a list of employees, return a `Map<String, List<String>>` of departments to ALL skills (with duplicates) of the employees in that department.

## Input
List<Employee> employees = [Alice(Engineering, [Java, SQL]), Bob(Engineering, [Java, Kotlin]), Charlie(Sales, [Demo])]

## Expected Output
{Engineering=[Java, SQL, Java, Kotlin], Sales=[Demo]} — Java appears twice

## API Category
Downstream Collectors

## Hints

### Hint 1
Same mechanics as Exercise 062 — only the terminal collector changes.

### Hint 2
toList() (not toSet()) keeps every occurrence.

### Hint 3
The mapper still returns a Stream: e -> e.skills().stream().

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for: `flatMapping(Function<T, Stream<R>>, Collector downstream)` — Java 9+

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, List<String>> exercise077Imperative(List<Employee> employees) {
    Map<String, List<String>> result = new HashMap<>();
    for (Employee e : employees) {
        result.computeIfAbsent(e.department(), k -> new ArrayList<>())
              .addAll(e.skills());
    }
    return result;
}
```

### 2. Stream without Collectors
Not applicable — grouped flattening requires collectors.

### 3. Stream + Collectors
```java
public static Map<String, List<String>> exercise077(List<Employee> employees) {
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

### 4. Downstream Collector Composition
Deduplicated variant — one line difference from the main solution:
```java
public static Map<String, Set<String>> exercise077Unique(List<Employee> employees) {
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

### Edge cases
- toList keeps duplicates; toSet removes them — the ONLY difference between this and Exercise 062
- Empty skills lists contribute nothing
- Employee.skills() returns List<String> — record component accessor

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.groupingBy(Classifier, Collectors.flatMapping(e -> e.list().stream(), Collectors.toList()))
```

### Recognition pattern
- Repetition drill for flatMapping()
- flatMapping + toList = merge with duplicates; + toSet = merge deduplicated
- Choosing the terminal collector is the design decision; the skeleton never changes

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file (and Exercise 062)
2. Open a scratch file
3. Implement allSkillsByDept(List<Employee>) with duplicates, then deduplicated
4. Check if you got it right
