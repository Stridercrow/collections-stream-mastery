# Exercise 102 — Build frequency map with merge

## Objective
Repetition drill: the classic word-frequency accumulator with Map.merge.

## Ask
Given a list of words, build a `Map<String, Integer>` counting occurrences of each word using a loop and `Map.merge` (NOT groupingBy).

## Input
List<String> words = [java, stream, java, api, java]

## Expected Output
{java=3, stream=1, api=1}

## API Category
Advanced Map Exercises

## Hints

### Hint 1
For each word: absent -> 1, present -> old + 1.

### Hint 2
`Map.merge(key, value, remapping)` handles both cases in one call.

### Hint 3
Seed value 1, combiner Integer::sum.

## What to investigate in the Java API
- Interface: `java.util.Map` — `merge(K, V, BiFunction<V, V, V>)`

## Four Implementation Passes

### 1. Imperative Java (with the idiom)
```java
public static Map<String, Integer> exercise102(List<String> words) {
    Map<String, Integer> freq = new HashMap<>();
    for (String word : words) {
        freq.merge(word, 1, Integer::sum);
    }
    return freq;
}
```

### 2. Stream without Collectors
forEach variant — same semantics:
```java
public static Map<String, Integer> exercise102ForEach(List<String> words) {
    Map<String, Integer> freq = new HashMap<>();
    words.forEach(w -> freq.merge(w, 1, Integer::sum));
    return freq;
}
```

### 3. Stream + Collectors (declarative twin)
```java
public static Map<String, Long> exercise102Grouping(List<String> words) {
    return words.stream()
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
}
```

### 4. Downstream Collector Composition
Top-3 most frequent words — merge map + ranking pipeline from Exercise 085:
```java
public static List<Map.Entry<String, Integer>> exercise102Top3(List<String> words) {
    Map<String, Integer> freq = exercise102(words);
    return freq.entrySet().stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
        .limit(3)
        .toList();
}
```

### Edge cases
- Empty list -> empty map
- merge removes the entry if the function returns null — don't return null from counters
- merge vs computeIfAbsent: merge ALWAYS combines (or inserts); computeIfAbsent never combines

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Map.merge(key, 1, Integer::sum)
```

### Recognition pattern
- "Count occurrences" in an imperative context
- merge(k, 1, Integer::sum) — burned-in muscle memory
- Declarative twin: groupingBy(identity, counting())

## Repetition Challenge
THIS exercise IS the repetition challenge:
1. Close the file (and Exercise 050)
2. Open a scratch file
3. Implement wordFreq(List<String>) with merge, from memory
4. Verify against exercise102 in MasterySolutions.java
