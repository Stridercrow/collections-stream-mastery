# Exercise 048 — Map.computeIfAbsent with List

## Objective
Train recognition of `Map.computeIfAbsent()` for lazy, per-key list initialization.

## Ask
Given a map of key -> list and a key/value pair, add the value to the list for that key, creating the list first if needed. Return the list for the key.

## Input
Map<String, List<Integer>> map = {a=[1]}
String key = "a" (exists) and key = "b" (missing), value = 2

## Expected Output
- key "a" -> {a=[1, 2]}, returns [1, 2]
- key "b" -> {a=[1], b=[2]}, returns [2]

## API Category
Map API

## Hints

### Hint 1
The classic two-step: check if the key exists, create a list if not, then add.

### Hint 2
`Map` has a method that computes the value from the key ONLY when the key is absent — and stores it.

### Hint 3
It returns the (new or existing) value, so you can chain `.add(value)` right onto it.

## What to investigate in the Java API
- Interface: `java.util.Map`
- Look for: `computeIfAbsent(K key, Function<K, V> mappingFunction)`
- The mapping function runs at most once per absent key; the computed value is stored

## Four Implementation Passes

### 1. Imperative Java
```java
public static List<Integer> exercise048Imperative(Map<String, List<Integer>> map, String key, Integer value) {
    if (!map.containsKey(key)) {
        map.put(key, new ArrayList<>());
    }
    map.get(key).add(value);
    return map.get(key);
}
```

### 2. Stream without Collectors
Not applicable — this is a Map mutation exercise.

### 3. Stream + Collectors
The stream equivalent for building the whole map from a list is groupingBy:
```java
public static Map<String, List<Integer>> exercise048Grouping(List<Pair> pairs) {
    return pairs.stream()
        .collect(Collectors.groupingBy(Pair::key, Collectors.mapping(Pair::value, Collectors.toList())));
}
```

### 4. Idiomatic Map API
```java
public static <K, V> List<V> exercise048(Map<K, List<V>> map, K key, V value) {
    map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
    return map.get(key);
}
```

### Edge cases
- Mapping function must not return null (would leave no mapping)
- The list is created ONLY when needed (lazy) — important for expensive structures
- This single line replaces the entire imperative if/get/add dance

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Map.computeIfAbsent(key, k -> new ArrayList<>()).add(value)
```

### Recognition pattern
- "Group elements into a Map manually" / "multi-map add"
- computeIfAbsent(k, k -> new ArrayList<>()) — THE idiom to burn into memory
- Building the whole map in one pass from a stream? groupingBy is the declarative equivalent

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement addToMultiMap(Map, k, v) using computeIfAbsent
4. Check if you got it right
