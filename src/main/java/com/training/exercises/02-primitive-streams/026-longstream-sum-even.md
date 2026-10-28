# Exercise 026 — LongStream: Sum of Even Numbers

## Objective
Train recognition that `Arrays.stream()` has overloads producing `LongStream` for long arrays.

## Ask
Given an array of longs, return the sum of only the even values.

## Input
long[] values = {1, 2, 3, 4, 5, 6}

## Expected Output
12  (2 + 4 + 6)

## API Category
Primitive Streams

## Hints

### Hint 1
`Arrays.stream()` is overloaded for int[], long[], and double[].

### Hint 2
A long[] produces a `LongStream`, which supports filter, sum, and friends just like IntStream.

### Hint 3
Filter with a modulo check for evenness, then sum.

## What to investigate in the Java API
- Class: `java.util.Arrays` — `stream(long[])` returns `LongStream`
- Class: `java.util.stream.LongStream`
- Look for: `filter(LongPredicate)`, `sum()`

## Four Implementation Passes

### 1. Imperative Java
```java
public static long exercise026Imperative(long[] values) {
    long sum = 0;
    for (long value : values) {
        if (value % 2 == 0) {
            sum += value;
        }
    }
    return sum;
}
```

### 2. Stream without Collectors
```java
public static long exercise026(long[] values) {
    return Arrays.stream(values)
        .filter(v -> v % 2 == 0)
        .sum();
}
```

### 3. Stream + Collectors
Alternative with a collector:
```java
public static long exercise026Collect(long[] values) {
    return Arrays.stream(values)
        .filter(v -> v % 2 == 0)
        .collect(Collectors.summingLong(Long::longValue));
}
```

### 4. Downstream Collector Composition
Not applicable — primitive stream exercise.

### Edge cases
- Empty array -> 0
- All odd values -> 0
- Negative even numbers -> included (e.g., -4 % 2 == 0)
- long[] does NOT autobox to Stream<Long> — you need LongStream

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Arrays.stream(long[]).filter(...).sum()
```

### Recognition pattern
- long[] input -> LongStream (not Stream<Long>)
- Same pattern as int[]: filter on the primitive stream, then sum()
- The primitive stream family: IntStream, LongStream, DoubleStream all share the same core API

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement sumEven(long[]) using Arrays.stream().filter().sum()
4. Check if you got it right
