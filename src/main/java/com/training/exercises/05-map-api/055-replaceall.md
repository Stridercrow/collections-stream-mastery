# Exercise 055 — Map.replaceAll: transform all values

## Objective
Train recognition of `Map.replaceAll()` for bulk value transformation in place.

## Ask
Given a map with integer values, double every value, modifying the map in place. Return the map.

## Input
Map<String, Integer> map = {a=1, b=2, c=3}

## Expected Output
{a=2, b=4, c=6}

## API Category
Map API

## Hints

### Hint 1
You could iterate entrySet() and put back new values — verbose.

### Hint 2
`Map` has a method that applies a function to every entry and replaces each value with the result.

### Hint 3
The function receives (key, value) and returns the replacement value.

## What to investigate in the Java API
- Interface: `java.util.Map`
- Look for: `replaceAll(BiFunction<K, V, V> function)`
- Modifies the map in place — values are replaced entry by entry

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, Integer> exercise055Imperative(Map<String, Integer> map) {
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
        entry.setValue(entry.getValue() * 2);
    }
    return map;
}
```

### 2. Stream without Collectors
Stream-based alternative that builds a NEW map instead of mutating:
```java
public static Map<String, Integer> exercise055New(Map<String, Integer> map) {
    return map.entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue() * 2));
}
```

### 3. Stream + Collectors
```java
public static Map<String, Integer> exercise055Collect(Map<String, Integer> map) {
    return map.entrySet().stream()
        .collect(HashMap::new, (m, e) -> m.put(e.getKey(), e.getValue() * 2), HashMap::putAll);
}
```

### 4. Idiomatic Map API
```java
public static Map<String, Integer> exercise055(Map<String, Integer> map) {
    map.replaceAll((k, v) -> v * 2);
    return map;
}
```

### Edge cases
- replaceAll MUTATES the original map — choose based on whether callers expect mutation
- Returning null from the function deletes the entry (same rule as compute)
- Building a new map vs in-place: replaceAll for mutation, stream+toMap for immutability

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Map.replaceAll((k, v) -> newValue)
```

### Recognition pattern
- "Transform every value in the map"
- replaceAll((k, v) -> ...) — in-place bulk edit
- Stream alternative when a new map is wanted: entrySet().stream() + toMap

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement doubleValues(Map) using replaceAll, then as a stream version
4. Check if you got it right
