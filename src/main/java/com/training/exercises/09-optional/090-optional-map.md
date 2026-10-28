# Exercise 090 — Optional.map: transform if present

## Objective
Train recognition of `Optional.map()` as the safe transformer of wrapped values.

## Ask
Given an `Optional<String>`, return an `Optional<Integer>` with the string's length — or an empty Optional if the input was empty.

## Input
Optional<String> optional = Optional.of("stream") and Optional.empty()

## Expected Output
- Optional[6] for "stream"
- Optional.empty for the empty input

## API Category
Optional

## Hints

### Hint 1
Don't call isPresent() and unwrap manually — that defeats the purpose.

### Hint 2
`Optional` has a method that applies a function to the value ONLY if present.

### Hint 3
The result is automatically re-wrapped: Optional<T> -> Optional<R>.

## What to investigate in the Java API
- Class: `java.util.Optional`
- Look for:
  - `map(Function<T, R>)` — returns Optional<R>
  - Related: `flatMap(Function<T, Optional<R>>)` — when the mapper already returns an Optional

## Four Implementation Passes

### 1. Imperative Java (the anti-pattern being replaced)
```java
public static Optional<Integer> exercise090Imperative(Optional<String> optional) {
    if (optional.isPresent()) {
        return Optional.of(optional.get().length());
    }
    return Optional.empty();
}
```

### 2. Stream without Collectors
Not applicable — Optional has its own monadic API.

### 3. Stream + Collectors
Not applicable — no stream involved.

### 4. Idiomatic Optional API
```java
public static Optional<Integer> exercise090(Optional<String> optional) {
    return optional.map(String::length);
}
```

### Edge cases
- Empty input -> empty output, function never invoked
- map flattens automatically: mapper returning Optional<R> via map() gives Optional<Optional<R>> — use flatMap for that
- Chaining: optional.map(a).map(b).orElse(default) replaces nested null checks

### API Recognition
What Java API should I immediately think about when I see this requirement?
```java
Optional.map(Function)
```

### Recognition pattern
- "Transform the value if it exists"
- optional.map(transformer) — no if, no get()
- Mapper already returns Optional? -> flatMap

## Repetition Challenge
Close this file and implement it again from memory:
1. Close the file
2. Open a scratch file
3. Implement lengthIfPresent(Optional<String>) using map()
4. Check if you got it right
