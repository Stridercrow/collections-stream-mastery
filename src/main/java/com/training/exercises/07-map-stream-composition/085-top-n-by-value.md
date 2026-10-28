# Exercise 085 — Sort entries by value descending and limit: top N

## Objective
Train the leaderboard pattern: sorted + reversed + limit on map entries.

## Ask
Given a map and a limit n, return the top n entries by value, highest first.

## Input
Map<String, Integer> map = {a=1, b=5, c=3, d=9, e=2}, limit = 3

## Expected Output
[d=9, b=5, c=3]

## API Category
Map + Stream Composition

## Hints

### Hint 1
Combine Exercise 080's sorting with Exercise 009's limiting.

### Hint 2
Descending order needs `.reversed()` on the value comparator.

### Hint 3
limit(n) AFTER sorting keeps the n best.

## What to investigate in the Java API
- Interface: `java.util.Map.Entry` — `comparingByValue()`
- Class: `java.util.Comparator` — `reversed()`
- Class: `java.util.stream.Stream` — `limit(long)`

## Four Implementation Passes

### 1. Imperative Java
```java
public static List<Map.Entry<String, Integer>> exercise085Imperative(Map<String, Integer> map, int limit) {
    List<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());
    entries.sort((a, b) -> b.getValue().compareTo(a.getValue()));
    return entries.subList(0, Math.min(limit, entries.size()));
}
```

### 2. Stream without Collectors
```java
public static List<Map.Entry<String, Integer>> exercise085(Map<String, Integer> map, int limit) {
    return map.entrySet().stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
        .limit(limit)
        .toList();
}
```

### 3. Stream + Collectors
Top N as an ordered LinkedHashMap:
```java
public static Map<String, Integer> exercise085TopMap(Map<String, Integer> map, int limit) {
    return map.entrySet().stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
        .limit(limit)
        .collect(Collectors.toMap(
            Map.Entry::getKey, Map.Entry::getValue,
            (a, b) -> a, LinkedHashMap::new
        ));
}
```

### 4. Downstream Collector Composition
Not applicable — ranking is a stream pipeline.

### Edge cases
- limit larger than map size -> all entries, still sorted
- limit(0) -> empty list
- The explicit type witness Map.Entry.<String, Integer>comparingByValue() is needed when chaining .reversed() — javac can't infer otherwise

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Map.entrySet().stream()
   .sorted(Map.Entry.<K, V>comparingByValue().reversed())
   .limit(n)
   .toList()
```

### Recognition pattern
- "Top N / leaderboard / most frequent"
- sorted(reversed) + limit — the canonical ranking pipeline
- Type witness needed for the generic comparator chain

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement topN(Map, n) using sorted + reversed + limit
4. Check if you got it right
