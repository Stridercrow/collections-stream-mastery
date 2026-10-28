# Exercise 019 — IntStream.iterate: Generate Squares

## Objective
Train recognition of `IntStream.iterate()` for generating sequences from a seed and a function.

## Ask
Given a count, generate the first N perfect squares: 1, 4, 9, 16, ... Return them as `List<Integer>`.

## Input
int count = 5

## Expected Output
[1, 4, 9, 16, 25]

## API Category
Primitive Streams

## Hints

### Hint 1
`IntStream` has a static method that takes a seed value and a function producing the next value.

### Hint 2
The stream is infinite — you MUST limit it.

### Hint 3
To return `List<Integer>` from an `IntStream`, convert with `boxed()` before `toList()`.

## What to investigate in the Java API
- Class: `java.util.stream.IntStream`
- Look for:
  - `IntStream.iterate(int seed, IntUnaryOperator f)` — infinite stream
  - `limit(long maxSize)` — required to make it finite
  - `map(IntUnaryOperator)` — transform values
  - `boxed()` — convert IntStream to Stream<Integer>

## Four Implementation Passes

### 1. Imperative Java
```java
public static List<Integer> exercise019Imperative(int count) {
    List<Integer> result = new ArrayList<>();
    int value = 1;
    for (int i = 0; i < count; i++) {
        result.add(value * value);
        value++;
    }
    return result;
}
```

### 2. Stream without Collectors
```java
public static List<Integer> exercise019(int count) {
    return IntStream.iterate(1, i -> i + 1)
        .map(i -> i * i)
        .limit(count)
        .boxed()
        .toList();
}
```

### 3. Stream + Collectors
```java
public static List<Integer> exercise019Collect(int count) {
    return IntStream.iterate(1, i -> i + 1)
        .map(i -> i * i)
        .limit(count)
        .boxed()
        .collect(Collectors.toList());
}
```

### 4. Downstream Collector Composition
Not applicable — primitive stream exercise.

### Edge cases
- count = 0 -> empty list
- Forgetting limit() -> infinite stream -> hangs or OutOfMemoryError
- Java 9+ also has `iterate(seed, hasNext, next)` with a predicate to stop

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
IntStream.iterate(seed, i -> next).limit(n).boxed().toList()
```

### Recognition pattern
- Sequence defined by a rule (seed + next function)
- iterate() for infinite/derived sequences
- ALWAYS pair with limit()
- Need List<Integer>? boxed() first

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement generateSquares(int count) using IntStream.iterate()
4. Check if you got it right
