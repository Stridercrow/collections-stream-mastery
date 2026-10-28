
# Solution Guide

## How to Use This Repository

### 1. Read the Exercise
Each exercise file contains:
- **Objective**: What API skill you're training
- **Ask**: The exact requirement
- **Input/Output**: What goes in, what comes out
- **Hints**: Three progressive hints
- **API Investigation**: Which Java class to look at

### 2. Try to Implement
1. Read the Ask
2. Look at the Input/Output
3. **Don't look at the hints yet** — try to guess the API
4. If stuck, use Hint 1
5. If still stuck, use Hint 2
6. If you're really stuck, investigate the API class mentioned

### 3. Check the Solutions
Compare your solution with the four implementation passes:
- Imperative (baseline understanding)
- Stream without Collectors (where applicable)
- Stream + Collectors (functional approach)
- Downstream Collector composition (advanced)

### 4. Self-Assess
Ask yourself:
- Did I know which API to use immediately?
- Did I have to look up the method name?
- Could I write it from memory right now?

### 5. Track Your Progress
Update `STUDY_TRACKER.md`:
- 🟡 if you needed the API lookup
- 🟢 if you reviewed the solution
- 🔵 if you reimplemented from memory
- ✅ if you can do it without looking up

### 6. Spaced Repetition
After 24-48 hours:
1. Close the solution file
2. Open a new scratch file
3. Implement the exercise from memory
4. Check against the solution
5. If correct, mark as ✅

## Learning Path

### Beginner (Exercises 001-020)
Focus on:
- Basic Stream operations (`filter`, `map`, `flatMap`)
- Primitive streams (`IntStream`, `sum`, `average`)
- Basic Collectors (`toList`, `toSet`)

### Intermediate (Exercises 021-060)
Focus on:
- `Map` API (`computeIfAbsent`, `merge`, `compute`)
- Grouping (`groupingBy`, `partitioningBy`)
- Primitive stream operations (`mapToInt`, `summaryStatistics`)

### Advanced (Exercises 061-080)
Focus on:
- Downstream collectors (`mapping`, `filtering`, `flatMapping`)
- Nested grouping
- Collector composition

### Expert (Exercises 081-100)
Focus on:
- Mixed API problems
- Map + Stream integration
- Full API recognition challenges

## Common Mistakes

### 1. Using boxed when you should use mapToInt
Wrong: `stream().mapToInt(...).boxed().sum()`
Right: `stream().mapToInt(...).sum()`

### 2. Using groupingBy when you need toMap
Wrong: `stream().collect(groupingBy(key, value))`
Right: `stream().collect(toMap(key, value, merge))`

### 3. Using compute when you need merge
Wrong: `map.compute(key, (k, v) -> v == null ? value : v + value)`
Right: `map.merge(key, value, Integer::sum)`

### 4. Filtering upstream vs downstream confusion
Wrong: (removes empty groups)
`stream().filter(predicate).collect(groupingBy(...))`
Right: (preserves empty groups)
`stream().collect(groupingBy(..., filtering(predicate, toList())))`

## Quick Reference

### Most Common APIs by Category

**Stream APIs**:
- `filter()` — keep only matching elements
- `map()` — transform elements
- `flatMap()` — flatten nested collections
- `sorted()` — sort elements
- `limit()`/`skip()` — slice stream
- `findFirst()`/`findAny()` — get one element
- `anyMatch()`/`allMatch()`/`noneMatch()` — check conditions
- `count()` — count elements

**Collectors**:
- `toList()` — collect to list
- `toSet()` — collect to set
- `toMap()` — collect to map
- `groupingBy()` — group elements
- `partitioningBy()` — split into two groups
- `joining()` — concatenate strings

**Map APIs**:
- `computeIfAbsent()` — initialize if missing
- `computeIfPresent()` — update if exists
- `merge()` — accumulate values
- `compute()` — compute from key/current
- `replaceAll()` — transform all values

**Primitive Stream APIs**:
- `sum()` — sum values
- `average()` — average values
- `summaryStatistics()` — get all stats
- `boxed()` — convert to wrapper
- `mapToInt()`/`mapToLong()`/`mapToDouble()` — convert to primitive stream

### Full exercise List

001-020: Stream Fundamentals & Primitive Streams
001 - Sum int[] (detailed)
002 - Average int[]
003 - Summary statistics
004 - Filter by department
005 - Map to names
006 - FlatMap skills
007 - Distinct departments
008 - Sort by salary
009 - Limit top 3
010 - Skip and takeWhile
011 - FindFirst
012 - AnyMatch
013 - Min/max
014 - Reduce concatenation
015 - Count
016 - toList
017 - IntStream.range
018 - IntStream.rangeClosed
019 - IntStream.iterate
020 - IntStream.generate

021-037: More Primitive & Basic Collectors
021 - mapToInt sum
022 - mapToInt average
023 - mapToInt summaryStatistics
024 - boxed()
025 - mapToObj
026 - LongStream
027 - DoubleStream
028 - Arrays.stream vs Stream.of
029 - Collectors.toList
030 - Collectors.toSet
031 - Collectors.toCollection
032 - Collectors.toUnmodifiableList
033 - toMap simple
034 - toMap with duplicates
035 - toMap with merge
036 - toMap with mapSupplier
037 - Collectors.joining

038-045: Grouping & Partitioning
038 - groupBy department (detailed)
039 - groupBy counting (detailed)
040 - groupBy summingInt
041 - groupBy averagingInt
042 - groupBy summarizingInt
043 - groupBy maxBy
044 - groupBy minBy
045 - partitioningBy

046-059: Map API
046 - put vs putIfAbsent
047 - getOrDefault
048 - computeIfAbsent List (detailed)
049 - computeIfAbsent Set
050 - merge counting
051 - merge summing
052 - computeIfPresent
053 - compute
054 - replace
055 - replaceAll
056 - remove
057 - containsKey/Value
058 - entrySet iteration
059 - forEach

060-078: Downstream Collectors
060 - groupingBy mapping (detailed)
061 - groupingBy filtering
062 - groupingBy flatMapping
063 - groupingBy collectingAndThen
064 - nested groupingBy
065 - groupingBy with TreeMap
066 - groupingBy with LinkedHashMap
067 - downstream counting
068 - downstream summingInt
069 - downstream averagingInt
070 - downstream summarizingInt
071 - downstream minBy
072 - downstream maxBy
073 - downstream joining
074 - downstream reducing
075 - downstream mapping toList
076 - downstream filtering
077 - downstream flatMapping
078 - downstream collectingAndThen

079-089: Map + Stream & Concurrent
079 - comparingByKey
080 - comparingByValue
081 - filter entries
082 - transform entries
083 - find max entry
084 - find min entry
085 - sort entries
086 - convert to list
087 - stream to map with duplicates
088 - toConcurrentMap
089 - groupingByConcurrent

090-097: Optional
090 - Optional.map
091 - Optional.filter
092 - Optional.orElse
093 - Optional.orElseGet
094 - Optional.orElseThrow
095 - Optional with min
096 - Optional with findFirst
097 - Optional with findAny

098-100: Mixed Challenges
098 - groupingBy + mapToInt
099 - computeIfAbsent + stream
100 - full API recognition (detailed)