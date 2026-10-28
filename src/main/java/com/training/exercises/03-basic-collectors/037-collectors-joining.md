# Exercise 037 — Collectors.joining: comma-separated names

## Objective
Train recognition of `Collectors.joining()` for string concatenation of stream elements.

## Ask
Given a list of employees, return all their names joined into a single string separated by ", ".

## Input
List<Employee> employees = [Alice, Bob, Charlie]

## Expected Output
"Alice, Bob, Charlie"

## API Category
Basic Collectors

## Hints

### Hint 1
First transform each employee into their name — joining works on the stream's Strings.

### Hint 2
`Collectors` has a method built exactly for concatenating a stream of CharSequences.

### Hint 3
Overloads: `joining()`, `joining(delimiter)`, `joining(delimiter, prefix, suffix)`.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors`
- Look for:
  - `joining()` — no separator
  - `joining(CharSequence delimiter)`
  - `joining(CharSequence delimiter, CharSequence prefix, CharSequence suffix)`

## Four Implementation Passes

### 1. Imperative Java
```java
public static String exercise037Imperative(List<Employee> employees) {
    StringBuilder sb = new StringBuilder();
    for (Employee e : employees) {
        if (sb.length() > 0) sb.append(", ");
        sb.append(e.name());
    }
    return sb.toString();
}
```

### 2. Stream without Collectors
```java
public static String exercise037Reduce(List<Employee> employees) {
    return employees.stream()
        .map(Employee::name)
        .reduce((a, b) -> a + ", " + b)
        .orElse("");
}
```

### 3. Stream + Collectors
```java
public static String exercise037(List<Employee> employees) {
    return employees.stream()
        .map(Employee::name)
        .collect(Collectors.joining(", "));
}
```

### 4. Downstream Collector Composition
joining() as a downstream — names per department:
```java
public static Map<String, String> exercise037Downstream(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.mapping(Employee::name, Collectors.joining(", "))
        ));
}
```

### Edge cases
- Empty list -> "" (empty string, not null)
- Single element -> no delimiter added
- joining(", ", "[", "]") produces "[Alice, Bob, Charlie]" — handy for debugging output
- More efficient than reduce with string concatenation (uses StringBuilder internally, handles parallel well)

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Stream.map(...).collect(Collectors.joining(", "))
```

### Recognition pattern
- "Join / concatenate / CSV-style output"
- joining(delimiter) — delimiter between elements only
- joining(delimiter, prefix, suffix) — wraps the whole thing
- Inside groupingBy: mapping(extractor, joining(...))

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement joinedNames(List<Employee>) using joining(", ")
4. Check if you got it right
