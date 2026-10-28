### docs/MAP_API_CHEATSHEET.md
```markdown
# Map API Cheatsheet

## Decision Table

| Requirement | API | Example |
|-------------|-----|---------|
| Get value, with fallback | `getOrDefault(key, default)` | `map.getOrDefault("key", "default")` |
| Insert only if absent | `putIfAbsent(key, value)` | `map.putIfAbsent("key", value)` |
| Initialize a List for key | `computeIfAbsent(key, k -> new ArrayList<>())` | `map.computeIfAbsent("key", k -> new ArrayList<>())` |
| Update only if present | `computeIfPresent(key, (k,v) -> v + 1)` | `map.computeIfPresent("key", (k,v) -> v + 1)` |
| Compute from key and current | `compute(key, (k,v) -> v == null ? 1 : v + 1)` | `map.compute("key", (k,v) -> v == null ? 1 : v + 1)` |
| Accumulate values | `merge(key, value, (a,b) -> a + b)` | `map.merge("key", 1, Integer::sum)` |
| Replace existing value | `replace(key, newValue)` | `map.replace("key", "new")` |
| Replace all values | `replaceAll((k,v) -> v * 2)` | `map.replaceAll((k,v) -> v * 2)` |
| Remove if present | `remove(key)` | `map.remove("key")` |

## Map Methods by Purpose

### Read Operations

| Method | Description |
|--------|-------------|
| `get(key)` | Returns value or null |
| `getOrDefault(key, default)` | Returns value or default |
| `containsKey(key)` | Checks if key exists |
| `containsValue(value)` | Checks if value exists |

### Write Operations

| Method | Description |
|--------|-------------|
| `put(key, value)` | Associates value with key (overwrites) |
| `putIfAbsent(key, value)` | Associates if not already present |
| `putAll(Map)` | Copies all entries from another map |

### Compute Operations

| Method | Description |
|--------|-------------|
| `compute(key, remappingFunction)` | Computes value from key and current value |
| `computeIfAbsent(key, mappingFunction)` | Computes value only if key absent |
| `computeIfPresent(key, remappingFunction)` | Computes value only if key present |
| `merge(key, value, remappingFunction)` | Merges new value with existing |

### Bulk Operations

| Method | Description |
|--------|-------------|
| `replaceAll(function)` | Transforms all values |
| `forEach(action)` | Iterates over all entries |

### Remove Operations

| Method | Description |
|--------|-------------|
| `remove(key)` | Removes entry by key |
| `remove(key, value)` | Removes only if key maps to value |

## Common Patterns

### Pattern 1: List of values by key
```java
Map<String, List<Employee>> deptMap = new HashMap<>();
for (Employee emp : employees) {
    deptMap.computeIfAbsent(emp.department(), k -> new ArrayList<>()).add(emp);
}
```
### Pattern 2: Frequency counter
```java
Map<String,Integer> wordCounts = new HashMap<>();
for (String word : words) {
    wordCounts.merge(word, 1, Integer::sum);
}
```

### Pattern 3: Summings totals by key
```java
Map<String, BigDecimal> depTotals = new HashMap<>();
for(Employee emp : employees) {
    depTotals.merge(emp.department(), emp.salary(), BigDecimal::add);
}
```

### Pattern 4: Update if exists
```java
Map<String, Integer> counts = new HashMap<>();
counts.put("active", 10);
counts.computeIfPresent("active", (k,v) -> v + 1);
```

### Pattern 5: Compute from scratch
```java
Map<String, Integer> cache = new HashMap<>();
cache.compute("expensive", (k,v) -> v == null ? computeExpensive(k) : v)
```

### Pattern 6 Transform all values
```java
Map<String, String> map = new HashMap<>();
map.put("a", "1");
map.put("b", "2");
map.replaceAll((k, v) -> v + " items");
```

### Pattern 7 EntrySet operations
```java
map.entrySet().stream()
    .sorted(Map.Entry.comparingByValue())
    .limit(10)
    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
```

### Map Implementation Selection


Implementation	Characteristics	Use When
HashMap	Unordered, O(1)	Default choice
LinkedHashMap	Insertion-order	Need predictable iteration
TreeMap	Sorted by key	Need sorted keys
ConcurrentHashMap	Thread-safe	Multi-threaded access

### Creating with specific implementation

groupingBy(Employee::department, TreeMap::new, toList())
toMap(keyMapper, valueMapper, mergeFunction, TreeMap::new)

### Quick Recognition

Problem Statement	API
"Add to list only if key exists"	computeIfAbsent()
"Increment counter"	merge()
"Add values to existing"	merge()
"Double all values"	replaceAll()
"Update only if key exists"	computeIfPresent()
"Compute based on key"	compute()


