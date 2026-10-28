# Exercise 105 — Update existing entries with computeIfPresent: specific key

## Objective
Repetition drill: guarded increment of a FIXED key ("active") with computeIfPresent.

## Ask
Given a map, increment the value of the key "active" by 1 — but ONLY if that key exists. Return the map.

## Input
Map<String, Integer> map = {active=10, idle=3}

## Expected Output
{active=11, idle=3}

## API Category
Advanced Map Exercises

## Hints

### Hint 1
The guard: update only when the key is already present.

### Hint 2
`computeIfPresent(key, remapping)` never creates the entry.

### Hint 3
The remapping lambda receives (key, currentValue) — return currentValue + 1.

## What to investigate in the Java API
- Interface: `java.util.Map` — `computeIfPresent(K, BiFunction<K, V, V>)`

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, Integer> exercise105Imperative(Map<String, Integer> map) {
    if (map.containsKey("active")) {
        map.put("active", map.get("active") + 1);
    }
    return map;
}
```

### 2. Stream without Collectors
Not applicable — in-place Map mutation.

### 3. Stream + Collectors
Not applicable — no stream involved.

### 4. Idiomatic Map API
```java
public static Map<String, Integer> exercise105(Map<String, Integer> map) {
    map.computeIfPresent("active", (k, v) -> v + 1);
    return map;
}
```

### Edge cases
- Key absent -> NO entry created, map unchanged (the defining behavior)
- What if you'd WANT creation on miss? merge("active", 1, Integer::sum)
- Atomic on ConcurrentHashMap — safe counter increment under concurrency

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Map.computeIfPresent(key, (k, v) -> v + 1)
```

### Recognition pattern
- "Increment existing counter, ignore missing keys"
- computeIfPresent — guarded update
- Repetition of Exercise 052 with a fixed key — recall speed is the goal

## Repetition Challenge
THIS exercise IS the repetition challenge:
1. Close the file (and Exercise 052)
2. Open a scratch file
3. Implement bumpActive(Map) with computeIfPresent, from memory
4. Verify against exercise105 in MasterySolutions.java
