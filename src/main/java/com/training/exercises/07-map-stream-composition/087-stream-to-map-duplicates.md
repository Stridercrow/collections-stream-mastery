# Exercise 087 — Stream to map with duplicate key handling

## Objective
Reinforce the 3-arg toMap merge function when duplicate keys are GUARANTEED to occur.

## Ask
Given a list of words (possibly with repeats), build a `Map<String, Integer>` mapping each DISTINCT word to the length of its FIRST occurrence position word. Duplicate words must not crash — keep the first mapping.

## Input
List<String> words = [java, stream, java, api]

## Expected Output
{java=4, stream=6, api=3} — second "java" does not overwrite

## API Category
Map + Stream Composition

## Hints

### Hint 1
Key: the word itself — `Function.identity()`.

### Hint 2
Value: the word's length — `String::length`.

### Hint 3
Repeated keys WILL collide: the 2-arg toMap throws IllegalStateException, so use the merge function (existing, replacement) -> existing.

## What to investigate in the Java API
- Class: `java.util.stream.Collectors` — `toMap(keyMapper, valueMapper, BinaryOperator mergeFunction)`
- Interface: `java.util.function.Function` — `identity()`

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, Integer> exercise087Imperative(List<String> words) {
    Map<String, Integer> result = new HashMap<>();
    for (String word : words) {
        result.putIfAbsent(word, word.length());
    }
    return result;
}
```

### 2. Stream without Collectors
Not applicable — Map building requires a collector.

### 3. Stream + Collectors
```java
public static Map<String, Integer> exercise087(List<String> words) {
    return words.stream()
        .collect(Collectors.toMap(
            Function.identity(),
            String::length,
            (existing, replacement) -> existing
        ));
}
```

### 4. Downstream Collector Composition
Last occurrence wins instead — flip the merge:
```java
public static Map<String, Integer> exercise087LastWins(List<String> words) {
    return words.stream()
        .collect(Collectors.toMap(
            Function.identity(),
            String::length,
            (existing, replacement) -> replacement
        ));
}
```

### Edge cases
- 2-arg toMap + duplicate keys = IllegalStateException at runtime — memorize this failure mode
- keep-first: (a, b) -> a; keep-last: (a, b) -> b
- Function.identity() == w -> w, but reads better and enables method-reference chains

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Collectors.toMap(Function.identity(), valueMapper, (existing, replacement) -> existing)
```

### Recognition pattern
- Stream to Map where key may repeat -> ALWAYS 3-arg toMap
- identity() as keyMapper for "the element is the key"
- This closes the Map+Stream section: every pipeline used entrySet() or toMap

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement wordLengths(List<String>) with 3-arg toMap, first-wins
4. Check if you got it right
