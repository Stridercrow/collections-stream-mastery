# Exercise 104 — Transform map values with toMap: append " items"

## Objective
Drill value transformation via entrySet().stream() + toMap with a formatted value mapper.

## Ask
Given a map with integer values, return a new map with the same keys but values formatted as "<n> items" (e.g., "5 items").

## Input
Map<String, Integer> map = {pen=5, book=2}

## Expected Output
{pen="5 items", book="2 items"}

## API Category
Advanced Map Exercises

## Hints

### Hint 1
Stream the entries; keys pass through untouched.

### Hint 2
toMap(keyMapper, valueMapper) — the valueMapper does the formatting.

### Hint 3
String concatenation: e.getValue() + " items".

## What to investigate in the Java API
- Interface: `java.util.Map.Entry` — `getKey()`, `getValue()`
- Class: `java.util.stream.Collectors` — `toMap(Function, Function)`

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, String> exercise104Imperative(Map<String, Integer> map) {
    Map<String, String> result = new HashMap<>();
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
        result.put(entry.getKey(), entry.getValue() + " items");
    }
    return result;
}
```

### 2. Stream without Collectors
Not applicable — rebuilding a Map requires a collector.

### 3. Stream + Collectors
```java
public static Map<String, String> exercise104(Map<String, Integer> map) {
    return map.entrySet().stream()
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            e -> e.getValue() + " items"
        ));
}
```

### 4. Downstream Collector Composition
Order-preserving variant with LinkedHashMap::new:
```java
public static Map<String, String> exercise104Ordered(Map<String, Integer> map) {
    return map.entrySet().stream()
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            e -> e.getValue() + " items",
            (a, b) -> a,
            LinkedHashMap::new
        ));
}
```

### Edge cases
- Value TYPE changes (Integer -> String) — the pipeline handles it transparently
- Original map is untouched — toMap builds a NEW map
- Pluralization edge: 1 -> "1 items"? Real code would use MessageFormat — the exercise trains the API, not i18n

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Map.entrySet().stream().collect(Collectors.toMap(Entry::getKey, e -> format(e.getValue())))
```

### Recognition pattern
- "Relabel / reformat every map value"
- toMap(key passthrough, value transformer) — third repetition of this shape (081, 082, 104)
- In-place same-type alternative: replaceAll

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement labelItems(Map) using entrySet().stream() + toMap
4. Check if you got it right
