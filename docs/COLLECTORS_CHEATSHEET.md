# Collectors Cheatsheet

## Organization by Output Type

### Produces a Collection

| Method | Return Type | Description |
|--------|-------------|-------------|
| `toList()` | `List<T>` | Collects to a list |
| `toUnmodifiableList()` | `List<T>` | Collects to unmodifiable list |
| `toSet()` | `Set<T>` | Collects to a set |
| `toUnmodifiableSet()` | `Set<T>` | Collects to unmodifiable set |
| `toCollection(Supplier)` | `Collection<T>` | Collects to specified collection type |

### Produces a Map

| Method | Return Type | Description |
|--------|-------------|-------------|
| `toMap(key, value)` | `Map<K,V>` | Simple key-value mapping |
| `toMap(key, value, merge)` | `Map<K,V>` | Handles duplicate keys |
| `toMap(key, value, merge, supplier)` | `Map<K,V>` | With map implementation choice |
| `toUnmodifiableMap(key, value)` | `Map<K,V>` | Unmodifiable map |
| `toConcurrentMap(key, value)` | `Map<K,V>` | Concurrent map |

### Produces a String

| Method | Return Type | Description |
|--------|-------------|-------------|
| `joining()` | `String` | Concatenates strings |
| `joining(delimiter)` | `String` | With delimiter |
| `joining(delimiter, prefix, suffix)` | `String` | With prefix and suffix |

### Produces a Numeric Value

| Method | Return Type | Description |
|--------|-------------|-------------|
| `counting()` | `Long` | Counts elements |
| `summingInt()` | `Integer` | Sums int values |
| `summingLong()` | `Long` | Sums long values |
| `summingDouble()` | `Double` | Sums double values |
| `averagingInt()` | `Double` | Average of int values |
| `averagingLong()` | `Double` | Average of long values |
| `averagingDouble()` | `Double` | Average of double values |

### Produces Statistics

| Method | Return Type | Description |
|--------|-------------|-------------|
| `summarizingInt()` | `IntSummaryStatistics` | Count, sum, min, avg, max |
| `summarizingLong()` | `LongSummaryStatistics` | Count, sum, min, avg, max |
| `summarizingDouble()` | `DoubleSummaryStatistics` | Count, sum, min, avg, max |

### Produces an Element

| Method | Return Type | Description |
|--------|-------------|-------------|
| `minBy(comparator)` | `Optional<T>` | Minimum element |
| `maxBy(comparator)` | `Optional<T>` | Maximum element |

### Transformations

| Method | Return Type | Description |
|--------|-------------|-------------|
| `mapping(mapper, downstream)` | `Collector` | Applies function then collects |
| `filtering(predicate, downstream)` | `Collector` | Filters then collects |
| `flatMapping(mapper, downstream)` | `Collector` | Flattens then collects |
| `collectingAndThen(downstream, finisher)` | `Collector` | Transforms result |
| `reducing(identity, op)` | `Collector` | Reduces elements |

## Common Collector Compositions

### Group By + Count
```java
groupingBy(Employee::department, counting())
```
### Group By + Sum
```java
groupingBy(Employee::department, sumingInt(Employee::getSalary))
```
### Group By + Average
```java
groupingBy(Employee::department, averagingDouble(Employee::getSalary))
```

### Group By + Mapping
```java
groupingBy(Employee::department, mapping(Employee::name, toList()))
```

### Group By + Filtering
```java
groupingBy(Employee::department, filtering(e -> e.salary > 100000, toList()))
```

### Group By + FlatMapping
```java
groupingBy(Employee::department, flatMapping(e -> e.skills().stream(), toSet())
```

### Group By + CollectingAndThen
```java
groupingBy(Eployee::department, collectingAndThen(toList(), Collections::unmodifiableList))
```

### Nested Grouping
```java
groupingBy(Employee::department, groupingBy(Employee:title))
```

### When to use which
Requirement	Collector
List of filtered items	toList()
Unique items	toSet()
Map of objects to their IDs	toMap(Object::id, Function.identity())
Map with duplicate keys	toMap(..., (a,b) -> a)
Count items per category	groupingBy(..., counting())
Sum values per category	groupingBy(..., summingInt(...))
Transform items per category	groupingBy(..., mapping(...))
Filter inside groups	groupingBy(..., filtering(...))
Flatten nested per category	groupingBy(..., flatMapping(...))
Split into two groups	partitioningBy(predicate)
Get max per category	groupingBy(..., maxBy(...))
Unmodifiable result	toUnmodifiableList(), collectingAndThen(...)
String concatenation	joining()
