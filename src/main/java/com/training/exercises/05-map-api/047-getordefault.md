# Exercise 047 — Map.getOrDefault

## Objective
Train recognition of `Map.getOrDefault()` for safe reads with fallback values.

## Ask
Given a map and a key, return the value for that key, or the string "NOT FOUND" if the key is absent.

## Input
Map<String, String> map = {a=1}
String key = "a" (present) and key = "z" (absent)

## Expected Output
- get "a" -> "1"
- get "z" -> "NOT FOUND"

## API Category
Map API

## Hints

### Hint 1
The old way: get the value, check for null, substitute a default.

### Hint 2
`Map` has a method that takes the key AND the fallback in one call.

### Hint 3
It's a read operation — the map is never modified.

## What to investigate in the Java API
- Interface: `java.util.Map`
- Look for: `getOrDefault(Object key, V defaultValue)`
- Default is returned when the key is absent OR mapped to null

## Four Implementation Passes

### 1. Imperative Java
```java
public static String exercise047Imperative(Map<String, String> map, String key) {
    String value = map.get(key);
    return value != null ? value : "NOT FOUND";
}
```

### 2. Stream without Collectors
Not applicable — this is a Map read exercise.

### 3. Stream + Collectors
Not applicable — no stream involved.

### 4. Idiomatic Map API
```java
public static String exercise047(Map<String, String> map, String key) {
    return map.getOrDefault(key, "NOT FOUND");
}
```

### Edge cases
- Key present with null value -> getOrDefault returns the DEFAULT, not null
- The default is evaluated eagerly even when the key exists — for expensive defaults use computeIfAbsent (which stores it) or `Optional.ofNullable(map.get(k)).orElse(default)`
- Works on any Map implementation

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Map.getOrDefault(key, defaultValue)
```

### Recognition pattern
- "Read with fallback / default value if missing"
- getOrDefault(k, default) — replaces get + null check
- Counter reads: map.getOrDefault(word, 0)

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement readWithDefault(Map, key) using getOrDefault
4. Check if you got it right
