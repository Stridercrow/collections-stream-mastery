# Exercise 082 — Transform map entries: values to formatted strings

## Objective
Train the entrySet().stream() + toMap pipeline for VALUE transformation.

## Ask
Given a map with integer values, return a new map with the same keys but values converted to strings like "Value: 5".

## Input
Map<String, Integer> map = {a=1, b=5}

## Expected Output
{a="Value: 1", b="Value: 5"}

## API Category
Map + Stream Composition

## Hints

### Hint 1
Keys stay unchanged; only the value mapping function transforms.

### Hint 2
In toMap, the keyMapper is Entry::getKey; the valueMapper does the conversion.

### Hint 3
String concatenation in the lambda: "Value: " + e.getValue().

## What to investigate in the Java API
- Interface: `java.util.Map.Entry` — `getKey()`, `getValue()`
- Class: `java.util.stream.Collectors` — `toMap(Function, Function)`

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, String> exercise082Imperative(Map<String, Integer> map) {
    Map<String, String> result = new HashMap<>();
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
        result.put(entry.getKey(), "Value: " + entry.getValue());
    }
    return result;
}
```

### 2. Stream without Collectors
Not applicable — rebuilding a Map requires a collector.

### 3. Stream + Collectors
```java
public static Map<String, String> exercise082(Map<String, Integer> map) {
    return map.entrySet().stream()
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            e -> "Value: " + e.getValue()
        ));
}
```

### 4. Downstream Collector Composition
Compare with in-place mutation — replaceAll modifies the ORIGINAL map (different types here, but the pattern):
```java
public static Map<String, Integer> exercise082InPlace(Map<String, Integer> map) {
    map.replaceAll((k, v) -> v * 2); // mutates; returns same map
    return map;
}
```

### Edge cases
- New map vs in-place: toMap pipeline creates a NEW map; replaceAll mutates
- Value type changes Integer -> String — toMap handles it naturally
- Null values: "Value: " + null produces "Value: null" (no NPE from concatenation)

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Map.entrySet().stream().collect(Collectors.toMap(Entry::getKey, e -> transform(e.getValue())))
```

### Recognition pattern
- "Transform map values / convert types"
- toMap(Entry::getKey, valueMapper) — keep keys, map values
- Same-type in-place alternative: replaceAll

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement stringifyValues(Map) using entrySet().stream() + toMap
4. Check if you got it right
