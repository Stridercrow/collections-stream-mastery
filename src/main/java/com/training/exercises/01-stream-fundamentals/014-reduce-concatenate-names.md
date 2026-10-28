# Exercise 014 — Reduce to Concatenate Names with Commas

## Objective
Train recognition of `Stream.reduce()` as a general fold operation, and when `joining()` is the better tool.

## Ask
Given a list of employees, concatenate all their names into a single string separated by ", " (e.g., "Alice, Bob, Charlie"). Return "" for an empty list.

## Input
List<Employee> employees = [Alice, Bob, Charlie]

## Expected Output
"Alice, Bob, Charlie"

## API Category
Stream / reduce

## Hints

### Hint 1
First map employees to their names — you're combining Strings.

### Hint 2
`Stream` has a method that repeatedly combines two elements into one using a binary operator.

### Hint 3
`reduce(BinaryOperator)` returns `Optional` because the stream might be empty. Note: for String joining specifically, `Collectors.joining()` is the idiomatic choice.

## What to investigate in the Java API
- Class: `java.util.stream.Stream`
- Look for:
  - `reduce(BinaryOperator<T>)` — returns `Optional<T>`
  - `reduce(T identity, BinaryOperator<T>)` — returns T directly
- Class: `java.util.stream.Collectors` — `joining(CharSequence delimiter)` as the idiomatic alternative

## Four Implementation Passes

### 1. Imperative Java
```java
public static String exercise014Imperative(List<Employee> employees) {
    StringBuilder sb = new StringBuilder();
    for (Employee e : employees) {
        if (sb.length() > 0) {
            sb.append(", ");
        }
        sb.append(e.name());
    }
    return sb.toString();
}
```

### 2. Stream without Collectors
```java
public static String exercise014(List<Employee> employees) {
    return employees.stream()
        .map(Employee::name)
        .reduce((a, b) -> a + ", " + b)
        .orElse("");
}
```

### 3. Stream + Collectors
```java
public static String exercise014Collect(List<Employee> employees) {
    return employees.stream()
        .map(Employee::name)
        .collect(Collectors.joining(", "));
}
```

### 4. Downstream Collector Composition
```java
public static Map<String, String> exercise014Downstream(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.mapping(Employee::name, Collectors.joining(", "))
        ));
}
```

### Edge cases
- Empty list -> reduce returns Optional.empty() -> orElse("") gives ""
- Single element -> that element alone, no delimiter
- reduce() with String concatenation creates intermediate strings — joining() is more efficient
- Parallel streams: use the 3-arg reduce or joining() to be safe

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Stream.map(...).reduce((a, b) -> combine)          // general fold
Stream.map(...).collect(Collectors.joining(", "))  // string-specific (idiomatic)
```

### Recognition pattern
- Combine all elements into ONE value using a rule
- reduce(BinaryOperator) -> Optional
- reduce(identity, BinaryOperator) -> direct value
- String concatenation specifically -> Collectors.joining()

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement concatNames(List<Employee>) both with reduce() and with joining()
4. Check if you got it right
