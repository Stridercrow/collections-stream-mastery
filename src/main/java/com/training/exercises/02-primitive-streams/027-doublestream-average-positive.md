# Exercise 027 — DoubleStream: Average of Positive Values

## Objective
Train recognition of `DoubleStream` operations and its `OptionalDouble` handling.

## Ask
Given an array of doubles, return the average of only the positive values. Return 0.0 if there are none.

## Input
double[] values = {1.5, -2.0, 3.5, -4.0, 5.0}

## Expected Output
3.3333333333333335  ((1.5 + 3.5 + 5.0) / 3)

## API Category
Primitive Streams

## Hints

### Hint 1
`Arrays.stream()` for a double[] produces a `DoubleStream`.

### Hint 2
Filter out the non-positive values first.

### Hint 3
`average()` returns `OptionalDouble` — handle the empty case with `orElse(0.0)`.

## What to investigate in the Java API
- Class: `java.util.Arrays` — `stream(double[])` returns `DoubleStream`
- Class: `java.util.stream.DoubleStream`
- Look for: `filter(DoublePredicate)`, `average()`
- Class: `java.util.OptionalDouble` — `orElse(double)`

## Four Implementation Passes

### 1. Imperative Java
```java
public static double exercise027Imperative(double[] values) {
    double sum = 0;
    int count = 0;
    for (double value : values) {
        if (value > 0) {
            sum += value;
            count++;
        }
    }
    return count == 0 ? 0.0 : sum / count;
}
```

### 2. Stream without Collectors
```java
public static double exercise027(double[] values) {
    return Arrays.stream(values)
        .filter(v -> v > 0)
        .average()
        .orElse(0.0);
}
```

### 3. Stream + Collectors
```java
public static double exercise027Collect(double[] values) {
    return Arrays.stream(values)
        .filter(v -> v > 0)
        .collect(Collectors.averagingDouble(Double::doubleValue));
}
```

### 4. Downstream Collector Composition
Not applicable — primitive stream exercise.

### Edge cases
- Empty array -> 0.0
- No positive values -> empty stream after filter -> 0.0
- Doubles can have NaN — average() with NaN inputs yields NaN (exercise assumption: no NaN)

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Arrays.stream(double[]).filter(...).average().orElse(0.0)
```

### Recognition pattern
- double[] input -> DoubleStream
- filter + average + OptionalDouble — same rhythm as IntStream
- The three primitive streams share the same core operations

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement averagePositive(double[]) using Arrays.stream().filter().average()
4. Check if you got it right
