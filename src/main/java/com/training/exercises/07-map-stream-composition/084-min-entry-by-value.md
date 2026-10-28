# Exercise 084 — Find min entry by value

## Objective
Reinforce `Stream.min()` on entries — the mirror of Exercise 083.

## Ask
Given a map, find the entry with the lowest value. Return it as `Optional<Map.Entry<String, Integer>>`.

## Input
Map<String, Integer> map = {a=1, b=5, c=3}

## Expected Output
Optional[a=1]

## API Category
Map + Stream Composition

## Hints

### Hint 1
One word differs from Exercise 083: min instead of max.

### Hint 2
`Stream.min(Comparator)` returns the least element as Optional.

### Hint 3
Same comparator: Map.Entry.comparingByValue().

## What to investigate in the Java API
- Interface: `java.util.Map.Entry` — static `comparingByValue()`
- Class: `java.util.stream.Stream` — `min(Comparator)`

## Four Implementation Passes

### 1. Imperative Java
```java
public static Optional<Map.Entry<String, Integer>> exercise084Imperative(Map<String, Integer> map) {
    Map.Entry<String, Integer> best = null;
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
        if (best == null || entry.getValue() < best.getValue()) {
            best = entry;
        }
    }
    return Optional.ofNullable(best);
}
```

### 2. Stream without Collectors
```java
public static Optional<Map.Entry<String, Integer>> exercise084(Map<String, Integer> map) {
    return map.entrySet().stream()
        .min(Map.Entry.comparingByValue());
}
```

### 3. Stream + Collectors
Equivalent via max with reversed comparator:
```java
public static Optional<Map.Entry<String, Integer>> exercise084ViaMax(Map<String, Integer> map) {
    return map.entrySet().stream()
        .max(Map.Entry.<String, Integer>comparingByValue().reversed());
}
```

### 4. Downstream Collector Composition
min entry per group:
```java
public static Map<String, Optional<Map.Entry<String, Integer>>> exercise084PerGroup(
        Map<String, Map<String, Integer>> groups) {
    return groups.entrySet().stream()
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            e -> e.getValue().entrySet().stream()
                    .min(Map.Entry.comparingByValue())
        ));
}
```

### Edge cases
- Empty map -> Optional.empty()
- min(cmp.reversed()) == max(cmp) — the recurring comparator equivalence
- Useful for "cheapest product", "earliest timestamp" lookups

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Map.entrySet().stream().min(Map.Entry.comparingByValue())
```

### Recognition pattern
- "Which key has the smallest value?"
- min vs max: direction only; everything else identical
- Optional unwrapping at the call site

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement minEntry(Map) using entrySet().stream().min()
4. Check if you got it right
