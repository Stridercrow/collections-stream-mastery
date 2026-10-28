# Exercise 003 — IntStream Summary Statistics

## Objective
Train recognition of `summaryStatistics()` for getting all statistics at once.

## Ask
Given an array of integers, return an `IntSummaryStatistics` object containing count, sum, min, average, and max.

## Input
int[] values = {1, 2, 3, 4, 5}

## Expected Output
IntSummaryStatistics{count=5, sum=15, min=1, average=3.000000, max=5}

## API Category
Primitive Streams

## Hints

### Hint 1
`IntStream` has a method that returns all statistics at once.

### Hint 2
The method returns `IntSummaryStatistics`.

### Hint 3
It's a terminal operation that doesn't need a Collector for primitive streams.

## What to investigate in the Java API
- Class: `java.util.stream.IntStream` for `summaryStatistics()`
- Class: `java.util.IntSummaryStatistics` for the result

## Four Implementation Passes

### 1. Imperative Java
```java
public static IntSummaryStatistics exercise003Imperative(int[] values) {
    if (values.length == 0) {
        return new IntSummaryStatistics(0, 0, 0, 0);
    }
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    long sum = 0;
    for (int value : values) {
        min = Math.min(min, value);
        max = Math.max(max, value);
        sum += value;
    }
    return new IntSummaryStatistics(
        values.length, sum, min, max
    );
}
```
### 2. Stream without Collectors
```java
public static IntSummaryStatistics exercise003(int[] values) {
    return Arrays.stream(values).summaryStatistics();
}
```

### 3. Stream + Collectors
Alternative using Collectors.summarizingInt();
```java
public static IntSummaryStatistics exercise003Collector(int [] values) {
    return Arrays.stream(values)
        .boxed()
        .collect(Collectors.summarizingInt(Integer::intValue));
}
```

### 4. Downstream Collector Composition
Not applicable - this is a primitive Stream exercise

### Edge cases
- Empty array -> count=0, sum=0, min-max invalid
- Single element -> min=max=that element

### API Recognition
What Java API should I immediately think about when I see this requirement?
Array.streams(int[]).summaryStatistics()

### Recognition pattern
Need statistics (count, sum, min, avg, max)
One operation gives everything


