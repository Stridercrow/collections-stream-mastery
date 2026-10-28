# Exercise 056 — Map.remove: delete a key

## Objective
Train recognition of `Map.remove()` and its conditional two-argument form.

## Ask
Given a map and a key, remove the entry for that key if it exists. Return the map.

## Input
Map<String, String> map = {a=1, b=2}
String key = "a" (present) and key = "z" (absent)

## Expected Output
- remove("a") -> {b=2}
- remove("z") -> unchanged

## API Category
Map API

## Hints

### Hint 1
Removal is the simplest Map operation — the learning point is the return value and the conditional variant.

### Hint 2
`remove(key)` returns the removed value, or null if the key wasn't there.

### Hint 3
There's a two-arg form that removes only if the current value matches — a conditional delete.

## What to investigate in the Java API
- Interface: `java.util.Map`
- Look for:
  - `remove(Object key)` — returns previous value or null
  - `remove(Object key, Object value)` — removes only if mapped to that exact value; returns boolean

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, String> exercise056Imperative(Map<String, String> map, String key) {
    if (map.containsKey(key)) {
        map.remove(key);
    }
    return map;
}
```

### 2. Stream without Collectors
Stream alternative that builds a NEW map without the key:
```java
public static Map<String, String> exercise056New(Map<String, String> map, String key) {
    return map.entrySet().stream()
        .filter(e -> !e.getKey().equals(key))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
}
```

### 3. Stream + Collectors
Not applicable — removal is naturally a Map operation.

### 4. Idiomatic Map API
```java
public static Map<String, String> exercise056(Map<String, String> map, String key) {
    map.remove(key);
    return map;
}
```

### Edge cases
- remove returns the removed VALUE (useful to know what you deleted), not the map
- remove(key, value) needs equals() to match — removes nothing on mismatch
- Removing while iterating a map: use Iterator.remove() or entrySet().removeIf(...), never map.remove inside for-each (ConcurrentModificationException)

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Map.remove(key)          // unconditional
Map.remove(key, value)   // conditional on value
```

### Recognition pattern
- "Delete entry"
- remove(k) — and check the boolean/returned value when the outcome matters
- Filter-based removal during iteration -> entrySet().removeIf(...)

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement deleteKey(Map, key) using remove
4. Check if you got it right
