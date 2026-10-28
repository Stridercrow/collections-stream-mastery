# Exercise 094 — Optional.orElseThrow: fail fast on empty

## Objective
Train recognition of `Optional.orElseThrow()` for unwrapping with an exception on empty.

## Ask
Given an `Optional<Integer>`, return its value; if empty, throw a NoSuchElementException.

## Input
Optional<Integer> optional = Optional.of(42) and Optional.empty()

## Expected Output
- 42 when present
- NoSuchElementException when empty

## API Category
Optional

## Hints

### Hint 1
There are two forms: no-arg (throws NoSuchElementException) and supplier-arg (throws your exception).

### Hint 2
The no-arg method replaced the old `get()` — same behavior, clearer name.

### Hint 3
Pass `() -> new IllegalStateException("...")` to customize the failure.

## What to investigate in the Java API
- Class: `java.util.Optional`
- Look for:
  - `orElseThrow()` — throws NoSuchElementException if empty (Java 10+)
  - `orElseThrow(Supplier<? extends X>)` — throws your exception
- Historical note: `Optional.get()` is the legacy equivalent of the no-arg form

## Four Implementation Passes

### 1. Imperative Java
```java
public static int exercise094Imperative(Optional<Integer> optional) {
    if (optional.isPresent()) {
        return optional.get();
    }
    throw new NoSuchElementException("No value present");
}
```

### 2. Stream without Collectors
Not applicable — Optional terminal-style API.

### 3. Stream + Collectors
Not applicable — no stream involved.

### 4. Idiomatic Optional API
```java
public static int exercise094(Optional<Integer> optional) {
    return optional.orElseThrow();
}
```

### Edge cases
- orElseThrow() (no-arg) needs Java 10+; orElseThrow(Supplier) exists since Java 10 too, and before that since Java 8
- Custom message form: optional.orElseThrow(() -> new IllegalArgumentException("id missing"))
- .get() is discouraged in code review — orElseThrow says the intent out loud

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Optional.orElseThrow()                      // NoSuchElementException
Optional.orElseThrow(() -> new MyException()) // custom
```

### Recognition pattern
- "This must have a value — fail otherwise"
- orElseThrow() — the modern replacement for get()
- Supplier form for domain-specific exceptions

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement requireValue(Optional<Integer>) using orElseThrow, then with a custom exception
4. Check if you got it right
