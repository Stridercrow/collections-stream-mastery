# Exercise 002 — Average int[] using IntStream

## Objective
Train recognition that `IntStream.average()` returns `OptionalDouble`.

## Ask
Given an array of integers, calculate and return the average.

## Input
int[] values = {1, 2, 3, 4, 5}

## Expected Output
3.0


## API Category
Primitive Streams

## Hints

### Hint 1
Use `Arrays.stream()` to get an `IntStream`.

### Hint 2
`IntStream` has an `average()` method that returns an `OptionalDouble`.

### Hint 3
Use `orElse(0.0)` to handle empty arrays.

## What to investigate in the Java API
- Class: `java.util.Arrays` for `stream()`
- Class: `java.util.stream.IntStream` for `average()`
- Class: `java.util.OptionalDouble` for `orElse()`

## Four Implementation Passes

### 1. Imperative Java
```java
public static double exercise002Imperative(int[] values) {
    if (values.length == 0) return 0.0;
    int sum = 0;
    for (int value : values) {
        sum += value;
    }
    return (double) sum / values.length;
}
```

### 2. Stream without Collectors
```java
public static double exercise002(int[] values) {
    return Arrays.stream(values)
        .average()
        .orElse(0.0);
}
```

### 3. Stream + Collectors
Not applicable - this is a primitive Stream terminal operation

### 4. Downstream Collector Composition
Not applicable - this is a primitive Stream terminal operation

### Edge Cases
Empty array -> return 0.0
All negative numbers -> average is negative
Large numbers -> use double for precision

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Arrays.stream(int[]).average().orElse(0.0)
```

### Recognition pattern
Average of int values
Returns OptionalDouble -> need to handle empty case
