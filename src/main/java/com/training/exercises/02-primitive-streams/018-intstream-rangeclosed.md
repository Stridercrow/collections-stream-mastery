# Exercise 018 — IntStream.rangeClosed: Sum Including n

## Objective
Train recognition of `IntStream.rangeClosed()` versus `range()` boundary semantics.

## Ask
Given a number n, calculate the sum of all numbers from 1 to n INCLUSIVE.

## Input
int n = 5

## Expected Output
15  (1 + 2 + 3 + 4 + 5)

## API Category
Primitive Streams

## Hints

### Hint 1
This is the same as Exercise 017, but the upper bound must be included.

### Hint 2
`IntStream` has a sibling method of `range()` where both bounds are inclusive.

### Hint 3
The method name literally says what it does: rangeClosed.

## What to investigate in the Java API
- Class: `java.util.stream.IntStream`
- Look for:
  - `IntStream.rangeClosed(int startInclusive, int endInclusive)`
  - `sum()` terminal operation

## Four Implementation Passes

### 1. Imperative Java
```java
public static int exercise018Imperative(int n) {
    int sum = 0;
    for (int i = 1; i <= n; i++) {
        sum += i;
    }
    return sum;
}
```

### 2. Stream without Collectors
```java
public static int exercise018(int n) {
    return IntStream.rangeClosed(1, n).sum();
}
```

### 3. Stream + Collectors
Not applicable — primitive stream terminal operation.

### 4. Downstream Collector Composition
Not applicable — primitive stream exercise.

### Edge cases
- n = 0 -> rangeClosed(1, 0) is empty -> sum = 0
- n = 1 -> sum = 1 (single element included)
- This is the classic "sum 1 to n" — Gauss formula n*(n+1)/2 also works, but the exercise trains the API

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
IntStream.rangeClosed(startInclusive, endInclusive).sum()
```

### Recognition pattern
- "From 1 to n INCLUSIVE" -> rangeClosed
- "Up to but excluding n" -> range
- The most common mistake is picking the wrong one

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement sumRangeClosed(int n) using IntStream.rangeClosed().sum()
4. Check if you got it right
