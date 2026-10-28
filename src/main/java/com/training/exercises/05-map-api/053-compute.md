# Exercise 053 — Map.compute: handle both absent and present

## Objective
Train recognition of `Map.compute()` — the unconditional remapping function.

## Ask
Given a map and a key, increment the value for that key; if the key is absent, set it to 1. Return the map.

## Input
Map<String, Integer> map = {a=5}
String key = "a" (present) and key = "b" (absent)

## Expected Output
- compute("a") -> {a=6}
- compute("b") -> {a=6, b=1}

## API Category
Map API

## Hints

### Hint 1
Unlike computeIfAbsent/computeIfPresent, this method always runs the function.

### Hint 2
The function receives (key, currentValue) — where currentValue may be null.

### Hint 3
Handle the null case inside the function: null -> 1, otherwise value + 1.

## What to investigate in the Java API
- Interface: `java.util.Map`
- Look for: `compute(K key, BiFunction<K, V, V> remappingFunction)`
- Returning null from the function removes the entry

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, Integer> exercise053Imperative(Map<String, Integer> map, String key) {
    if (map.containsKey(key)) {
        map.put(key, map.get(key) + 1);
    } else {
        map.put(key, 1);
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
public static Map<String, Integer> exercise053(Map<String, Integer> map, String key) {
    map.compute(key, (k, v) -> v == null ? 1 : v + 1);
    return map;
}
```

### Edge cases
- v is null when the key is absent — null-check inside the lambda is mandatory
- compute(k, (k, v) -> null) removes the key
- merge(key, 1, Integer::sum) achieves the same result more concisely — knowing when to prefer which is the real skill

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Map.compute(key, (k, v) -> v == null ? seed : transform(v))
```

### Recognition pattern
- "One function must handle both create and update"
- compute(k, (k, v) -> ...) with an in-lambda null check
- If create and update logic differ cleanly, prefer computeIfAbsent / computeIfPresent
- If logic is "combine with a seed", merge is shorter

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement incrementOrCreate(Map, key) using compute, then rewrite it with merge
4. Check if you got it right
