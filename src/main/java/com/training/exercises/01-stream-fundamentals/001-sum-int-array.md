# Exercise 001 — Sum int[] using IntStream

## Objective
Train recognition that `int[]` arrays should be processed with `IntStream`, not `Stream<Integer>`.

## Ask
Given an array of integers, calculate and return the sum.

## Input
int[] values = {1, 2, 3, 4, 5}

## Expected Output
15


## API Category
Primitive Streams

## Hints

### Hint 1
Java has specialized streams for primitive types to avoid boxing overhead.

### Hint 2
Look at `java.util.Arrays` — it has a method that creates a stream from an array.

### Hint 3
The method returns an `IntStream`, which has a `sum()` terminal operation.

## What to investigate in the Java API

- Class: `java.util.Arrays`
- Look for a method that:
  - Takes an `int[]` parameter
  - Returns an `IntStream`
- Then look at `IntStream` for a method that returns the sum

## Four Implementation Passes

### 1. Imperative Java
```java
public static int exercise001Imperative(int[] values) {
    int sum = 0;
    for (int value : values) {
        sum += value;
    }
    return sum;
}
```

### 2. Stream without Collectors
```java
public static int exercise001(int[] values) {
    return Arrays.stream(values).sum();
}
```

### 3. Stream + Collectors
Not applicable - this is a primitive Stream terminal operation

### 4. Downstream Collector Composition
Not applicable - this is a primitive Stream exercise

### Edge cases

- Empty array -> return 0
- Large numbers -> sum may overflow int (use long if needed)
- Negative numbers -> works correctly

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Arrays.stream(int[]).sum()
```

### Recognition pattern
Sum of int values
int[] array as input
Use primitive Stream for performance

### Repetition Challenge
Close this solution and implement it agian from memory:
1. Close the file
2. Open a scratch file
3. Implement sumIntArray(int[] values) using Arrays.stream().sum()
4. Check if you got it right

