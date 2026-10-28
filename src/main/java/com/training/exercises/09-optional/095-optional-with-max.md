# Exercise 095 — Optional from max: oldest employee

## Objective
Train the recognition that Stream terminal searches RETURN Optional — empty streams produce empty Optionals.

## Ask
Given a list of employees, find the oldest one. Return `Optional<Employee>` — and understand WHY the return type is Optional.

## Input
List<Employee> employees = [Alice(30), Bob(45), Charlie(28)] and an empty list

## Expected Output
- Optional[Bob(45)] for the populated list
- Optional.empty for the empty list

## API Category
Optional

## Hints

### Hint 1
You need the element with the maximum age — not a sorted list.

### Hint 2
`Stream.max(Comparator)` returns Optional<T>.

### Hint 3
Comparator.comparingInt(Employee::getAge) builds the comparator without boxing.

## What to investigate in the Java API
- Class: `java.util.stream.Stream`
- Look for: `max(Comparator)`, `min(Comparator)` — both return Optional<T>
- Class: `java.util.Comparator` — `comparingInt(ToIntFunction)`

## Four Implementation Passes

### 1. Imperative Java
```java
public static Optional<Employee> exercise095Imperative(List<Employee> employees) {
    Employee oldest = null;
    for (Employee e : employees) {
        if (oldest == null || e.getAge() > oldest.getAge()) {
            oldest = e;
        }
    }
    return Optional.ofNullable(oldest);
}
```

### 2. Stream without Collectors
```java
public static Optional<Employee> exercise095(List<Employee> employees) {
    return employees.stream()
        .max(Comparator.comparingInt(Employee::getAge));
}
```

### 3. Stream + Collectors
Collector form:
```java
public static Optional<Employee> exercise095Collect(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.maxBy(Comparator.comparingInt(Employee::getAge)));
}
```

### 4. Downstream Collector Composition
The collector form is what groupingBy uses — see Exercise 043:
```java
public static Map<String, Optional<Employee>> exercise095PerDept(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.maxBy(Comparator.comparingInt(Employee::getAge))
        ));
}
```

### Edge cases
- Empty list -> Optional.empty() — the reason max() returns Optional at all
- Ties -> arbitrary winner
- max() vs sorted().limit(1): max is O(n), sorting is O(n log n)

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Stream.max(Comparator.comparingInt(...))
```

### Recognition pattern
- "Find the most X element"
- Stream terminal ops that may find nothing return Optional: max, min, findFirst, findAny, reduce
- Optional in the signature is the API telling you: handle the empty case

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement oldestEmployee(List<Employee>) using max(comparingInt)
4. Check if you got it right
