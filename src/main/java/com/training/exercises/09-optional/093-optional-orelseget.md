# Exercise 093 — Optional.orElseGet: lazy default

## Objective
Train recognition of `Optional.orElseGet()` — the lazy sibling of orElse.

## Ask
Given an `Optional<Integer>`, return its value, or a COMPUTED fallback (a pseudorandom-ish int derived from the current time) only when empty.

## Input
Optional<Integer> optional = Optional.of(42) and Optional.empty()

## Expected Output
- 42 when present — the supplier is NEVER called
- A computed value when empty

## API Category
Optional

## Hints

### Hint 1
The fallback is a Supplier, not a value.

### Hint 2
`Optional` has a method that invokes the supplier ONLY when the value is missing.

### Hint 3
The lambda has NO parameters: () -> someComputation.

## What to investigate in the Java API
- Class: `java.util.Optional`
- Look for: `orElseGet(Supplier<? extends T>)`
- Contrast with `orElse(T)` — eager vs lazy evaluation

## Four Implementation Passes

### 1. Imperative Java
```java
public static int exercise093Imperative(Optional<Integer> optional) {
    if (optional.isPresent()) {
        return optional.get();
    }
    return (int) (System.currentTimeMillis() % 100);
}
```

### 2. Stream without Collectors
Not applicable — Optional terminal-style API.

### 3. Stream + Collectors
Not applicable — no stream involved.

### 4. Idiomatic Optional API
```java
public static int exercise093(Optional<Integer> optional) {
    return optional.orElseGet(() -> (int) (System.currentTimeMillis() % 100));
}
```

### Edge cases
- The supplier runs AT MOST once, and only when empty
- Side effects in the supplier are therefore conditional — orElse would run them always
- Choosing orElse vs orElseGet is about COST and SIDE EFFECTS of the default

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Optional.orElseGet(Supplier)
```

### Recognition pattern
- "Default that must be computed / is expensive / has side effects"
- orElseGet(() -> compute()) — lazy
- orElse(constant) — eager, fine for literals

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement valueOrComputed(Optional<Integer>) using orElseGet()
4. Check if you got it right
