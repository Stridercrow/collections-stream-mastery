# Exercise 046 — Map.put vs Map.putIfAbsent

## Objective
Train recognition of `Map.putIfAbsent()` for non-destructive insertion.

## Ask
Given a mutable map and a key/value pair, insert the value ONLY if the key is not already present. Return the map.

## Input
Map<String, String> map = {a=1}
String key = "a" (already present), and key = "b" (not present), value = "X"

## Expected Output
- putIfAbsent("a", "X") -> {a=1}       (existing value kept)
- putIfAbsent("b", "X") -> {a=1, b=X}  (new entry added)

## API Category
Map API

## Hints

### Hint 1
Plain `put()` overwrites whatever was there.

### Hint 2
`Map` has a method that puts only when the key has no current mapping (or maps to null).

### Hint 3
It returns the PREVIOUS value, or null if there was none — often ignored.

## What to investigate in the Java API
- Interface: `java.util.Map`
- Compare:
  - `put(K, V)` — always sets, returns previous value
  - `putIfAbsent(K, V)` — sets only if absent (or mapped to null), returns previous value

## Four Implementation Passes

### 1. Imperative Java (baseline: plain put with a check)
```java
public static Map<String, String> exercise046Imperative(Map<String, String> map, String key, String value) {
    if (!map.containsKey(key)) {
        map.put(key, value);
    }
    return map;
}
```

### 2. Stream without Collectors
Not applicable — this is a Map mutation exercise.

### 3. Stream + Collectors
Not applicable — no stream involved.

### 4. Idiomatic Map API
```java
public static Map<String, String> exercise046(Map<String, String> map, String key, String value) {
    map.putIfAbsent(key, value);
    return map;
}
```

### Edge cases
- Key mapped to null -> putIfAbsent DOES replace it (null counts as absent)
- put returns the old value; putIfAbsent returns null when it inserted
- Useful for default-value initialization

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Map.putIfAbsent(key, value)
```

### Recognition pattern
- "Add only if missing / don't overwrite"
- putIfAbsent(k, v) — one call, no if
- Compute a default only when needed? That's computeIfAbsent with a Supplier

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement insertIfMissing(Map, k, v) using putIfAbsent
4. Check if you got it right
