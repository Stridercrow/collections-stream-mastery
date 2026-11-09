# Java Collections Decision Guide

**For fintech interview candidates**

---

## How to Read This on Kindle

Convert this markdown to epub or mobi with:

- **Pandoc:** `pandoc file.md -o file.epub`
- **Kindle Previewer:** open an epub or html file
- **Calibre:** import markdown, export to azw3

Keep headings short. Avoid wide tables.
Code lines stay short here on purpose.
If text wraps on your device, that is expected
and fine for scanning.

---

## Quick Decision Flow

Use this path under time pressure.

**1. Keys and values, or values only?**

- Values only → continue to step 2
- Keys + values → continue to step 3

**2. Values only — do I need uniqueness?**

- Yes, insertion order matters → **LinkedHashSet**
- Yes, no order needed → **HashSet**
- No, and I need indexed access → **ArrayList**
- No, and I need fast add/remove at ends → **ArrayDeque**

**3. Keys + values — what do I need from keys?**

- Fast lookup, no order needed → **HashMap**
- Fast lookup, insertion order → **LinkedHashMap**
- Fast lookup, sorted keys → **TreeMap**

**4. Do I need priority ordering?**

- Yes → **PriorityQueue**

**5. Is this shared across threads?**

- Yes, map-like → **ConcurrentHashMap**
- Yes, queue handoff → **BlockingQueue**

---

## Collection Short Profiles

Each profile ends with a tiny example.
The examples are readable on purpose:
they show the shape of the code you would
actually write, not a full program.

### ArrayList

- **Purpose:** Resizable array, indexed access.
- **Strengths:** Fast get(i), fast append at end.
- **Weakness:** Slow insert/remove in middle.
- **Costs:** get O(1), add at end O(1) amortized,
  insert/remove in middle O(n), contains O(n).
- **Use when:** You build a list once, then read
  it by index or iterate it in order. Most
  "give me a list" code is this.
- **Why not others:** LinkedList is slower to
  iterate (cache locality); ArrayDeque gives no
  indexed access; a Set drops duplicates.
- **Fintech use:** List of transactions to iterate once.
- **Not for:** Frequent middle inserts or removing by value.

```java
List<BigDecimal> amounts = new ArrayList<>();
for (Transaction t : daySheet) {
    amounts.add(t.getAmount());   // append: O(1)
}
BigDecimal third = amounts.get(2); // index: O(1)
```

### LinkedList

- **Purpose:** Doubly-linked list.
- **Strengths:** Fast add/remove at head and tail;
  also implements Deque.
- **Weakness:** Slow indexed access, poor cache locality.
- **Costs:** add/remove at ends O(1),
  get(i) O(n), contains O(n).
- **Use when:** Rarely. Only when you are already
  holding an iterator mid-list and want to remove
  as you go, or you specifically need the Deque
  interface on a List type. ArrayDeque beats it
  for queue/stack; ArrayList beats it for reads.
- **Why not others:** That is the point: there is
  almost always a better-fitting collection.
- **Fintech use:** Rarely the best default choice.
- **Not for:** Random access or large iteration.

```java
// The one place LinkedList shines:
// remove while walking with an iterator.
for (Iterator<Transaction> it = pending.iterator();
     it.hasNext(); ) {
    if (it.next().isReversed()) {
        it.remove();              // O(1) at cursor
    }
}
// (ArrayList.remove inside a loop would be O(n^2).)
```

### HashMap

- **Purpose:** Key-value lookup by hash.
- **Strengths:** O(1) average put/get.
- **Weakness:** No order guaranteed.
- **Costs:** put/get/containsKey O(1) average.
- **Use when:** The default map. You look values
  up by key and do not care about iteration order.
- **Why not others:** TreeMap pays O(log n) for
  sorting you may not need; LinkedHashMap pays
  memory for order you may not need.
- **Fintech use:** Look up account by account ID.
- **Not for:** Sorted keys or iteration order.

```java
Map<String, Account> byId = new HashMap<>();
byId.put(acct.getId(), acct);          // O(1)
Account a = byId.get(requestedId);     // O(1)
if (a == null) throw new UnknownAccountException();
```

### HashSet

- **Purpose:** Unique elements by hash.
- **Strengths:** O(1) add/contains.
- **Weakness:** No order guaranteed.
- **Costs:** add/contains/remove O(1) average.
- **Use when:** "Have I seen this before?" and
  "is this allowed?" questions, or exact-set
  operations like intersection and difference.
  Order is irrelevant to you.
- **Why not others:** LinkedHashSet costs extra
  memory for order you do not need; TreeSet
  costs O(log n) for sorting you do not need.
