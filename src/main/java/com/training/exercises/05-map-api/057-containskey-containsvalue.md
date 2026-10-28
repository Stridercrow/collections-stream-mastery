# Exercise 057 — Map.containsKey and Map.containsValue

## Objective
Train recognition of the two membership probes of Map and their performance characteristics.

## Ask
Given a map, a key, and a value, return true only if the map contains BOTH that key AND that value.

## Input
Map<String, String> map = {a=1, b=2}
key = "a", value = "1" -> true
key = "a", value = "2" -> false (value belongs to another key)
key = "z", value = "1" -> false (key missing)

## Expected Output
true or false

## API Category
Map API

## Hints

### Hint 1
Map answers two different membership questions: about keys and about values.

### Hint 2
Both methods are boolean-returning and cheap to call — but their costs differ.

### Hint 3
Key lookup is O(1) on hashed maps; value lookup scans ALL entries.

## What to investigate in the Java API
- Interface: `java.util.Map`
- Look for:
  - `containsKey(Object key)` — hash lookup, O(1) on HashMap
  - `containsValue(Object value)` — full scan, O(n)

## Four Implementation Passes

### 1. Imperative Java (manual value scan, for understanding)
```java
public static boolean exercise057ManualValueCheck(Map<String, String> map, String key, String value) {
    if (!map.containsKey(key)) {
        return false;
    }
    for (String v : map.values()) {
        if (v.equals(value)) {
            return map.get(key).equals(value);
        }
    }
    return false;
}
```

### 2. Stream without Collectors (alternative value check)
```java
public static boolean exercise057Stream(Map<String, String> map, String key, String value) {
    return key != null
        && value != null
        && value.equals(map.get(key));
}
```

### 3. Stream + Collectors
Not applicable — membership probing is a direct Map read.

### 4. Idiomatic Map API
```java
public static boolean exercise057(Map<String, String> map, String key, String value) {
    return map.containsKey(key) && map.containsValue(value);
}
```

### Edge cases
- containsKey(null) and containsValue(null) are allowed on HashMap, forbidden on TreeMap/Hashtable
- containsValue is O(n) — in hot paths, invert the map or keep a second index
- "Contains key AND value" is stricter than either alone: the value must exist SOMEWHERE, the key must exist too

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Map.containsKey(key) / Map.containsValue(value)
```

### Recognition pattern
- "Does it have this key?" -> containsKey (fast)
- "Does it have this value anywhere?" -> containsValue (linear scan)
- Both return boolean, neither mutates

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement hasKeyAndValue(Map, key, value) using containsKey + containsValue
4. Check if you got it right
