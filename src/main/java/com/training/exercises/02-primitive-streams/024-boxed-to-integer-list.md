# Exercise 024 — boxed(): int[] to List<Integer>

## Objective
Train recognition of `IntStream.boxed()` as the bridge from primitive to wrapper streams.

## Ask
Given an array of ints, return a `List<Integer>` containing the same values.

## Input
int[] values = {1, 2, 3, 4, 5}

## Expected Output
[1, 2, 3, 4, 5] as List<Integer>

## API Category
Primitive Streams

## Hints

### Hint 1
You cannot call `toList()` on an `IntStream` and get `List<Integer>` — the types don't match.

### Hint 2
`IntStream` has a method that converts it to `Stream<Integer>` by wrapping each int.

### Hint 3
The method is literally named `boxed()` — it puts each primitive into its wrapper box.

## What to investigate in the Java API
- Class: `java.util.stream.IntStream`
- Look for: `boxed()` — returns `Stream<Integer>`
- Related conversions:
  - `asLongStream()`, `asDoubleStream()` — primitive-to-primitive widening
  - `mapToObj(IntFunction<R>)` — alternative to boxed() when also transforming

## Four Implementation Passes

### 1. Imperative Java
```java
public static List<Integer> exercise024Imperative(int[] values) {
    List<Integer> result = new ArrayList<>();
    for (int value : values) {
        result.add(value); // autoboxing happens here
    }
    return result;
}
```

### 2. Stream without Collectors
```java
public static List<Integer> exercise024(int[] values) {
    return Arrays.stream(values)
        .boxed()
        .toList();
}
```

### 3. Stream + Collectors
```java
public static List<Integer> exercise024Collect(int[] values) {
    return Arrays.stream(values)
        .boxed()
        .collect(Collectors.toList());
}
```

### 4. Downstream Collector Composition
boxed() also enables collectors that need objects:
```java
public static Set<Integer> exercise024Downstream(int[] values) {
    return Arrays.stream(values)
        .boxed()
        .collect(Collectors.toSet());
}
```

### Edge cases
- Empty array -> empty list
- boxed() has a boxing cost — stay primitive when possible
- mapToObj(i -> i) achieves the same thing

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
IntStream.boxed()
```

### Recognition pattern
- Primitive stream but need objects (List<Integer>, Set<Integer>, collectors that need wrappers)
- boxed() = IntStream -> Stream<Integer>
- Reverse direction: mapToInt / intValue / unboxing

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement toIntegerList(int[]) using Arrays.stream().boxed().toList()
4. Check if you got it right
