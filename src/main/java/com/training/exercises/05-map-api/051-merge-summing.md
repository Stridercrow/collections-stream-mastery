# Exercise 051 — Map.merge for summing values

## Objective
Reinforce `Map.merge()` for value accumulation beyond counting.

## Ask
Given a map and a key/amount pair, add the amount to the current value for that key (treating absence as 0). Return the map.

## Input
Map<String, Integer> map = {sales=100}
String key = "sales", amount = 50 (existing key)
String key = "costs", amount = 30 (missing key)

## Expected Output
- merge("sales", 50) -> {sales=150}
- merge("costs", 30) -> {sales=150, costs=30}

## API Category
Map API

## Hints

### Hint 1
This is Exercise 050 with a variable amount instead of a fixed 1.

### Hint 2
`merge` takes the new value and a function combining old + new.

### Hint 3
`Integer::sum` combines the existing value with the incoming amount.

## What to investigate in the Java API
- Interface: `java.util.Map`
- Look for: `merge(K key, V value, BiFunction<V, V, V> remappingFunction)`
- Absent key -> the incoming value is inserted as-is

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, Integer> exercise051Imperative(Map<String, Integer> map, String key, int amount) {
    if (map.containsKey(key)) {
        map.put(key, map.get(key) + amount);
    } else {
        map.put(key, amount);
    }
    return map;
}
```

### 2. Stream without Collectors
Not applicable — Map mutation exercise.

### 3. Stream + Collectors
Aggregate a list of (key, amount) pairs declaratively:
```java
public static Map<String, Integer> exercise051Stream(List<Pair> pairs) {
    return pairs.stream()
        .collect(Collectors.toMap(Pair::key, Pair::amount, Integer::sum));
}
```

### 4. Idiomatic Map API
```java
public static Map<String, Integer> exercise051(Map<String, Integer> map, String key, int amount) {
    map.merge(key, amount, Integer::sum);
    return map;
}
```

### Edge cases
- Missing key -> value inserted directly (no NPE, no default needed)
- BigDecimal accumulation: map.merge(key, amount, BigDecimal::add)
- merge's remapping function gets (oldValue, incomingValue) — order matters for non-commutative ops

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Map.merge(key, amount, Integer::sum)
```

### Recognition pattern
- "Add to a running total in a Map"
- merge(k, delta, accumulator) — one line, no null checks
- Counting is the special case delta=1

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement addToTotal(Map, key, amount) using merge
4. Check if you got it right
