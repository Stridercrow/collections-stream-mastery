# API Discovery Map

## The Decision Tree

When you see a requirement, follow this mental decision tree:

Requirement → What data shape?
├── Primitive numeric array (int[], long[], double[])
│ └── Arrays.stream().{sum, average, min, max, summaryStats}
│
├── Collection of objects → Need to aggregate?
│ ├── Yes → Need to group?
│ │ ├── Yes → groupingBy()
│ │ │ ├── Need count? → groupingBy(..., counting())
│ │ │ ├── Need sum? → groupingBy(..., summingInt())
│ │ │ ├── Need transform objects? → groupingBy(..., mapping())
│ │ │ └── Need filter? → groupingBy(..., filtering())
│ │ │
│ │ └── No → toList(), toSet(), toMap()
│ │
│ └── No → filter(), map(), flatMap(), sorted(), etc.
│
└── Map → Need to mutate?
├── Yes → Map API
│ ├── Initialize if absent → computeIfAbsent()
│ ├── Update if present → computeIfPresent()
│ ├── Accumulate/merge → merge()
│ ├── Transform all values → replaceAll()
│ └── Compute based on key → compute()
│
└── No → entrySet().stream()
├── Sort by key → comparingByKey()
├── Sort by value → comparingByValue()
├── Max/min → max()/min()
└── Filter/transform → filter()/map()


## API Recognition Patterns

### Pattern 1: "I need to sum int[]"
**Recognition**: `int[]` → `Arrays.stream(arr).sum()`

### Pattern 2: "I need to group by property"
**Recognition**: Output `Map<K,List<V>>` → `groupingBy()`

### Pattern 3: "I need counts per group"
**Recognition**: Output `Map<K,Long>` → `groupingBy(..., counting())`

### Pattern 4: "I need to initialize a Map List"
**Recognition**: "if key missing, create list" → `computeIfAbsent()`

### Pattern 5: "I need to accumulate into Map"
**Recognition**: "add to existing or create new" → `merge()`

### Pattern 6: "I need to transform Map values"
**Recognition**: "change all values" → `replaceAll()`

### Pattern 7: "I need numeric stats on primitive"
**Recognition**: `IntStream.summaryStatistics()`

### Pattern 8: "I need to split into two groups"
**Recognition**: Boolean keys → `partitioningBy()`

### Pattern 9: "I need to flatten nested collections"
**Recognition**: List inside object → `flatMap()` or `flatMapping()`

### Pattern 10: "I need Optional result"
**Recognition**: May not exist → `findFirst()`, `findAny()`, `min()`, `max()`

## API Families Quick Reference

| Operation | Class | Key Methods |
|-----------|-------|-------------|
| Primitive arrays | `Arrays` | `stream()`, `sort()` |
| Primitive streams | `IntStream`, `LongStream`, `DoubleStream` | `sum()`, `average()`, `boxed()` |
| Object streams | `Stream` | `filter()`, `map()`, `flatMap()` |
| Collectors | `Collectors` | `toList()`, `toMap()`, `groupingBy()` |
| Map mutation | `Map` | `computeIfAbsent()`, `merge()`, `replaceAll()` |
| Optional | `Optional` | `map()`, `filter()`, `orElse()` |
| Entry sorting | `Map.Entry` | `comparingByKey()`, `comparingByValue()` |