- **Fintech use:** Deduplicate transaction IDs.
- **Not for:** When insertion or sorted order matters.

```java
Set<String> processed = new HashSet<>();
for (Transaction t : feed) {
    if (!processed.add(t.getId())) {  // false = dupe
        metrics.duplicateDropped(t);
    }
}
```

### LinkedHashSet

- **Purpose:** Unique elements with insertion order.
- **Strengths:** Stable iteration order.
- **Weakness:** Slightly more memory than HashSet.
- **Costs:** Same O(1) operations as HashSet,
  plus per-entry order links.
- **Use when:** You need dedup AND want to output
  or iterate in first-seen order: reports,
  audit trails, feeds shown to a user.
- **Why not others:** HashSet loses the order;
  TreeSet re-sorts, which is not the same thing
  as first-seen order.
- **Fintech use:** First-seen transaction dedup.
- **Not for:** Sorted order (use TreeSet).

```java
Set<String> auditIds = new LinkedHashSet<>();
for (Transaction t : batch) {
    auditIds.add(t.getId());          // dupes ignored
}
// Iterates in first-seen order:
for (String id : auditIds) {
    audit.append(id);
}
```

### LinkedHashMap

- **Purpose:** Map with insertion or access order.
- **Strengths:** Predictable iteration.
- **Weakness:** Slightly more memory.
- **Costs:** O(1) put/get plus order bookkeeping.
- **Use when:** You need map lookup plus stable
  output order, or an LRU cache via access-order
  mode plus removeEldestEntry.
- **Why not others:** HashMap has no order;
  TreeMap sorts by key, not by recency.
- **Fintech use:** Small LRU cache or ordered results.
- **Not for:** Large-scale unsorted lookup.

```java
Map<String, Quote> cache = new LinkedHashMap<>(
    16, 0.75f, true /* access order */);
// Evict the least recently used quote.
cache.removeEldestEntry((map) -> map.size() > 10_000);
Quote q = cache.get(symbol);   // get counts as "use"
```

### TreeMap

- **Purpose:** Sorted keys by natural or custom order.
- **Strengths:** Range queries, first/last key.
- **Weakness:** O(log n) per op, no hash speed.
- **Costs:** put/get/remove O(log n);
  navigable views (headMap, tailMap, subMap,
  floorKey, ceilingKey) are also O(log n).
- **Use when:** Keys have an order and you query
  by that order: ranges, nearest key, floor/
  ceiling, first/last. Think time buckets or
  price ladders.
- **Why not others:** HashMap cannot answer range
  or "closest key" questions at all; sorting a
  HashMap's keys on every query is O(n log n).
- **Fintech use:** Sorted date or amount range queries.
- **Not for:** Pure key lookup with no order need.

```java
NavigableMap<Instant, Rate> book = new TreeMap<>();
book.put(rate.getEffectiveAt(), rate);
// Rate in effect at 10:00: the latest entry
// whose key is <= the query time.
Map.Entry<Instant, Rate> e =
    book.floorEntry(queryTime);
Rate effective =
    (e == null) ? fallback : e.getValue();
```

### TreeSet

- **Purpose:** Sorted unique elements.
- **Strengths:** Range views, first/last.
- **Weakness:** No hash speed.
- **Costs:** add/contains/remove O(log n).
- **Use when:** You need unique values in sorted
  order at all times, or top-N / range views
  while data keeps arriving.
- **Why not others:** HashSet is faster but has
  no order; sorting an ArrayList per query is
  wasteful when inserts keep coming.
- **Fintech use:** Sorted set of unique amounts.
- **Not for:** Pure membership testing.

```java
NavigableSet<BigDecimal> strikes = new TreeSet<>();
strikes.add(order.getLimit());
// Next strike above the current price:
BigDecimal next = strikes.higher(currentPrice);
```

### ArrayDeque

- **Purpose:** Double-ended queue on an array.
- **Strengths:** Fast push/pop/peek at both ends.
- **Weakness:** No indexed access by position.
- **Costs:** add/remove/peek at either end O(1)
  amortized; no indexed access at all.
- **Use when:** Any FIFO queue, LIFO stack, or
  sliding-window processing. The default for
  queue and stack semantics.
- **Why not others:** LinkedList does the same
  with worse locality; java.util.Stack is legacy;
  PriorityQueue would reorder your items.
- **Fintech use:** Queue or stack default choice.
- **Not for:** Fast contains or remove by value.

