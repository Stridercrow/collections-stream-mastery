# Exercise 028 — Arrays.stream vs Stream.of for int Arrays

## Objective
Train recognition of a classic pitfall: `Stream.of(int[])` does NOT give an IntStream.

## Ask
Given an array of ints, return the sum. Implement it the CORRECT way — and understand why `Stream.of` would be wrong here.

## Input
int[] values = {1, 2, 3, 4, 5}

## Expected Output
15

## API Category
Primitive Streams

## Hints

### Hint 1
`Stream.of(T...)` treats an int[] as a single Object — the whole array becomes ONE element of a `Stream<int[]>`.

### Hint 2
To get an `IntStream` from an array, use the `Arrays` utility class.

### Hint 3
`Arrays.stream(int[])` returns `IntStream` directly, with `.sum()` available.

## What to investigate in the Java API
- Class: `java.util.Arrays` — `stream(int[])` -> `IntStream`
- Class: `java.util.stream.Stream` — `of(T... values)`
- The pitfall:
  - `Stream.of(intArray)` -> `Stream<int[]>` with 1 element
  - `Arrays.stream(intArray)` -> `IntStream` with N elements

## Four Implementation Passes

### 1. Imperative Java
```java
public static int exercise028Imperative(int[] values) {
    int sum = 0;
    for (int value : values) {
        sum += value;
    }
    return sum;
}
```

### 2. Stream without Collectors (CORRECT)
```java
public static int exercise028(int[] values) {
    return Arrays.stream(values).sum();
}
```

### 3. Stream + Collectors (WRONG attempt shown for recognition)
```java
// Does NOT compile: Stream.of(values) is Stream<int[]>, not IntStream
// Stream.of(values).sum();

// Compiles but sums... nothing useful (flatMaps the single int[] element):
public static int exercise028ViaStreamOf(int[] values) {
    return Stream.of(values)
        .flatMapToInt(Arrays::stream)
        .sum();
}
```

### 4. Downstream Collector Composition
Not applicable — primitive stream exercise.

### Edge cases
- The wrongness is silent when combined with map: Stream.of(values).map(...) still compiles but maps the ARRAY, not the elements
- For Integer[] (object arrays), Stream.of works fine and gives Stream<Integer>
- Rule of thumb: primitive arrays -> Arrays.stream(); object arrays/elements -> Stream.of()

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Arrays.stream(int[])       // IntStream — correct for primitive arrays
Stream.of(T...)            // Stream<T> — correct for objects, DANGER with int[]
```

### Recognition pattern
- int[]/long[]/double[] as input -> Arrays.stream()
- See Stream.of used with a primitive array -> alarm bell: that's Stream<int[]>
- Escape hatch if stuck with Stream.of: flatMapToInt(Arrays::stream)

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement sumCorrectly(int[]) and explain why Stream.of would fail
4. Check if you got it right
