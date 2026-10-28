# Exercise 092 — Optional.orElse: provide a default

## Objective
Train recognition of `Optional.orElse()` and its EAGER evaluation of the default.

## Ask
Given an `Optional<Integer>`, return its value, or 0 if it is empty.

## Input
Optional<Integer> optional = Optional.of(42) and Optional.empty()

## Expected Output
- 42 when present
- 0 when empty

## API Category
Optional

## Hints

### Hint 1
The fallback is a plain VALUE, not a function.

### Hint 2
`Optional` has a method that takes the default directly.

### Hint 3
Key subtlety: the default expression is evaluated EVEN when the value is present.

## What to investigate in the Java API
- Class: `java.util.Optional`
- Compare:
  - `orElse(T other)` — eager: other is computed regardless
  - `orElseGet(Supplier)` — lazy: supplier runs only when empty

## Four Implementation Passes

### 1. Imperative Java
```java
public static int exercise092Imperative(Optional<Integer> optional) {
    if (optional.isPresent()) {
        return optional.get();
    }
    return 0;
}
```

### 2. Stream without Collectors
Not applicable — Optional terminal-style API.

### 3. Stream + Collectors
Not applicable — no stream involved.

### 4. Idiomatic Optional API
```java
public static int exercise092(Optional<Integer> optional) {
    return optional.orElse(0);
}
```

### Edge cases
- orElse(expensive()) computes expensive() EVERY call — use orElseGet for lazy defaults
- null default is allowed: orElse(null) compiles (rarely wise)
- Constants and literals are the perfect orElse arguments

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Optional.orElse(defaultValue)
```

### Recognition pattern
- "Unwrap with fallback"
- orElse(value) for cheap constants; orElseGet(supplier) for computed defaults
- The eager-vs-lazy distinction is a favorite interview question

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement valueOrZero(Optional<Integer>) using orElse()
4. Check if you got it right
