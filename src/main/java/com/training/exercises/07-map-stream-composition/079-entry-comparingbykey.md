# Exercise 079 — Map.Entry.comparingByKey: sort entries by key

## Objective
Train recognition of `Map.Entry.comparingByKey()` for streaming over a map's entries in key order.

## Ask
Given a map, return its entries as a list sorted by key (natural String order).

## Input
Map<String, Integer> map = {banana=2, apple=5, cherry=1}

## Expected Output
[apple=5, banana=2, cherry=1]

## API Category
Map + Stream Composition

## Hints

### Hint 1
Maps don't sort — but their entrySet() can be streamed and sorted.

### Hint 2
`Map.Entry` has static comparator factories: comparingByKey() and comparingByValue().

### Hint 3
entrySet().stream().sorted(Map.Entry.comparingByKey()).toList() — the whole idiom.

## What to investigate in the Java API
- Interface: `java.util.Map.Entry`
- Look for static methods:
  - `comparingByKey()` — requires K to be Comparable
  - `comparingByKey(Comparator)` — custom key order
  - `comparingByValue()` / `comparingByValue(Comparator)`

## Four Implementation Passes

### 1. Imperative Java
```java
public static List<Map.Entry<String, Integer>> exercise079Imperative(Map<String, Integer> map) {
    List<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());
    entries.sort((a, b) -> a.getKey().compareTo(b.getKey()));
    return entries;
}
```

### 2. Stream without Collectors
```java
public static List<Map.Entry<String, Integer>> exercise079(Map<String, Integer> map) {
    return map.entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .toList();
}
```

### 3. Stream + Collectors
```java
public static Map<String, Integer> exercise079SortedMap(Map<String, Integer> map) {
    return map.entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .collect(Collectors.toMap(
            Map.Entry::getKey, Map.Entry::getValue,
            (a, b) -> a, LinkedHashMap::new
        ));
}
```

### 4. Downstream Collector Composition
Not applicable — sorting entries is a stream pipeline, not a downstream.

### Edge cases
- Result is a LIST of entries, not a Map — that's usually what you want for iteration
- HashMap has no order; sorting the entry stream is the fix
- comparingByKey() needs Comparable keys; otherwise pass a Comparator

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Map.entrySet().stream().sorted(Map.Entry.comparingByKey()).toList()
```

### Recognition pattern
- "Iterate a map in sorted order"
- entrySet().stream() + Map.Entry.comparingByKey/ByValue
- Sorted map permanently? TreeMap at construction; sorted view on demand? stream + sort

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement sortedByKey(Map) using entrySet().stream().sorted()
4. Check if you got it right
