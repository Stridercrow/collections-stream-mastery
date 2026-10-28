# Exercise 058 — Map.entrySet iteration: invert a map

## Objective
Train recognition of `entrySet()` as the entry point for streaming over map entries.

## Ask
Given a map, return a NEW map with keys and values swapped (an inverted map).

## Input
Map<String, String> map = {a=1, b=2}

## Expected Output
{1=a, 2=b}

## API Category
Map API

## Hints

### Hint 1
You need both the key and the value of each entry at the same time.

### Hint 2
`Map.entrySet()` yields `Map.Entry` objects exposing getKey() and getValue().

### Hint 3
The loop body is a single put(result, value, key).

## What to investigate in the Java API
- Interface: `java.util.Map`
- Look for:
  - `entrySet()` — Set<Map.Entry<K, V>>
  - `Map.Entry.getKey()`, `Map.Entry.getValue()`
- Note: keySet() + get(k) works too but does a second lookup per entry — entrySet() is preferred

## Four Implementation Passes

### 1. Imperative Java
```java
public static Map<String, String> exercise058Imperative(Map<String, String> map) {
    Map<String, String> result = new LinkedHashMap<>();
    for (Map.Entry<String, String> entry : map.entrySet()) {
        result.put(entry.getValue(), entry.getKey());
    }
    return result;
}
```

### 2. Stream without Collectors
Not applicable — building a Map needs a collector.

### 3. Stream + Collectors
```java
public static Map<String, String> exercise058Stream(Map<String, String> map) {
    return map.entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
}
```

### 4. Downstream Collector Composition
Guard against duplicate values when inverting (two keys mapping to the same value would throw):
```java
public static Map<String, String> exercise058Safe(Map<String, String> map) {
    return map.entrySet().stream()
        .collect(Collectors.toMap(
            Map.Entry::getValue,
            Map.Entry::getKey,
            (existing, replacement) -> existing
        ));
}
```

### Edge cases
- Duplicate VALUES become duplicate KEYS after inversion -> IllegalStateException without a merge function
- LinkedHashMap preserves original iteration order; HashMap does not
- entrySet() iteration order on HashMap is unspecified

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Map.entrySet().stream().collect(Collectors.toMap(Entry::getValue, Entry::getKey))
```

### Recognition pattern
- "Process entries" (key + value together)
- entrySet() for iteration and streaming — never keySet() + get() in loops
- Map.Entry::getKey / getValue method references
- Inversion = swap the toMap arguments

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement invertMap(Map) with entrySet loop AND with entrySet().stream()
4. Check if you got it right
