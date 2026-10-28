# Exercise 020 — IntStream.generate: Random Numbers

## Objective
Train recognition of `IntStream.generate()` for streams produced by a Supplier.

## Ask
Given a count, generate N random integers between 0 (inclusive) and 100 (exclusive). Return them as `List<Integer>`.

## Input
int count = 5  (Random with fixed seed 42 for reproducibility)

## Expected Output
A list of 5 integers, each in [0, 100)

## API Category
Primitive Streams

## Hints

### Hint 1
`IntStream` has a static method that takes an `IntSupplier` and calls it for each element.

### Hint 2
The stream is infinite — `limit()` is mandatory.

### Hint 3
`java.util.Random` has a method to get a random int within a bound.

## What to investigate in the Java API
- Class: `java.util.stream.IntStream`
- Look for:
  - `IntStream.generate(IntSupplier s)` — infinite stream
  - `limit(long maxSize)`
  - `boxed()`
- Class: `java.util.Random`
- Look for: `nextInt(int bound)`

## Four Implementation Passes

### 1. Imperative Java
```java
public static List<Integer> exercise020Imperative(int count) {
    Random random = new Random(42);
    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < count; i++) {
        result.add(random.nextInt(100));
    }
    return result;
}
```

### 2. Stream without Collectors
```java
public static List<Integer> exercise020(int count) {
    Random random = new Random(42); // fixed seed for reproducibility
    return IntStream.generate(() -> random.nextInt(100))
        .limit(count)
        .boxed()
        .toList();
}
```

### 3. Stream + Collectors
```java
public static List<Integer> exercise020Collect(int count) {
    Random random = new Random(42);
    return IntStream.generate(() -> random.nextInt(100))
        .limit(count)
        .boxed()
        .collect(Collectors.toList());
}
```

### 4. Downstream Collector Composition
Not applicable — primitive stream exercise.

### Edge cases
- count = 0 -> empty list
- Forgetting limit() -> infinite stream
- Supplier captures the random instance — the lambda has no parameters
- Alternative: `random.ints(count, 0, 100).boxed().toList()` does it in one call

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
IntStream.generate(supplier).limit(n).boxed().toList()
// or, for randoms specifically: random.ints(count, origin, bound)
```

### Recognition pattern
- Values produced by a function with no state dependency between calls
- generate(Supplier) vs iterate(seed, f): iterate uses the previous value, generate does not
- ALWAYS pair with limit()

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement randomNumbers(int count) using IntStream.generate()
4. Check if you got it right