```java
Queue<Transaction> inbox = new ArrayDeque<>();
inbox.add(t);                     // enqueue at tail
Transaction next = inbox.poll();  // dequeue head

Deque<Batch> undo = new ArrayDeque<>();
undo.push(done);                  // LIFO
Batch last = undo.pop();
```

### PriorityQueue

- **Purpose:** Min-heap (or custom comparator).
- **Strengths:** O(log n) add, O(1) peek.
- **Weakness:** No fast contains or remove by value.
- **Costs:** offer O(log n), peek O(1),
  poll O(log n), remove(object)/contains O(n).
- **Use when:** Consumers always want the "most
  urgent" item next, regardless of arrival order:
  retries, risk scoring, top-K streaming.
- **Why not others:** ArrayDeque is FIFO only;
  sorting a list per poll is O(n log n) each time;
  TreeMap sorts by key, not by urgency.
- **Fintech use:** Priority retry or risk queue.
- **Not for:** Fast contains or remove by value;
  its iterator is NOT in priority order
  (drain with poll, not a for-each).

```java
Queue<Payment> retries = new PriorityQueue<>(
    Comparator.comparing(Payment::getNextRetryAt));
retries.add(failedPayment);
Payment next = retries.peek(); // most overdue, O(1)
while ((next = retries.poll()) != null) {
    scheduler.attempt(next);   // drain in priority order
}
```

### BlockingQueue (interface)

- **Purpose:** Thread-safe producer/consumer queue.
- **Strengths:** Blocks on empty take or full put.
- **Weakness:** Implementations vary in behavior.
- **Costs:** Offer/poll non-blocking or timed;
  put/take block. Backing structure decides the rest.
- **Use when:** Distinct producer and consumer
  threads, and you want backpressure: take() waits
  when empty, put() waits when full.
- **Why not others:** Plain ArrayDeque is not
  thread-safe; a hand-rolled wait/notify queue is
  a classic source of bugs.
- **Fintech use:** Async transaction processing pipeline.
- **Note:** Choose ArrayBlockingQueue, LinkedBlockingQueue,
  or PriorityBlockingQueue based on bounds and ordering.

```java
BlockingQueue<Trade> pipeline =
    new ArrayBlockingQueue<>(10_000); // bounded

// Producer thread:
while (running) {
    pipeline.put(nextTrade());   // blocks when full
}

// Consumer thread:
Trade t = pipeline.take();       // blocks when empty
settle(t);
```

### ConcurrentHashMap

- **Purpose:** Thread-safe hash map.
- **Strengths:** High concurrency, no full locking.
- **Weakness:** Compound operations are not atomic.
- **Costs:** O(1) average reads/writes; writes
  stripe locks internally instead of one big lock.
- **Use when:** A map is read and written from
  multiple threads with little contention per key.
- **Why not others:** synchronized HashMap serializes
  every access; Hashtable is legacy. Neither scales.
- **Fintech use:** Shared counters or per-account aggregates.
- **Not for:** Check-then-act without extra synchronization.

```java
ConcurrentHashMap<String, LongAdder> volume =
    new ConcurrentHashMap<>();
// Atomic compound ops, no external lock:
volume.computeIfAbsent(acctId,
        k -> new LongAdder())
    .increment();
long total = volume.get(acctId).sum();
```

### EnumMap

- **Purpose:** Map with enum keys, backed by an array.
- **Strengths:** Fastest map for enum keys;
  keys iterate in enum declaration order.
- **Weakness:** Keys must be one enum type.
- **Costs:** O(1) put/get via array index, no
  hashing at all; smaller memory footprint
  than HashMap.
- **Use when:** The key domain is an enum:
  statuses, fee types, transaction types.
  Also nice to pre-fill every enum key upfront.
- **Why not others:** HashMap hashes keys you could
  have indexed directly; it also gives you a
  meaningless iteration order for enums.
- **Fintech use:** Tally counts per status or fee type.
- **Not for:** Arbitrary String or entity keys.

```java
Map<TxStatus, Integer> counts =
    new EnumMap<>(TxStatus.class);
for (Transaction t : batch) {
    counts.merge(t.getStatus(), 1, Integer::sum);
}
// Iterates in enum declaration order,
// not arrival order.
```

---

## Queue, Stack, and Ordered Processing

Separate the behavior from the implementation.

**FIFO queue**

Use ArrayDeque as the default.
It is faster and more memory-efficient
than LinkedList for queue operations.

**LIFO stack**

Use ArrayDeque.
It was designed to replace Stack.
Do not use java.util.Stack.

**Double-ended deque**

ArrayDeque supports fast add/remove at both ends.
LinkedList can do this but is rarely the better choice.

**Priority-based ordering**

