# Exercise 059 — Map.forEach: iterate and accumulate

## Objective
Train recognition of `Map.forEach(BiConsumer)` for side-effect iteration.

## Ask
Given a map with integer values, return the sum of all values, iterating with `Map.forEach`.

## Input
Map<String, Integer> map = {a=1, b=2, c=3}

## Expected Output
6

## API Category
Map API

## Hints

### Hint 1
`Map.forEach` takes a two-argument consumer: (key, value).

### Hint 2
Lambdas can't mutate local variables — accumulate into an array cell, a field, or an AtomicLong.

### Hint 3
For a pure value, streams are cleaner: values().stream().mapToInt(...).sum().

## What to investigate in the Java API
- Interface: `java.util.Map`
- Look for: `forEach(BiFunction -> BiConsumer<K, V> action)`
- Class: `java.util.stream.Stream` — `values().stream().mapToInt(Integer::intValue).sum()` as the functional alternative

## Four Implementation Passes

### 1. Imperative Java
```java
public static int exercise059Imperative(Map<String, Integer> map) {
    int sum = 0;
    for (int value : map.values()) {
        sum += value;
    }
    return sum;
}
```

### 2. Stream without Collectors
```java
public static int exercise059Stream(Map<String, Integer> map) {
    return map.values().stream()
        .mapToInt(Integer::intValue)
        .sum();
}
```

### 3. Stream + Collectors
```java
public static int exercise059Collect(Map<String, Integer> map) {
    return map.values().stream()
        .collect(Collectors.summingInt(Integer::intValue));
}
```

### 4. Idiomatic Map API
```java
public static int exercise059(Map<String, Integer> map) {
    int[] sum = {0};
    map.forEach((k, v) -> sum[0] += v);
    return sum[0];
}
```

### Edge cases
- forEach is for SIDE EFFECTS (logging, mutation of external state) — accumulating a return value fits streams better
- Effectively-final restriction forces the sum[0] trick in the forEach version
- Empty map -> 0

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Map.forEach((k, v) -> ...)                     // side-effect iteration
Map.values().stream().mapToInt(...).sum()      // pure computation
```

### Recognition pattern
- "Do something with each entry" -> forEach
- "Compute something from all values" -> values().stream()
- BiConsumer signature: two parameters, no return

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement sumValues(Map) three ways: for-each loop, Map.forEach, values().stream()
4. Check if you got it right
