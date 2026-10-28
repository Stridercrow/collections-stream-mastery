# Java Collections & Streams API Mastery

## Philosophy

This repository is designed to develop **muscle memory** for Java's Collection, Stream, Collector, and Map APIs.

**This is NOT an algorithmic exercise repository.**

The goal is to build an instant API recognition reflex:

- See a requirement → Immediately know which API to use
- Know when to use `groupingBy()` vs `toMap()`
- Know when to use `computeIfAbsent()` vs `merge()`
- Know when to use `IntStream.sum()` vs `Collectors.summingInt()`
- Know when to use `filter()` upstream vs `filtering()` downstream

## Learning Principles

### 1. API Recognition First

Most Java developers know *what* they want to do but struggle to remember *which API method* to use. This repository trains that recognition.

### 2. Deliberate Practice

Every important API is repeated multiple times in different contexts. Repetition is intentional—it builds muscle memory.

### 3. Discovery, Not Memorization

Exercises guide you to **discover** the API by describing:
- What you need to accomplish
- What input/output shape to expect
- Which Java class to investigate

### 4. Four Implementation Passes

Each exercise includes four approaches:
1. **Imperative** - Traditional for/if loops (baseline understanding)
2. **Stream without Collectors** - Stream operations where meaningful
3. **Stream with Collectors** - The idiomatic functional approach
4. **Downstream Collector Composition** - Advanced nesting patterns

### 5. Test-Driven Learning

All exercises have accompanying JUnit tests. Run them to verify your solutions.

## Recommended Workflow

1. **Read the exercise requirement**
2. **Identify the input and output types**
3. **Classify the operation** (Primitive? Collection? Map mutation?)
4. **Predict which API family to use** (Stream? Collectors? Map? IntStream?)
5. **Investigate the Java API** (Use hints, JavaDoc, or IDE autocomplete)
6. **Implement the solution** (Start with imperative, then functional)
7. **Run the tests** (`mvn test`)
8. **Compare with the solution** (in `MasterySolutions.java` or docs)
9. **Close the solution** and reimplement from memory
10. **Mark your progress** in `STUDY_TRACKER.md`

## API Recognition Mental Map
Java requirement
|
v
What is the shape?
|
+----------------+----------------+
| | |
v v v
Primitive Collection Map
numeric operation mutation
| | |
v v v
IntStream / Stream / Map API
LongStream / Collectors
DoubleStream
| | |
v v v
sum() toList() computeIfAbsent()
average() toSet() computeIfPresent()
min() toMap() compute()
max() groupingBy() merge()
boxed() partitioningBy()
summaryStats() downstream()


## Quick API Reference

### When to use what?

| Requirement | API |
|-------------|-----|
| Sum of int[] | `Arrays.stream(array).sum()` |
| Sum of object's int property | `stream().mapToInt(Object::property).sum()` |
| Group objects by property | `Collectors.groupingBy()` |
| Count items in groups | `Collectors.groupingBy(..., counting())` |
| Transform items in groups | `Collectors.groupingBy(..., mapping(...))` |
| Initialize Map List only if absent | `Map.computeIfAbsent()` |
| Accumulate values in Map | `Map.merge()` |
| Update Map value if present | `Map.computeIfPresent()` |
| Split into two groups | `Collectors.partitioningBy()` |
| Statistics on primitive values | `IntStream.summaryStatistics()` |
| Collect to Map with duplicates | `Collectors.toMap(..., mergeFunction)` |
| Get Optional wrapper | `findFirst()`, `findAny()`, `min()`, `max()` |

## Repository Structure
java-collections-streams-api-mastery/
├── README.md
├── pom.xml
├── STUDY_TRACKER.md
├── .gitignore
├── docs/
│ ├── API_DISCOVERY_MAP.md
│ ├── COLLECTORS_CHEATSHEET.md
│ ├── MAP_API_CHEATSHEET.md
│ ├── STREAM_API_CHEATSHEET.md
│ ├── PRIMITIVE_STREAM_CHEATSHEET.md
│ ├── DOWNSTREAM_COLLECTORS.md
│ ├── SOLUTION_GUIDE.md
│ └── DETAILED_SOLUTIONS.md
├── src/
│ ├── main/
│ │ └── java/
│ │ └── com/example/mastery/
│ │ ├── model/
│ │ │ ├── Employee.java
│ │ │ ├── Department.java
│ │ │ ├── Order.java
│ │ │ ├── Product.java
│ │ │ └── Transaction.java
│ │ └── solutions/
│ │ └── MasterySolutions.java
│ └── test/
│ └── java/
│ └── com/example/mastery/
│ └── MasterySolutionsTest.java
└── exercises/
├── 00-api-discovery/
├── 01-stream-fundamentals/
├── 02-primitive-streams/
├── 03-basic-collectors/
├── 04-grouping-and-partitioning/
├── 05-map-api/
├── 06-downstream-collectors/
├── 07-advanced-composition/
└── 08-mixed-api-challenges/


## Getting Started

```bash
# Build the project
mvn clean compile

# Run all tests
mvn test

# Run specific test
mvn test -Dtest=MasterySolutionsTest#testExercise001

# Start with exercise 001 and work through sequentially