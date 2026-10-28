# Exercise 106 — Compute with null handling: increment or start at 0

## Objective
Final drill: Map.compute where the lambda must handle the null (absent-key) case explicitly — and note the 0-seed twist versus Exercise 053.

## Ask
Given a map and a key, set the key's value to 0 if absent, or increment it by 1 if present. Return the map.

## Input
Map<String, Integer> map = {a=5}
String key = "a" (present) and key = "b" (absent)

## Expected Output
- compute("a") -> {a=6}
- compute("b") -> {a=6, b=0} — note: starts at ZERO, not one

## API Category
Advanced Map Exercises

## Hints

### Hint 1
`compute` always runs the function; the current value may be NULL.

### Hint 2
Null-check inside the lambda: v == null -> 0, else v + 1.

### Hint 3
This is Exercise 053's shape with a different seed — read the requirement carefully, seeds matter.

## What to investigate in the Java API
- Interface: `java.util.Map` — `compute(K, BiFunction<K, V, V>)`
- The lambda contract: (key, currentValue-or-null) -> newValue

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, Integer> exercise106Imperative(Map<String, Integer> map, String key) {
    if (map.containsKey(key)) {
        map.put(key, map.get(key) + 1);
    } else {
        map.put(key, 0);
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
public static Map<String, Integer> exercise106(Map<String, Integer> map, String key) {
    map.compute(key, (k, v) -> v == null ? 0 : v + 1);
    return map;
}
```

### Edge cases
- v is null for absent keys — the in-lambda null check is MANDATORY
- Seed asymmetry: absent -> 0, present -> +1. Merge can't express this directly (merge applies the same value+combiner to both cases)
- compute returning null REMOVES the entry — not a risk here (lambda never returns null)

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Map.compute(key, (k, v) -> v == null ? seed : transform(v))
```

### Recognition pattern
- "One call must handle create AND update with different seeds"
- compute with in-lambda null check
- The compute/computeIfAbsent/computeIfPresent/merge quartet is now complete — you've trained all four

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement initOrIncrement(Map, key) using compute with the 0-seed twist
4. Check if you got it right — then re-do 046-059 from memory as a final sweep