Use PriorityQueue for single-thread work.
Use PriorityBlockingQueue for concurrent use.

**Blocking producer/consumer handoff**

Use a BlockingQueue implementation.
ArrayBlockingQueue for bounded capacity.
LinkedBlockingQueue for optional bounds.

### Fintech-flavored examples

- Processing transactions in arrival order:
  ArrayDeque as a FIFO queue.

- Retry queue for failed payments:
  PriorityQueue with retry-time comparator,
  or PriorityBlockingQueue if concurrent.

- Undo for reversing a batch operation:
  ArrayDeque used as a stack.

- Priority processing for high-value transactions:
  PriorityQueue sorted by amount or risk score.

### Common mistake

Choosing a familiar collection that
does not match the access pattern.
Do not default to LinkedList for queues or stacks.

---

## Fintech Scenarios

### Deduplicate transaction IDs, preserve order

**Problem:** Keep only the first occurrence.

**Best fit:** LinkedHashSet.

**Why:** Unique elements, insertion order.

**Wrong choice:** HashSet.
It drops order, so first-seen is not guaranteed.

### Group transactions by account ID

**Problem:** Bucket transactions under each account.

**Best fit:** HashMap with List values.

**Why:** Fast key lookup, grouping by account.

**Wrong choice:** TreeMap.
Unnecessary sort cost when order is not needed.

### Look up a transaction by ID

**Problem:** Fast fetch by unique ID.

**Best fit:** HashMap.

**Why:** O(1) average get.

**Wrong choice:** ArrayList.
Linear scan is too slow at scale.

### Count statuses or fee types

**Problem:** Tally occurrences of each value.

**Best fit:** HashMap or EnumMap.

**Why:** Fast increment per key.

**Wrong choice:** HashSet.
It tracks existence, not counts.

### Process transactions in arrival order

**Problem:** FIFO processing.

**Best fit:** ArrayDeque.

**Why:** Fast enqueue and dequeue.

**Wrong choice:** PriorityQueue.
It reorders, so arrival order is lost.

### Small ordered cache of recent lookups

**Problem:** Bounded map, insertion order matters.

**Best fit:** LinkedHashMap with access-order.

**Why:** Predictable iteration, LRU-capable.

**Wrong choice:** HashMap.
No order guarantee.

### Priority-based retry work

**Problem:** Highest priority first.

**Best fit:** PriorityQueue.

**Why:** O(1) peek, O(log n) add.

**Wrong choice:** ArrayList.
No fast priority extraction.

### Thread-safe concurrent counters

**Problem:** Multiple threads update shared counts.

**Best fit:** ConcurrentHashMap or LongAdder.

**Why:** Reduced lock contention.

**Wrong choice:** synchronized HashMap.
Poor scalability under contention.

### Sorted range queries

**Problem:** Find values in a date or amount range.

**Best fit:** TreeMap or TreeSet.

**Why:** Navigable range views, log n operations.

**Wrong choice:** HashMap.
No sorted access, no range support.

---

## Comparison Table

- **ArrayList**
  - Ordered? Yes
  - Indexed? Yes
  - Unique? No
  - Lookup? By index
  - Default for? Indexed iteration

- **LinkedList**
  - Ordered? Yes
  - Indexed? Slow
  - Unique? No
  - Lookup? By index
  - Default for? Rarely

- **HashMap**
  - Ordered? No
  - Indexed? No
  - Unique? By key
  - Lookup? Hash
  - Default for? General lookup

- **HashSet**
  - Ordered? No
  - Indexed? No
  - Unique? Yes
  - Lookup? Hash
  - Default for? Dedup

- **LinkedHashSet**
  - Ordered? Insertion
  - Indexed? No
  - Unique? Yes
  - Lookup? Hash
  - Default for? First-seen dedup

- **LinkedHashMap**
  - Ordered? Insertion
  - Indexed? No
  - Unique? By key
  - Lookup? Hash
  - Default for? Ordered map

- **TreeMap**
  - Ordered? Sorted
  - Indexed? No
  - Unique? By key
  - Lookup? Compare
  - Default for? Sorted keys

- **TreeSet**
  - Ordered? Sorted
  - Indexed? No
  - Unique? Yes
  - Lookup? Compare
  - Default for? Sorted unique set

- **ArrayDeque**
  - Ordered? Insertion
  - Indexed? No
  - Unique? No
  - Lookup? No
  - Default for? Queue/stack

- **PriorityQueue**
  - Ordered? Priority
  - Indexed? No
  - Unique? No
  - Lookup? No
  - Default for? Priority work

