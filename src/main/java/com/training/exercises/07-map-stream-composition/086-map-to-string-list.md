# Exercise 086 — Convert map entries to formatted strings

## Objective
Train entry-to-string projection: entrySet().stream().map(...).toList().

## Ask
Given a map, return a list of strings in "key=value" format, one per entry.

## Input
Map<String, Integer> map = {a=1, b=2}

## Expected Output
["a=1", "b=2"]

## API Category
Map + Stream Composition

## Hints

### Hint 1
The output type changes: Map -> List<String>.

### Hint 2
Stream the entries, map each to a formatted string.

### Hint 3
String concatenation with getKey() and getValue() builds each line.

## What to investigate in the Java API
- Interface: `java.util.Map` — `entrySet()`
- Interface: `java.util.Map.Entry` — `getKey()`, `getValue()`
- Class: `java.util.stream.Stream` — `map(Function)`, `toList()`

## Four Implementation Passes

### 1. Imperative Java
```java
public static List<String> exercise086Imperative(Map<String, Integer> map) {
    List<String> result = new ArrayList<>();
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
        result.add(entry.getKey() + "=" + entry.getValue());
    }
    return result;
}
```

### 2. Stream without Collectors
```java
public static List<String> exercise086(Map<String, Integer> map) {
    return map.entrySet().stream()
        .map(e -> e.getKey() + "=" + e.getValue())
        .toList();
}
```

### 3. Stream + Collectors
Join directly into ONE string — the debugging classic:
```java
public static String exercise086Joined(Map<String, Integer> map) {
    return map.entrySet().stream()
        .map(e -> e.getKey() + "=" + e.getValue())
        .collect(Collectors.joining(", ", "{", "}"));
}
```

### 4. Downstream Collector Composition
Not applicable — projection to strings is a plain pipeline.

### Edge cases
- HashMap iteration order is unspecified — sort the entries first if the output order matters
- Null values render as "key=null" via concatenation
- joining(", ", "{", "}") prints map-like output for logs

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Map.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).toList()
```

### Recognition pattern
- "Print / log / format a map"
- map() each entry to a String — shape change Map -> List<String>
- Pair with joining() for one-line output

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement formatEntries(Map) using entrySet().stream().map()
4. Check if you got it right
