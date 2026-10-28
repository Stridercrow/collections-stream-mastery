# Exercise 054 — Map.replace: replace value only if key exists

## Objective
Train recognition of `Map.replace()` as put-with-guard.

## Ask
Given a map, a key, and a new value, replace the value only if the key already exists. Do not create a new entry. Return the map.

## Input
Map<String, String> map = {a=1}
String key = "a" (present), newValue = "9"
String key = "z" (absent), newValue = "9"

## Expected Output
- replace("a", "9") -> {a=9}
- replace("z", "9") -> unchanged — "z" is NOT added

## API Category
Map API

## Hints

### Hint 1
`put()` would add the missing key — you need the guarded variant.

### Hint 2
`Map` has a method that replaces the value only for existing keys.

### Hint 3
It returns the old value (null if nothing was replaced) — the return value tells you whether it happened.

## What to investigate in the Java API
- Interface: `java.util.Map`
- Look for:
  - `replace(K key, V value)` — replaces only if key present; returns previous value or null
  - `replace(K key, V oldValue, V newValue)` — conditional on the current value (compare-and-set)

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, String> exercise054Imperative(Map<String, String> map, String key, String newValue) {
    if (map.containsKey(key)) {
        map.put(key, newValue);
    }
    return map;
}
```

### 2. Stream without Collectors
Not applicable — Map mutation exercise.

### 3. Stream + Collectors
Not applicable — no stream involved.

### 4. Idiomatic Map API
```java
public static Map<String, String> exercise054(Map<String, String> map, String key, String newValue) {
    map.replace(key, newValue);
    return map;
}
```

### Edge cases
- replace never creates entries — the defining property
- 3-arg replace(key, expectedOldValue, newValue) only replaces if the current value equals expected — a CAS-style update
- put returns the old value too but ALWAYS writes; replace returns null both when absent and when the old value was null

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Map.replace(key, newValue)
```

### Recognition pattern
- "Overwrite existing but never insert"
- replace(k, v) — the guarded put
- Need "only if value is still X"? 3-arg replace
- Opposite guard (only insert)? putIfAbsent

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement updateExisting(Map, key, newValue) using replace
4. Check if you got it right
