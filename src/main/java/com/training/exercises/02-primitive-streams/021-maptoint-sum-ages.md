# Exercise 021 — mapToInt: Sum of Employee Ages

## Objective
Train recognition of `Stream.mapToInt()` to bridge from object streams to primitive streams.

## Ask
Given a list of employees, calculate the sum of all their ages.

## Input
List<Employee> employees (age derived from id via getAge())

## Expected Output
An int — the total of all ages

## API Category
Primitive Streams

## Hints

### Hint 1
You have a `Stream<Employee>` but need numeric summation — the primitive path.

### Hint 2
`Stream` has methods that convert to `IntStream` by extracting an int value from each element.

### Hint 3
Once in `IntStream`, `sum()` gives you the total directly — no boxing involved.

## What to investigate in the Java API
- Class: `java.util.stream.Stream`
- Look for:
  - `mapToInt(ToIntFunction<T>)` — returns IntStream
  - Related: `mapToLong`, `mapToDouble`
- Class: `java.util.stream.IntStream`
- Look for: `sum()`

## Four Implementation Passes

### 1. Imperative Java
```java
public static int exercise021Imperative(List<Employee> employees) {
    int total = 0;
    for (Employee e : employees) {
        total += e.getAge();
    }
    return total;
}
```

### 2. Stream without Collectors
```java
public static int exercise021(List<Employee> employees) {
    return employees.stream()
        .mapToInt(Employee::getAge)
        .sum();
}
```

### 3. Stream + Collectors
Alternative with a collector (boxed, less efficient — but the pattern to know):
```java
public static int exercise021Collect(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.summingInt(Employee::getAge));
}
```

### 4. Downstream Collector Composition
The collector form is what you need inside groupingBy:
```java
public static Map<String, Integer> exercise021Downstream(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::department,
            Collectors.summingInt(Employee::getAge)
        ));
}
```

### Edge cases
- Empty list -> sum() returns 0
- mapToInt avoids boxing overhead of map(e -> e.getAge()) which produces Stream<Integer>
- getAge() is a method on the record — a plain method reference works

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Stream.mapToInt(ToIntFunction).sum()
```

### Recognition pattern
- Sum/average/min/max of an OBJECT's numeric property
- mapToInt(Obj::getIntProperty) is the bridge
- In a grouping context, switch to Collectors.summingInt() as downstream

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement sumAges(List<Employee>) using mapToInt().sum()
4. Check if you got it right
