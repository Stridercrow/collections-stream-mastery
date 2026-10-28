# Exercise 083 — Find max entry by value

## Objective
Train `Stream.max()` on an ENTRY stream using Map.Entry.comparingByValue().

## Ask
Given a map, find the entry with the highest value. Return it as `Optional<Map.Entry<String, Integer>>`.

## Input
Map<String, Integer> map = {a=1, b=5, c=3}

## Expected Output
Optional[b=5]

## API Category
Map + Stream Composition

## Hints

### Hint 1
Don't loop and track a max — stream the entries and reduce to the extreme.

### Hint 2
`Stream` has max/min terminal operations taking a Comparator.

### Hint 3
The comparator factory is Map.Entry.comparingByValue() — exactly what Exercise 080 used for sorting.

## What to investigate in the Java API
- Interface: `java.util.Map.Entry` — static `comparingByValue()`
- Class: `java.util.stream.Stream` — `max(Comparator)`, `min(Comparator)` returning Optional

## Four Implementation Passes

### 1. Imperative Java
```java
public static Optional<Map.Entry<String, Integer>> exercise083Imperative(Map<String, Integer> map) {
    Map.Entry<String, Integer> best = null;
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
        if (best == null || entry.getValue() > best.getValue()) {
            best = entry;
        }
    }
    return Optional.ofNullable(best);
}
```

### 2. Stream without Collectors
```java
public static Optional<Map.Entry<String, Integer>> exercise083(Map<String, Integer> map) {
    return map.entrySet().stream()
        .max(Map.Entry.comparingByValue());
}
```

### 3. Stream + Collectors
Collector alternative — maxBy inside collectingAndThen:
```java
public static Optional<Map.Entry<String, Integer>> exercise083Collect(Map<String, Integer> map) {
    return map.entrySet().stream()
        .collect(Collectors.collectingAndThen(
            Collectors.maxBy(Map.Entry.comparingByValue()),
            Optional::of
        ));
}
```

### 4. Downstream Collector Composition
The same machinery per GROUP — max entry per category:
```java
public static Map<String, Optional<Map.Entry<String, Integer>>> exercise083PerGroup(
        Map<String, Map<String, Integer>> groups) {
    return groups.entrySet().stream()
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            e -> e.getValue().entrySet().stream()
                    .max(Map.Entry.comparingByValue())
        ));
}
```

### Edge cases
- Empty map -> Optional.empty()
- Ties: any one of the tied entries — don't rely on which
- Returns the whole ENTRY, so you still have both key and value

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Map.entrySet().stream().max(Map.Entry.comparingByValue())
```

### Recognition pattern
- "Which key has the biggest value?" — the ranked-#1 question
- entrySet().stream().max(comparingByValue()) — no loop, one line
- Optional return forces empty-map handling

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement maxEntry(Map) using entrySet().stream().max()
4. Check if you got it right
