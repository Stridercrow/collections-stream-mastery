# Exercise 091 — Optional.filter: keep or empty

## Objective
Train recognition of `Optional.filter()` for conditionally emptying an Optional.

## Ask
Given an `Optional<Integer>`, return the value only if it is positive; otherwise return an empty Optional.

## Input
Optional<Integer> optional = Optional.of(5), Optional.of(-3), and Optional.empty()

## Expected Output
- Optional[5] for 5
- Optional.empty for -3 (predicate fails)
- Optional.empty for the empty input

## API Category
Optional

## Hints

### Hint 1
The condition is a Predicate, same as in streams.

### Hint 2
`Optional` has a method that keeps the value only when the predicate passes.

### Hint 3
Failing the predicate turns the Optional EMPTY — nothing is thrown.

## What to investigate in the Java API
- Class: `java.util.Optional`
- Look for: `filter(Predicate<T>)` — returns Optional<T>
- Contrast with `orElseThrow()` when a failed condition should throw instead

## Four Implementation Passes

### 1. Imperative Java
```java
public static Optional<Integer> exercise091Imperative(Optional<Integer> optional) {
    if (optional.isPresent() && optional.get() > 0) {
        return optional;
    }
    return Optional.empty();
}
```

### 2. Stream without Collectors
Not applicable — Optional monadic API.

### 3. Stream + Collectors
Not applicable — no stream involved.

### 4. Idiomatic Optional API
```java
public static Optional<Integer> exercise091(Optional<Integer> optional) {
    return optional.filter(v -> v > 0);
}
```

### Edge cases
- Empty input -> predicate is NOT evaluated -> stays empty
- filter + map chains: optional.filter(...).map(...) — read left to right
- filter never throws on empty — unlike orElseThrow()

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Optional.filter(Predicate)
```

### Recognition pattern
- "Keep value only if condition holds"
- optional.filter(predicate) — monadic guard
- Chain shape: filter -> map -> orElse is the idiomatic Optional pipeline

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement positiveOnly(Optional<Integer>) using filter()
4. Check if you got it right
