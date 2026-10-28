# Exercise 081 — Filter map entries by value

## Objective
Train the entrySet().stream() + toMap pipeline for filtering a map into a new map.

## Ask
Given a map and a threshold, return a new map containing only entries whose value is >= the threshold.

## Input
Map<String, Integer> map = {a=1, b=5, c=3}, minValue = 3

## Expected Output
{b=5, c=3}

## API Category
Map + Stream Composition

## Hints

### Hint 1
You can't filter a Map directly — stream its entries first.

### Hint 2
`entrySet().stream().filter(...)` tests each Map.Entry.

### Hint 3
Collect back with toMap(Entry::getKey, Entry::getValue) to rebuild the map.

## What to investigate in the Java API
- Interface: `java.util.Map` — `entrySet()`
- Interface: `java.util.Map.Entry` — `getKey()`, `getValue()`
- Class: `java.util.stream.Collectors` — `toMap(keyMapper, valueMapper)`

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, Integer> exercise081Imperative(Map<String, Integer> map, int minValue) {
    Map<String, Integer> result = new HashMap<>();
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
        if (entry.getValue() >= minValue) {
            result.put(entry.getKey(), entry.getValue());
        }
    }
    return result;
}
```

### 2. Stream without Collectors
Not applicable — rebuilding a Map requires a collector.

### 3. Stream + Collectors
```java
public static Map<String, Integer> exercise081(Map<String, Integer> map, int minValue) {
    return map.entrySet().stream()
        .filter(e -> e.getValue() >= minValue)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
}
```

### 4. Downstream Collector Composition
Filter a map WHILE building a grouped structure — filtering inside a larger pipeline:
```java
public static Map<Integer, List<String>> exercise081Grouped(Map<String, Integer> map, int minValue) {
    return map.entrySet().stream()
        .filter(e -> e.getValue() >= minValue)
        .collect(Collectors.groupingBy(
            Map.Entry::getValue,
            Collectors.mapping(Map.Entry::getKey, Collectors.toList())
        ));
}
```

### Edge cases
- No entry passes the filter -> empty map
- toMap without merge function throws on duplicate keys — impossible here (keys are unique in the source map)
- Entry order is lost with HashMap; use LinkedHashMap::new as supplier to keep it

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Map.entrySet().stream().filter(...).collect(Collectors.toMap(Entry::getKey, Entry::getValue))
```

### Recognition pattern
- "Filter a map by its values/keys"
- entrySet().stream() -> filter -> toMap — the round-trip idiom
- Key-based filtering: filter on e.getKey() instead

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement filterByValue(Map, min) using entrySet().stream()
4. Check if you got it right