- **ConcurrentHashMap**
  - Ordered? No
  - Indexed? No
  - Unique? By key
  - Lookup? Hash
  - Default for? Thread-safe map

---

## Interview Anti-Patterns

- Defaulting to LinkedList too early.
- Using HashSet when iteration order matters.
- Using HashMap when sorted keys are needed.
- Using mutable objects as map keys without
  stable hashCode and equals.
- Assuming HashMap iteration order is stable.
- Picking a concurrent collection and assuming
  compound operations are atomic.
- Choosing by micro-benchmark instead of access pattern.
- Overusing a queue or stack abstraction when a
  simple list or map would be clearer.

---

## Value Objects, Money, and Concurrency

### Immutability and key safety

- If an object is a map key or set element,
  its identity fields must not change after insertion.
- Changing key fields can break lookups.
- Prefer immutable keys and values.
- If mutation is needed, remove, update, then reinsert.

### BigDecimal for money

- Use BigDecimal, not double or float.
- Use the String constructor for exact values.
- Be consistent about scale and rounding early.
- Use compareTo, not equals, for value comparison.
  equals checks scale too, which can surprise you.
- Do not mix scaled and unscaled values by accident.

### What ConcurrentHashMap does not make safe

Individual operations are thread-safe.
Compound operations are not automatically atomic.

Still unsafe patterns:

- Check-then-act on existence and insertion.
- Read-modify-write across multiple keys.
- Business invariants spanning multiple map steps.

You still need locks, atomic references,
or a design that narrows the critical section.

Say this in an interview. It shows you understand
the boundary between a concurrent collection
and correct concurrent logic.

---

## Interview Usage Notes

- Start from the access pattern, not the name.
- Mention trade-offs out loud.
- Note when order, uniqueness, sorted order,
  priority, or concurrency changes the choice.
- Keep the first version simple.
- Optimize only if the problem implies scale or contention.
- If you suggest a concurrent collection,
  say what is still not automatically safe.

---

# Appendix A: One-Line Trade-off Phrases

Use these under time pressure.

- "I chose this based on the dominant operation."
- "I need uniqueness, not order, so HashSet."
- "I need stable iteration order, so LinkedHashSet."
- "I need key lookup, so HashMap."
- "I need insertion-order map, so LinkedHashMap."
- "I need sorted keys, so TreeMap."
- "I need queue or stack, not random access,
  so ArrayDeque."
- "I need priority ordering, so PriorityQueue."
- "Multiple threads share this map,
  so ConcurrentHashMap, but compound logic
  still needs guarding."
- "I use BigDecimal so totals stay exact."
- "I made the key immutable so lookups stay stable."
- "I chose EnumMap because the key type is an enum."
- "I chose ArrayList for indexed iteration."
- "I chose LongAdder over AtomicLong for
  high-contention counters."
- "I ruled out LinkedList because cache locality
  matters at scale."

---

# Appendix B: Practice Quiz

Cover the answer, answer aloud, then check reasoning.

**Q1.**
Deduplicate transaction IDs while keeping first-seen order.
Which collection and why?

A. LinkedHashSet. Unique elements in insertion order.

---

**Q2.**
Group transactions by account ID for fast lookup.
Which collection?

A. HashMap keyed by accountId, value is a List.

---

**Q3.**
You need sorted keys for range queries by date.
Which map?

A. TreeMap. Sorted keys with range views.

---

**Q4.**
A retry queue must process the most overdue payment first.
Which collection?

A. PriorityQueue or PriorityBlockingQueue,
sorted by retry time.

---

**Q5.**
Multiple threads update per-account counters.
Which collection is a good start?

A. ConcurrentHashMap. But compound read-modify-write
still needs external synchronization.

---

**Q6.**
Is HashMap iteration order guaranteed?

A. No. Never rely on it.

---

**Q7.**
You use a BigDecimal as a map key.
What matters most?

A. Stable equals and hashCode.
Use compareTo only for ordering.
Do not rely on equals for numeric equality
if scales differ.

---

**Q8.**
Which collection replaces java.util.Stack?

A. ArrayDeque. Faster and no legacy sync overhead.

---

**Q9.**
You need a small bounded cache in insertion order.
Which map?

A. LinkedHashMap with accessOrder set to true,
overriding removeEldestEntry for eviction.

---

**Q10.**
You have a check-then-act insert pattern
in a concurrent map.
Is ConcurrentHashMap enough?

A. No. You still need external synchronization
or an atomic method like computeIfAbsent.

---

**How to use this quiz:**
Read the scenario, say your answer aloud,
then check the reasoning.
If you hesitate, review the matching profile
or scenario section above.
