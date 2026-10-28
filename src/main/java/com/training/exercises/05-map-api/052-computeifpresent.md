# Exercise 052 — Map.computeIfPresent: update only if exists

## Objective
Train recognition of `Map.computeIfPresent()` for guarded updates.

## Ask
Given a map, a key, and an increment, increase the value of that key by the increment ONLY if the key already exists. Do nothing if absent. Return the map.

## Input
Map<String, Integer> map = {sales=100}
String key = "sales" (present), increment = 10
String key = "costs" (absent), increment = 10

## Expected Output
- computeIfPresent("sales", ...) -> {sales=110}
- computeIfPresent("costs", ...) -> unchanged — no "costs" entry is created

## API Category
Map API

## Hints

### Hint 1
The guard is "key must already exist" — the opposite condition of computeIfAbsent.

### Hint 2
`Map` has a method that runs a remapping function on the value ONLY when the key is present.

### Hint 3
The function receives (key, currentValue) and returns the new value.

## What to investigate in the Java API
- Interface: `java.util.Map`
- Look for: `computeIfPresent(K key, BiFunction<K, V, V> remappingFunction)`
- If the function returns null, the entry is REMOVED

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, Integer> exercise052Imperative(Map<String, Integer> map, String key, int increment) {
    if (map.containsKey(key)) {
        map.put(key, map.get(key) + increment);
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
public static Map<String, Integer> exercise052(Map<String, Integer> map, String key, int increment) {
    map.computeIfPresent(key, (k, v) -> v + increment);
    return map;
}
```

### Edge cases
- Returning null from the function deletes the entry — occasionally useful, often surprising
- computeIfPresent vs merge: merge CREATES the entry when absent; computeIfPresent never does
- Atomic on ConcurrentHashMap — safe for concurrent updates

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Map.computeIfPresent(key, (k, v) -> newValue)
```

### Recognition pattern
- "Update if exists / only touch existing entries"
- computeIfPresent(k, (k, v) -> ...)
- Sibling trio: computeIfAbsent (create), computeIfPresent (update), compute (both)

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement incrementExisting(Map, key, inc) using computeIfPresent
4. Check if you got it right
