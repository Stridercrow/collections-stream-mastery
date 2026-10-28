# Exercise 080 — Map.Entry.comparingByValue: sort entries by value

## Objective
Train recognition of `Map.Entry.comparingByValue()` — the "rank by value" idiom.

## Ask
Given a map, return its entries as a list sorted by value ascending.

## Input
Map<String, Integer> map = {banana=2, apple=5, cherry=1}

## Expected Output
[cherry=1, banana=2, apple=5]

## API Category
Map + Stream Composition

## Hints

### Hint 1
Identical skeleton to Exercise 079 — one word changes: ByValue.

### Hint 2
`Map.Entry.comparingByValue()` compares V (must be Comparable).

### Hint 3
For "top N by value", append `.reversed().limit(n)`.

## What to investigate in the Java API
- Interface: `java.util.Map.Entry`
- Look for: static `comparingByValue()` and `comparingByValue(Comparator)`

## Four Implementation Passes

### 1. Imperative Java
```java
public static List<Map.Entry<String, Integer>> exercise080Imperative(Map<String, Integer> map) {
    List<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());
    entries.sort((a, b) -> a.getValue().compareTo(b.getValue()));
    return entries;
}
```

### 2. Stream without Collectors
```java
public static List<Map.Entry<String, Integer>> exercise080(Map<String, Integer> map) {
    return map.entrySet().stream()
        .sorted(Map.Entry.comparingByValue())
        .toList();
}
```

### 3. Stream + Collectors
Value-sorted LinkedHashMap result:
```java
public static Map<String, Integer> exercise080SortedMap(Map<String, Integer> map) {
    return map.entrySet().stream()
        .sorted(Map.Entry.comparingByValue())
        .collect(Collectors.toMap(
            Map.Entry::getKey, Map.Entry::getValue,
            (a, b) -> a, LinkedHashMap::new
        ));
}
```

### 4. Downstream Collector Composition
Not applicable — entry sorting is a stream pipeline.

### Edge cases
- Ties in value: order among them is the encounter order (stable sort)
- Null values break natural-order comparison — guard with a nullsFirst comparator if needed
- This is THE pattern behind "top 10 customers by spend"

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Map.entrySet().stream().sorted(Map.Entry.comparingByValue()).toList()
```

### Recognition pattern
- "Rank / sort map by its values"
- Map.Entry.comparingByValue() — static factory on the Entry interface
- Paired with .reversed() and .limit() for leaderboards

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement sortedByValue(Map) using entrySet().stream().sorted()
4. Check if you got it right
