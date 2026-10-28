# Exercise 050 — Map.merge for counting words

## Objective
Train recognition of `Map.merge()` as the counter accumulator.

## Ask
Given a list of words, return a `Map<String, Integer>` with the number of occurrences of each word.

## Input
List<String> words = [java, stream, java, map, java]

## Expected Output
{java=3, stream=1, map=1}

## API Category
Map API

## Hints

### Hint 1
For each word: if not in the map, count starts at 1; otherwise add 1.

### Hint 2
`Map` has a method that inserts a value if absent, or combines it with the existing value via a function.

### Hint 3
`Integer::sum` is the combining function; the seed for counting is 1.

## What to investigate in the Java API
- Interface: `java.util.Map`
- Look for: `merge(K key, V value, BiFunction<V, V, V> remappingFunction)`
- Semantics: if key absent (or null), put(value); else put(remapping(oldValue, value))

## Four Implementation Passes

### 1. Imperative Java (pre-Java 8 style)
```java
public static Map<String, Integer> exercise050Classic(List<String> words) {
    Map<String, Integer> counts = new HashMap<>();
    for (String word : words) {
        Integer count = counts.get(word);
        if (count == null) {
            counts.put(word, 1);
        } else {
            counts.put(word, count + 1);
        }
    }
    return counts;
}
```

### 2. Stream without Collectors
Not applicable — accumulative Map building is merge/collector territory.

### 3. Stream + Collectors
The stream equivalent — compare both until they feel identical:
```java
public static Map<String, Long> exercise050Stream(List<String> words) {
    return words.stream()
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
}
```

### 4. Idiomatic Map API
```java
public static Map<String, Integer> exercise050(List<String> words) {
    Map<String, Integer> counts = new HashMap<>();
    for (String word : words) {
        counts.merge(word, 1, Integer::sum);
    }
    return counts;
}
```

### Edge cases
- Empty list -> empty map
- merge with a null value REMOVES the mapping (unusual semantics — know it exists)
- counting() returns Long; merge gives you the numeric type you choose

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Map.merge(key, 1, Integer::sum)                                  // imperative accumulation
Collectors.groupingBy(Function.identity(), Collectors.counting()) // stream equivalent
```

### Recognition pattern
- Frequency counting, accumulating into a Map
- merge(k, seed, combiner) — the imperative workhorse
- groupingBy + counting() — the declarative twin
- Both belong in muscle memory; pick per context

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement wordCount(List<String>) BOTH with merge() and with groupingBy+counting()
4. Check if you got it right
