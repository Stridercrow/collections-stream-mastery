# Exercise 017 — IntStream.range: Sum 1 to n

## Objective
Train recognition of `IntStream.range()` as the idiomatic way to build a numeric sequence.

## Ask
Given a number n, calculate the sum of all numbers from 1 up to (but not including) n.

## Input
int n = 5

## Expected Output
10  (1 + 2 + 3 + 4)

## API Category
Primitive Streams

## Hints

### Hint 1
`IntStream` has a static method that generates a sequential ordered stream from startInclusive to endExclusive.

### Hint 2
Note the boundary rule: `range(1, n)` includes 1 but EXCLUDES n.

### Hint 3
The resulting `IntStream` has a `sum()` terminal operation.

## What to investigate in the Java API
- Class: `java.util.stream.IntStream`
- Look for:
  - `IntStream.range(int startInclusive, int endExclusive)`
  - `IntStream.rangeClosed(int startInclusive, int endInclusive)`
  - `sum()` terminal operation

## Four Implementation Passes

### 1. Imperative Java
```java
public static int exercise017Imperative(int n) {
    int sum = 0;
    for (int i = 1; i < n; i++) {
        sum += i;
    }
    return sum;
}
```

### 2. Stream without Collectors
```java
public static int exercise017(int n) {
    return IntStream.range(1, n).sum();
}
```

### 3. Stream + Collectors
Not applicable — primitive stream terminal operation.

### 4. Downstream Collector Composition
Not applicable — primitive stream exercise.

### Edge cases
- n = 0 or n = 1 -> range is empty -> sum = 0
- Negative n -> empty range -> 0
- Confusing range() with rangeClosed() shifts the result by n

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
IntStream.range(startInclusive, endExclusive).sum()
```

### Recognition pattern
- Sequence of consecutive numbers
- range() = [start, end), rangeClosed() = [start, end]
- Primitive loop replacement

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement sumRange(int n) using IntStream.range().sum()
4. Check if you got it right
