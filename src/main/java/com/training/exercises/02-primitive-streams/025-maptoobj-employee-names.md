# Exercise 025 — mapToObj: Generate Names from IntStream

## Objective
Train recognition of `IntStream.mapToObj()` to build objects from a numeric sequence.

## Ask
Given a number n, return a list of n employee names generated as "Employee-1", "Employee-2", ..., "Employee-n".

## Input
int n = 3

## Expected Output
[Employee-1, Employee-2, Employee-3]

## API Category
Primitive Streams

## Hints

### Hint 1
Start with a numeric range from 1 to n inclusive.

### Hint 2
`IntStream` has a method that converts to an object stream while transforming each int.

### Hint 3
The transformation itself is string concatenation with the loop index.

## What to investigate in the Java API
- Class: `java.util.stream.IntStream`
- Look for:
  - `IntStream.rangeClosed(int, int)`
  - `mapToObj(IntFunction<R>)` — returns `Stream<R>`
- Compare with `boxed()`: mapToObj transforms AND converts in one step

## Four Implementation Passes

### 1. Imperative Java
```java
public static List<String> exercise025Imperative(int n) {
    List<String> result = new ArrayList<>();
    for (int i = 1; i <= n; i++) {
        result.add("Employee-" + i);
    }
    return result;
}
```

### 2. Stream without Collectors
```java
public static List<String> exercise025(int n) {
    return IntStream.rangeClosed(1, n)
        .mapToObj(i -> "Employee-" + i)
        .toList();
}
```

### 3. Stream + Collectors
```java
public static List<String> exercise025Collect(int n) {
    return IntStream.rangeClosed(1, n)
        .mapToObj(i -> "Employee-" + i)
        .collect(Collectors.toList());
}
```

### 4. Downstream Collector Composition
Not applicable — this is a direct generation exercise.

### Edge cases
- n = 0 -> empty list
- n negative -> empty list (empty range)
- mapToObj vs boxed(): boxed() only wraps; mapToObj also maps

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
IntStream.rangeClosed(1, n).mapToObj(i -> ...).toList()
```

### Recognition pattern
- Generate objects from an index/counter
- rangeClosed() for the sequence + mapToObj() to build each object
- The classic "repeat N times with index" stream idiom

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement generateNames(int n) using IntStream.rangeClosed().mapToObj()
4. Check if you got it right
