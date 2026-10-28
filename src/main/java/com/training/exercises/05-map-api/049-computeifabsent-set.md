# Exercise 049 — Map.computeIfAbsent with Set

## Objective
Reinforce `Map.computeIfAbsent()` with a Set instead of a List — the value type changes nothing.

## Ask
Given a map of key -> set and a key/value pair, add the value to the set for that key, creating the set first if needed. Return the set for the key.

## Input
Map<String, Set<Integer>> map = {a=[1]}
String key = "a", value = 1 (duplicate) and value = 2 (new)

## Expected Output
- add 1 -> {a=[1]}, set stays [1] (Set dedupes)
- add 2 -> {a=[1, 2]}

## API Category
Map API

## Hints

### Hint 1
Same idiom as Exercise 048 — only the collection type differs.

### Hint 2
`computeIfAbsent` takes a Function producing the empty container.

### Hint 3
Use `HashSet::new` as the supplier; the add() semantics of Set apply (no duplicates).

## What to investigate in the Java API
- Interface: `java.util.Map` — `computeIfAbsent(K, Function<K, V>)`
- Class: `java.util.HashSet` — the container being lazily created

## Four Implementation Passes

### 1. Imperative Java
```java
public static Set<Integer> exercise049Imperative(Map<String, Set<Integer>> map, String key, Integer value) {
    if (!map.containsKey(key)) {
        map.put(key, new HashSet<>());
    }
    map.get(key).add(value);
    return map.get(key);
}
```

### 2. Stream without Collectors
Not applicable — Map mutation exercise.

### 3. Stream + Collectors
The declarative equivalent when building the whole map:
```java
public static Map<String, Set<Integer>> exercise049Grouping(List<Pair> pairs) {
    return pairs.stream()
        .collect(Collectors.groupingBy(Pair::key, Collectors.mapping(Pair::value, Collectors.toSet())));
}
```

### 4. Idiomatic Map API
```java
public static <K, V> Set<V> exercise049(Map<K, Set<V>> map, K key, V value) {
    map.computeIfAbsent(key, k -> new HashSet<>()).add(value);
    return map.get(key);
}
```

### Edge cases
- Adding a duplicate to the set is a silent no-op — add() returns false
- Need sorted sets per key? Use TreeSet::new as the supplier
- Same lazy-creation semantics as with List

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Map.computeIfAbsent(key, k -> new HashSet<>()).add(value)
```

### Recognition pattern
- Multi-map with uniqueness per key
- The container type is a parameter of the idiom: ArrayList, HashSet, TreeSet, LinkedHashSet...
- Whole-map declarative alternative: groupingBy + toSet

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement addToSetMap(Map, k, v) using computeIfAbsent + HashSet
4. Check if you got it right
