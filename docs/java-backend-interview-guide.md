# Java Backend Interview Study Guide

**Databases · Messaging · JPA/Hibernate · Functional
Java · Security · Resilience · System Design**

For fintech interview candidates

---

## How to Use This Guide

- Each section: facts first, then
  "say this out loud" lines for verbal answers.
- Live-coding sections include copy-from-memory
  drills.
- Appendix A: one-liners for time pressure.
- Appendix B: self-quiz. Cover the answer,
  speak aloud, then check.

Sections map to the announced topics:

1. SQL vs NoSQL (MongoDB)
2. Event-driven messaging (Kafka, ActiveMQ,
   JMS, Solace, SQS)
3. JPA/Hibernate live coding (read-only entity,
   non-updatable column, default update
   behavior, @DynamicUpdate)
4. Functional interfaces and lambdas
5. Security beyond JWT (OAuth, SSO, 2FA)
6. Resilience and microservices patterns
7. System design: duplicate payments,
   booking collisions

---

# 1. SQL vs NoSQL

## The One-Minute Framing

"SQL gives me strong consistency, ACID
transactions, and a schema enforced on write.
NoSQL trades some of that for flexible
schemas and horizontal scale. I choose based
on the consistency and query shape the domain
needs, not on fashion. In payments I default
to SQL; for document-shaped, read-heavy data,
MongoDB fits well."

## SQL (Relational)

- Data model: tables and rows, normalized
  schema.
- Schema: enforced on write (DDL migrations).
- Query: SQL, joins, aggregations, window
  functions.
- Transactions: ACID. Isolation levels:
  READ UNCOMMITTED, READ COMMITTED
  (Postgres default; MySQL InnoDB defaults
  to REPEATABLE READ), REPEATABLE READ,
  SERIALIZABLE.
- Scale: vertical first, then read replicas,
  then sharding (painful and app-aware).
- Examples: PostgreSQL, MySQL, Oracle,
  SQL Server.

Strengths:

- Multi-row, multi-table transactions.
- Ad-hoc queries, reporting, joins.
- Constraints: UNIQUE, FK, CHECK. The
  database is your last line of defense
  against bad data.

Weaknesses:

- Horizontal scale requires sharding.
- Rigid schema can slow model iteration.

## NoSQL Families

- Document: MongoDB, DynamoDB.
- Key-value: Redis, DynamoDB.
- Wide-column: Cassandra, ScyllaDB.
- Graph: Neo4j.

Each family solves a different problem.
Saying just "NoSQL" without naming the
family sounds vague. Name the family and
the trade-off.

## MongoDB Essentials

- Data model: BSON documents in collections.
  Documents nest; arrays included.
- Schema: flexible (schema-on-read), but you
  can enforce rules with JSON Schema
  validation.
- Query: rich query language plus the
  aggregation pipeline ($match, $group,
  $sort, $lookup).
- Indexes: B-tree style, compound indexes.
  ESR rule for compound order: Equality,
  Sort, Range.
- Replication: replica set — one primary,
  secondaries, automatic failover. Writes go
  to the primary; read preference is tunable
  (primary, primaryPreferred, secondary,
  nearest).
- Sharding: horizontal scale by shard key.
  The shard key is THE design decision:
  high cardinality, even distribution, and
  matching query patterns. A bad key gives
  you hot shards forever.
- Transactions: single-document operations
  are always atomic. Multi-document ACID
  transactions exist since 4.0 (replica
  sets) and 4.4+ (sharded), at a cost.
- Consistency: tunable via read/write
  concern; majority writes and primary reads
  are the strong end, secondaries lag
  (eventual).

## ACID vs BASE

- ACID: Atomicity, Consistency, Isolation,
  Durability. Strong guarantees.
- BASE: Basically Available, Soft state,
  Eventually consistent. Availability and
  scale over immediate consistency.
- CAP: under a network partition you choose
  consistency or availability. CP: most
  SQL setups, Mongo with majority concerns.
  AP: Cassandra, Dynamo-style systems.

## Interview Q&A

**Q: When would you pick MongoDB over SQL?**

A: "When the data is document-shaped,
read-heavy, and access patterns are known —
product catalogs, user profiles, event
snapshots. Flexible schema speeds iteration.
But for cross-entity transactions with strict
invariants — payments, ledgers — I go SQL."

**Q: Is MongoDB ACID?**

A: "Single-document operations are atomic
by design. Multi-document ACID transactions
exist since 4.0, with performance cost.
Well-designed Mongo schemas keep related
data in one document so you rarely need them."

**Q: How would you model an order in MongoDB?**

A: "Embed line items inside the order
document — they are read together and bounded
in size. Reference the customer by id. If an
array is unbounded, model it as a separate
collection instead."

**Q: Sharding a payments collection — what
shard key?**

A: "High cardinality, evenly spread, and
matching my queries — for example a hash of
accountId, or a compound key (accountId,
time). Avoid monotonically increasing keys
like a bare timestamp: every insert lands on
one hot shard."

## Say This Out Loud

- "Schema-on-write vs schema-on-read."
- "The database constraint is the last line
  of defense; the application is the first."
- "The shard key decides performance for the
  life of the collection."

---

# 2. Event-Driven Messaging

## Core Concepts (know these cold)

- Point-to-point (queue): one consumer per
  message; consumers load-balance.
- Publish-subscribe (topic): every
  subscriber gets a copy; fan-out.
- Delivery semantics:
  - At-most-once: fire and forget; can lose.
  - At-least-once: can duplicate; needs
    idempotent consumers. Industry default.
  - Exactly-once: only within bounded
    systems (Kafka transactions); a myth
    end-to-end across arbitrary hops.
- Ordering: only guaranteed within a queue
  or partition.
- Durability: persist to disk vs memory.
- Dead-letter queue (DLQ): parking lot for
  poison messages after N failed attempts.
- Idempotent consumer: same message twice
  gives the same result; track processed ids.
- Backpressure: consumer controls intake pace.
- Choreography (events trigger services) vs
  orchestration (a central coordinator
  commands the steps).

## JMS (the API, not the broker)

- Java standard API for messaging. JMS 2.0
  added the simplified API.
- Two styles: Queue (point-to-point) and
  Topic (pub/sub).
- Acknowledgment modes: AUTO_ACKNOWLEDGE,
  CLIENT_ACKNOWLEDGE, DUPS_OK_ACKNOWLEDGE,
  transacted sessions.
- Delivery modes: PERSISTENT (survives
  broker restart) vs NON_PERSISTENT.
- You code against JMS; the broker is
  pluggable (ActiveMQ, Solace, IBM MQ).

```java
JMSContext ctx = cf.createContext();
ctx.createProducer()
   .setDeliveryMode(DeliveryMode.PERSISTENT)
   .send(queue, payment);

ctx.createConsumer(queue)
   .setMessageListener(msg ->
       settle(msg.getBody(Payment.class)));
```

## ActiveMQ

- Open-source JMS broker, two flavors:
  - ActiveMQ Classic: the original.
  - ActiveMQ Artemis: next-gen, faster
    non-blocking core, multi-protocol
    (OpenWire, AMQP 1.0, MQTT, STOMP).
- Model: queues, durable topics, virtual
  topics; journal-based storage.
- Sweet spot: enterprise integration, mature
  JMS workloads, request/reply over a broker.

## Kafka

- Distributed, partitioned, append-only LOG.
  Not a queue: consumers track offsets and
  data is retained and REPLAYABLE.
- Topic → partitions → offsets. Ordering is
  guaranteed only within a partition. Keys
  map to partitions (hash), so key by entity
  id (accountId) to serialize per entity.
- Consumer groups: partitions are split among
  members; you can scale consumers up to the
  partition count. Rebalancing on membership
  change.
- Delivery: at-least-once by default (commit
  offsets after processing). At-most-once if
  you commit first. Exactly-once within
  Kafka via idempotent producer +
  transactions (read-process-write loops).
- Retention: time/size based; log compaction
  keeps the latest record per key. Replay =
  reset offsets.
- Ecosystem: Kafka Streams, Connect, Schema
  Registry, ksqlDB.
- Sweet spot: high throughput, event
  sourcing, streaming pipelines, event bus
  between microservices.

```java
@KafkaListener(topics = "payments",
               groupId = "settlement")
public void on(Payment p, Acknowledgment ack) {
    dedupeAndSettle(p);  // must be idempotent
    ack.acknowledge();   // commit AFTER the work
}
```

## Solace

- Event broker (hardware, VM, software, and
  PubSub+ Cloud). Multi-protocol: JMS, AMQP,
  MQTT, REST, SMPP.
- Strength: guaranteed delivery with queueing
  AND topic fan-out; queues can subscribe to
  topics (durable subscriptions with queue
  semantics); hierarchical topics; message
  VPNs for multi-tenancy.
- Common in banking/finance: reliable pub/sub
  at scale, and a bridge between legacy
  middleware and modern protocols.

## AWS SQS

- Managed queue service, two flavors:
  - Standard: huge throughput,
    at-least-once, best-effort ordering
    (order NOT guaranteed).
  - FIFO: strict order and dedup within
    message groups; needs MessageGroupId and
    MessageDeduplicationId; lower throughput.
- Visibility timeout: the message is hidden
  while a consumer processes it; the consumer
  must delete it when done, otherwise it
  reappears (redelivery → idempotency again).
- Dead-letter queue via redrive policy
  (maxReceiveCount).
- Long polling vs short polling for receive.
- SQS is queue-only; for pub/sub pair it
  with SNS (SNS fan-out to SQS per consumer).

## Choosing (interview shortcut)

- Need replay, event history, streaming?
  Kafka.
- Enterprise JMS, request/reply, many
  protocols? ActiveMQ/Artemis or Solace.
- Bank-grade pub/sub with guaranteed
  delivery and multi-tenancy? Solace.
- Simple managed work queue on AWS? SQS.
- Pub/sub on AWS? SNS, with SQS per
  subscriber.

## Say This Out Loud

- "At-least-once is the default everywhere,
  so I make every consumer idempotent."
- "Kafka orders per partition, so I key
  messages by entity id."
- "Retries without idempotency are how you
  double-charge a customer."

---

# 3. JPA/Hibernate Live Coding

This section maps directly to the announced
topics: read-only entity, non-updatable
column, default update behavior, and
@DynamicUpdate.

## The Core Mental Model

The persistence context ("session") holds
MANAGED entities. At flush time Hibernate:

1. Dirty-checks each managed entity against
   the snapshot taken when it was loaded.
2. Schedules ONE UPDATE per dirty entity.
3. By default that UPDATE sets EVERY
   updatable column — even the ones that
   did not change.

Memorize this one-liner:

"Dirty checking decides WHETHER an entity
is updated. The SET clause by default lists
ALL updatable columns. @DynamicUpdate
narrows the SET clause to the columns that
actually changed."

## Trap: Removing the Setter Is NOT Read-Only

- Hibernate reads and writes FIELDS directly
  (field access); setters are irrelevant.
- Dirty checking compares field values, not
  setter calls.
- A setter-less entity can still be updated
  by field access, reflection, or a framework.

Say: "Removing the setter is encapsulation,
not a persistence guarantee. I enforce
read-only-ness at the JPA level."

## Making an Entity Read-Only

### Option 1: Hibernate @Immutable (entity level)

```java
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "fx_rate_snapshot")
@Immutable
public class FxRateSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String currencyPair;

    @Column(nullable = false, precision = 18, scale = 6)
    private BigDecimal rate;

    // ...
}
```

- Hibernate never generates an UPDATE for it.
- INSERT still works — data can be created,
  never revised.
- Modifications are ignored at flush (and
  some paths throw), so bugs fail safe
  instead of silently persisting.

### Option 2: Session-level read-only

```java
@Transactional
public void audit(Long id) {
    FxRateSnapshot r =
        em.find(FxRateSnapshot.class, id);
    em.unwrap(Session.class)
      .setReadOnly(r, true);
}
```

Or make everything loaded in this session
read-only:

```java
session.setDefaultReadOnly(true);
```

- Read-only entities skip dirty-check
  snapshot work — cheaper queries too.

### Option 3: Spring @Transactional(readOnly = true)

```java
@Transactional(readOnly = true)
public List<FxRateSnapshot> rates(String pair) {
    return repo.findByCurrencyPair(pair);
}
```

- With Hibernate: flush mode goes MANUAL and
  the JDBC connection is marked read-only.
- This is a service-level guard, not
  entity-level — good defense in depth,
  best combined with @Immutable.

### Option 4: Map a view or @Subselect

```java
@Entity
@Subselect("""
    select c.id, c.name,
           count(o.id) as orderCount
    from customers c
    left join orders o on o.customer_id = c.id
    group by c.id, c.name
    """)
@Synchronize({"customers", "orders"})
public class CustomerSummary {

    @Id
    private Long id;

    private String name;

    private int orderCount;
}
```

- Non-updatable by construction: it has no
  table of its own. @Synchronize keeps
  auto-flush correct so the query sees
  pending changes.

### Combo answer (impressive)

"For a truly immutable entity I use
@Immutable plus readOnly transactions.
For computed aggregates I map a @Subselect
or a database view. Column-level control I
get from @Column(updatable = false)."

## Marking a Column Non-Updatable

```java
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false, updatable = false)
    private String accountNumber;

    @Column(nullable = false)
    private BigDecimal balance;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdTs;

    @UpdateTimestamp
    private Instant updatedTs;
}
```

- `updatable = false`: the column never
  appears in the UPDATE SET clause. It is
  still inserted (insertable defaults true).
- `insertable = false`: never in INSERT —
  for columns filled by a DB default or
  trigger.
- Mutating the Java field still compiles
  and runs; the change is simply never
  persisted. Say that out loud — it shows
  precision.

Nuance: JPQL bulk updates
(`update Account set ...`) bypass dirty
checking AND entity metadata — they go
straight to SQL. updatable=false does not
protect you there.

## Default Update Behavior (be precise)

- persist(): schedules the INSERT; with
  IDENTITY it executes immediately to get
  the id.
- Managed entity with NO changes: NO UPDATE
  at all. Dirty checking prevents useless
  SQL.
- Managed entity WITH a change: ONE UPDATE
  whose SET clause contains ALL updatable
  columns, changed or not.
- That UPDATE statement is built once per
  entity type and reused — stable SQL, so
  JDBC statement caching and batching work
  well.

So the precise answer to "does Hibernate
update all columns or only changed ones?":

"Neither extreme. It updates only entities
that changed — but for a changed entity the
UPDATE lists every updatable column, not
just the changed ones."

## @DynamicUpdate

```java
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class Position {

    @Id
    @GeneratedValue
    private Long id;

    private BigDecimal qty;

    private String desk;

    private Instant lastTouch;
}
```

- At flush, the SET clause is built from the
  columns that ACTUALLY changed.
- Change only lastTouch and you get:
  `update position set last_touch=? where id=?`

When to use it:

- DB triggers that must fire only for real
  column changes.
- Wide tables where most updates touch few
  columns.
- Reducing the window for overwriting
  concurrent changes on other columns.

Costs / when NOT to use:

- SQL text varies per changed-column
  combination, so the JDBC statement cache
  and batching become less effective.
- Slight runtime cost building the SQL.
- High-volume, uniform updates: the default
  is faster.

Related knobs (name-drop if probed):

- @DynamicInsert: omit null columns from
  INSERT so DB defaults apply.
- @SelectBeforeUpdate: for DETACHED
  entities, SELECT first to verify something
  actually changed before issuing an UPDATE
  (avoids useless updates on unchanged
  detached graphs).
- Bytecode enhancement: tracks dirty
  properties at set-time instead of snapshot
  comparison; combines with @DynamicUpdate.

## Live-Coding Drill (do this from memory)

One entity that demonstrates all four
answers:

```java
@Entity
@Table(name = "trade")
@DynamicUpdate
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String tradeRef;   // immutable id

    @Column(nullable = false, updatable = false)
    private Instant bookedAt;  // never changes

    @Column(nullable = false)
    private String status;     // changes

    @Version
    private long version;      // optimistic lock
}
```

Then narrate:

- "tradeRef and bookedAt never appear in an
  UPDATE because updatable = false."
- "@DynamicUpdate means status is the only
  column in the SET clause when only status
  changed."
- "@Version gives optimistic locking; a
  concurrent write fails with
  OptimisticLockException."
- "If the whole entity were immutable, I'd
  add @Immutable instead."

To verify in dev: log `org.hibernate.SQL`
(DEBUG) and, on Hibernate 6,
`org.hibernate.orm.jdbc.bind` (TRACE) to
see the exact statements and bound values.

## Mapping Quick Reference (they may probe)

- Fetch defaults: @ManyToOne and @OneToOne
  are EAGER; @OneToMany and @ManyToMany are
  LAZY. Make everything LAZY explicitly.
- cascade: PERSIST, MERGE, REFRESH, REMOVE,
  ALL. Think twice before ALL.
- orphanRemoval = true: removing from the
  collection deletes the row.
- Inheritance strategies: SINGLE_TABLE,
  JOINED, TABLE_PER_CLASS.
- N+1 problem: fix with JOIN FETCH or
  @EntityGraph.

## Say This Out Loud

- "Setters are encapsulation, not
  persistence policy."
- "updatable = false is column-level;
  @Immutable is entity-level; readOnly
  transactions are session-level."
- "Dirty checking answers whether;
  @DynamicUpdate answers which."

---

# 4. Functional Interfaces

## Definition

A functional interface is an interface with
EXACTLY ONE abstract method — the Single
Abstract Method (SAM) rule.

- default methods: do NOT count.
- static methods: do NOT count.
- abstract methods that merely override
  public Object methods (equals, toString,
  hashCode): do NOT count.

@FunctionalInterface is optional; it makes
the compiler enforce the SAM rule. It is
documentation plus a compile-time guard.

```java
@FunctionalInterface
public interface RiskRule {

    boolean passes(Payment p);      // the SAM

    default RiskRule and(RiskRule other) {
        return p -> passes(p) && other.passes(p);
    }

    static RiskRule alwaysTrue() {
        return p -> true;
    }
}
```

## Relation to Lambdas

- A lambda is an INSTANCE of a functional
  interface; the interface is its TARGET
  TYPE.
- The compiler infers which interface from
  context (assignment, parameter, return).
- That is why lambda syntax has no method
  name: there is exactly one abstract method
  it could be implementing.

```java
Predicate<Payment> overLimit =
    p -> p.getAmount().compareTo(LIMIT) > 0;

List<Payment> flagged = payments.stream()
    .filter(overLimit)
    .toList();
```

- Method references are lambda shorthand:
  - Static: `Payment::isSettled`
  - Bound: `txn::getAmount`
  - Unbound: `Payment::getAmount`
  - Constructor: `Settlement::new`
- Lambdas can capture only effectively
  final locals. Unlike anonymous classes,
  a lambda has NO own `this` — `this` is
  the enclosing instance.

## Built-in Interfaces (java.util.function)

- Function<T,R>: T → R. Method: apply.
- Consumer<T>: T → side effect. accept.
- Supplier<T>: produces a T, takes nothing.
- Predicate<T>: T → boolean. test.
- UnaryOperator<T>: T → T.
- BinaryOperator<T>: (T, T) → T (reduce).
- BiFunction, BiPredicate, BiConsumer:
  two-argument variants.
- Primitive specializations: IntPredicate,
  ToLongFunction, LongSupplier — they avoid
  boxing.

Composition methods:

- Function: andThen, compose, identity.
- Predicate: and, or, negate.
- Consumer: andThen.

```java
Function<Payment, Money> fee = Payment::feeOf;
Function<Money, Money> tax = m -> m.times(0.19);

Function<Payment, Money> gross =
    fee.andThen(tax);   // fee first, then tax
```

## Why It Matters (the "so what")

- Streams are built on them: filter takes a
  Predicate, map a Function, forEach a
  Consumer.
- They enable behavior parameterization:
  passing code as data.
- Default methods let interfaces evolve
  (and gain combinators) without breaking
  existing implementors.

One-liner: "A functional interface is the
type of a lambda. One abstract method is
what makes the lambda unambiguous; default
and static methods ride along for free."

---

# 5. Security Beyond JWT

## JWT in One Breath (baseline)

- Compact signed token: header.payload.
  signature (JWS; JWE when encrypted).
- Base64url is ENCODING, not encryption —
  anyone can read the payload.
- Stateless verification: server checks the
  signature; no session lookup.
- Pain point: revocation. Mitigations:
  short expiry + refresh tokens, denylists
  for emergencies.

## OAuth 2.0 — Authorization Framework

- NOT a login protocol (that is OIDC). It
  delegates authorization: "client X may act
  on my behalf with scope Y."
- Roles: resource owner, client,
  authorization server, resource server.
- Grants to know:
  - Authorization Code + PKCE: browser and
    mobile apps; the recommended one (PKCE
    protects the code from interception).
  - Client Credentials: service-to-service.
  - Device Code: TVs and IoT.
  - Refresh token: new access tokens.
  - Implicit and password grants: DEPRECATED
    — saying this signals you are current.
- Access token (often a JWT) + refresh
  token; scopes bound the permissions.

## OpenID Connect (OIDC)

- Identity layer ON TOP of OAuth 2.0.
- Adds the ID token (a JWT with identity
  claims), the userinfo endpoint, and
  standard scopes (openid, profile, email).
- Phrase: "OAuth says what you may do;
  OIDC says who you are."

## SAML 2.0 and SSO

- SAML: XML assertions for enterprise SSO.
  The IdP asserts identity to the service
  provider via browser redirects/POSTs.
- Still dominant in workforce SSO (Okta,
  Microsoft Entra, Ping).
- SSO pattern: one login at the IdP, then
  sessions across apps. OIDC for modern
  web/mobile; SAML for enterprise.

## Server-Side Sessions (the JWT alternative)

- Cookie (httpOnly, Secure, SameSite) maps
  to session state in a shared store (Redis).
- Instant revocation; no token reachable
  from JavaScript.
- Cost: shared state; scale-out needs the
  store or sticky sessions.

## Other Schemes to Name

- API keys: simple, static, per-client;
  rotate them; not user authentication.
- mTLS: both sides present certificates;
  strong service-to-service auth (service
  mesh territory).
- Kerberos/SPNEGO: legacy enterprise
  (Windows AD).
- PASETO: hardened token format, a JWT
  alternative that kills algorithm-confusion
  bugs.
- Signed requests (HMAC): AWS SigV4 style.

## 2FA / MFA

- Factors: knowledge (password), possession
  (phone, hardware key), inherence
  (biometric).
- TOTP (RFC 6238, authenticator apps),
  SMS (weak: SIM swap), push approval,
  WebAuthn/FIDO2/passkeys (phishing-
  resistant, current best practice),
  backup codes.
- Flow: password (factor 1) plus OTP or key
  (factor 2). Watch for push-fatigue attacks;
  number matching helps.

## Interview Framing (memorize)

"JWT is a token FORMAT. OAuth2 is an
authorization FRAMEWORK. OIDC adds
authentication on top. SAML is the
enterprise XML predecessor. Sessions trade
statelessness for instant revocation. MFA
adds factors, not tokens. I pick per
requirement: workforce SSO via IdP with
OIDC/SAML; service-to-service with client
credentials or mTLS; user-facing API with
short-lived access tokens, refresh tokens,
and MFA at login."

---

# 6. Resilience & Microservices

## Fail-Fast: Stop Processing Early

Business-rule level:

- Validate cheap conditions BEFORE expensive
  work. Guard clauses at the top of the flow.
- Example: reject a payment in a closed
  currency pair before reserving funds,
  calling the FX service, or writing rows.

```java
public void transfer(Payment p) {
    if (!fx.isOpen(p.currencyPair())) {
        // stop here, spend nothing
        throw new MarketClosedException(p);
    }
    reserve(p);   // expensive, only after checks
    book(p);
    publish(p);
}
```

System level — the Circuit Breaker:

- The same idea generalized to a failing
  DEPENDENCY: stop calling it early.
- States:
  - CLOSED: normal operation; failures
    counted.
  - OPEN: threshold exceeded → calls fail
    immediately (fail fast), fallback is
    used, the dependency gets time to
    recover.
  - HALF_OPEN: trial calls; success closes
    the breaker, failure reopens it.
- Prevents thread-pool exhaustion, cascading
  failures, and wasted work on doomed calls.

```java
@CircuitBreaker(name = "fxRates",
                fallbackMethod = "cachedRate")
public BigDecimal rate(String pair) {
    return fxClient.rate(pair);
}

BigDecimal cachedRate(String pair, Throwable t) {
    return rateCache.get(pair);  // degrade, don't die
}
```

(Resilience4j syntax; same concept as
Hystrix, which is in maintenance mode.)

Say: "A circuit breaker is fail-fast for
dependencies, the way a guard clause is
fail-fast for business rules. Both refuse
work we already know will fail, to avoid
wasting resources."

## Stream.filter → Distributed Patterns

When the interviewer pushes past the code:

- filter(pred) early in a pipeline →
  admission control / request validation at
  the edge: reject bad work before it
  consumes downstream resources.
- Cheap predicate before an expensive map →
  fail-fast ordering: cheap checks first,
  costly enrichment after.
- Short-circuiting operations (findFirst,
  takeWhile, limit) → circuit breaker:
  stop evaluating when the condition says
  so, before draining resources.
- Lazy evaluation of streams → don't call a
  downstream service until the work is
  actually needed; lazy fetch; backpressure.
- Stateless lambdas → horizontally scalable,
  stateless services; state belongs in a
  store, not in the processing node.
- Exception mid-pipeline → partial failure
  in a distributed flow: you need
  compensation (a saga) or a dead-letter
  queue, not a stack trace.
- parallelStream → fan-out to workers, but
  shared mutable state ruins it — the same
  reason stateless consumers scale in Kafka
  consumer groups.
- Putting filter BEFORE map → filter before
  you enrich or publish: send less data
  across the network. In Kafka Streams,
  filter events before an expensive join or
  before producing onward.

Say: "filter is admission control in
miniature — evaluate a cheap predicate and
refuse work early. The circuit breaker is
the same reflex at the system boundary: if
the predicate says the dependency is
unhealthy, don't even start the work."

## The Resilience Pattern Toolkit

- Timeout: every remote call has one.
- Retry with backoff + jitter: ONLY for
  idempotent operations.
- Circuit breaker: stop hammering a failing
  dependency.
- Bulkhead: separate pools per dependency
  so one failure cannot sink the ship.
- Rate limiter: protect yourself from your
  callers.
- Fallback: cached value, default answer,
  queue for later — degrade, don't die.
- Idempotency + dedup: because retries mean
  duplicates.
- Saga: a distributed "transaction" as a
  sequence of local transactions with
  compensating actions.
- Outbox: write business data and the event
  in ONE DB transaction, publish after —
  solves the dual-write problem.

---

# 7. System Design

## Problem A: Avoiding Duplicate Payments

### Where duplicates come from

- User double-clicks or refreshes.
- Client retries after a timeout — the first
  request may have SUCCEEDED server-side.
- At-least-once messaging redelivers.
- Crash between the debit and the response.

The framing to state first:

"Networks retry, brokers redeliver, users
double-click. Exactly-once end-to-end is a
myth, so I make the payment operation
IDEMPOTENT and add a hard database backstop."

### Layer 1: Idempotency key (application)

- Client generates a UUID per user INTENT
  (per checkout attempt) and sends it as
  `Idempotency-Key`.
- Server: UNIQUE index on idempotency_key.
  - First request: process, store key and
    result.
  - Replay: return the stored result, never
    re-charge.
- Scope keys per operation and entity; keep
  them at least 24–72 hours.

```java
// unique constraint on idempotency_key:
// the duplicate insert throws
try {
    paymentRepo.save(pending);
} catch (DataIntegrityViolationException e) {
    return paymentRepo.findByIdempotencyKey(key)
                      .orElseThrow();
}
```

### Layer 2: Atomic status transition

```sql
UPDATE payments
SET    status = 'PROCESSING'
WHERE  id = :id AND status = 'INITIATED';
-- rowcount 1 → we own it
-- rowcount 0 → someone else is on it
```

- A single UPDATE is atomic; no
  check-then-act race. The rowcount decides
  everything.

### Layer 3: Idempotent consumer (messaging)

- Dedup table of processed message ids,
  written in the SAME transaction as the
  business effect:

```sql
INSERT INTO processed_messages(msg_id, at)
VALUES (:msgId, now());
-- constraint violation → already processed
-- → ack and skip
```

### Layer 4: Optimistic locking

- @Version on the payment row: concurrent
  writers fail cleanly instead of
  interleaving changes.

### Layer 5: Database constraints as backstop

- UNIQUE on (account_id, payment_reference).
- Ledger design: append-only, immutable
  entries; reconcile daily. Even if a bug
  slips through, the ledger shows it.

### The 30-second answer

"Idempotency key from the client, stored
under a unique constraint; state-machine
transitions via atomic conditional updates;
idempotent consumers with a dedup table;
@Version for concurrent writers; an
append-only ledger for audit. Exactly-once
is a myth — I make retries harmless."

## Problem B: Two Bookings Must Not Collide

(Ticketing: two requests race for the same
seat.)

### Name the race first

"Both requests read seat.status = AVAILABLE,
then both write SOLD. Classic
check-then-act. The fix is to make the check
and the write ONE atomic step — at the
database — plus constraints as a backstop."

### Option 1: Atomic conditional UPDATE (default)

```sql
UPDATE seats
SET    status = 'HELD',
       hold_id = :holdId,
       hold_expires_at = :expiresAt
WHERE  id = :seatId AND status = 'AVAILABLE';
-- rowcount 1: you won. 0: someone beat you.
```

- Simple, fast, no explicit locks, pool-
  friendly. Pairs with a two-phase flow:
  HOLD with a TTL → CONFIRM after payment →
  a sweeper releases expired holds.

### Option 2: Pessimistic lock

```java
@Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("select s from Seat s where s.id = :id")
Seat forUpdate(Long id);
// SELECT ... FOR UPDATE: the second tx waits
```

- Strong and easy to reason about. Costs:
  lock contention, deadlock care (always
  lock rows in a consistent order),
  connection held for the transaction.

### Option 3: Optimistic locking

- @Version on the seat or booking; the
  loser retries or gets a friendly
  "that seat was just taken".
- Best when collisions are rare.

### Option 4: Unique constraint backstop

- UNIQUE(event_id, seat_id) on bookings
  (or a partial unique index on active
  statuses in Postgres). Even if all
  application logic fails, the database
  refuses the second booking.

### Option 5: Serialize by design

- Route all bookings for an event to ONE
  Kafka partition (key = event_id) so a
  single consumer processes them in order,
  or use a single-writer service per event.
- Redis short-term hold:
  `SET seat:{id} <token> NX EX 300` — fast,
  but the database constraint remains the
  source of truth.

### Trade-off lines to say

- "Pessimistic: correctness by blocking —
  right for hot contention; watch deadlocks."
- "Optimistic: correctness by retry — right
  when conflicts are rare."
- "Conditional UPDATE: my default — atomic,
  no locks, trivial to test."
- "The unique constraint is the last line
  of defense; I never rely on app logic
  alone."

---

# Appendix A: One-Line Trade-off Phrases

- "SQL for invariants and joins; Mongo for
  document-shaped, read-heavy data."
- "The shard key decides performance for
  the life of the collection."
- "At-least-once is the default; idempotent
  consumers are mandatory."
- "Kafka is a replayable log, not a queue."
- "Ordering lives per partition; I key by
  entity id."
- "Dirty checking decides whether;
  @DynamicUpdate decides which."
- "updatable = false is column-level;
  @Immutable is entity-level."
- "Removing the setter is not read-only."
- "JPQL bulk updates bypass entity
  metadata."
- "A functional interface is the type of a
  lambda: one abstract method; defaults
  ride along."
- "JWT is a format; OAuth2 is a framework;
  OIDC is authentication."
- "PKCE protects the authorization code."
- "Circuit breaker: fail fast on a failing
  dependency; guard clauses: fail fast on
  a failed rule."
- "filter is admission control in
  miniature."
- "Exactly-once is a myth; retries must be
  harmless."
- "The unique constraint is my last line
  of defense."

---

# Appendix B: Practice Quiz

Cover the answer, say it aloud, then check.

**Q1.** A colleague says "the entity is
read-only, I removed the setters." What is
wrong?

A. Hibernate uses field access and dirty
checking — setters are irrelevant. Use
@Immutable (entity), setReadOnly (session),
or readOnly transactions.

---

**Q2.** What does Hibernate's UPDATE look
like by default when one field of a managed
entity changes?

A. One UPDATE listing ALL updatable columns,
even unchanged ones. And no UPDATE at all
if nothing changed — dirty checking.

---

**Q3.** What does @DynamicUpdate change, and
what does it cost?

A. The SET clause contains only changed
columns. Cost: SQL text varies per
combination, so statement caching and JDBC
batching suffer.

---

**Q4.** Does @Column(updatable = false) stop
you mutating the field in Java?

A. No. The field changes in memory; the
change is never persisted. JPQL bulk
updates bypass it entirely.

---

**Q5.** Can an @Immutable entity be inserted?

A. Yes — persist/insert works. It can never
be updated.

---

**Q6.** Why do default methods not break the
functional interface rule?

A. The SAM rule counts only abstract
methods; default and static methods add no
ambiguity about what the lambda implements.

---

**Q7.** `p -> p.getAmount() > 100` — what
type is this lambda?

A. Whatever the target type is — e.g.,
Predicate<Payment> when passed to filter().
Target typing decides.

---

**Q8.** Kafka: two events for the same
account must be processed in order. How?

A. Same topic, key = accountId → same
partition → ordered within that partition.

---

**Q9.** SQS Standard vs FIFO — name two
differences.

A. Standard: at-least-once, no ordering
guarantee, huge throughput. FIFO: ordering
within message groups plus dedup, lower
throughput.

---

**Q10.** Why is the visibility timeout alone
not enough in SQS?

A. If processing outlives it or the consumer
crashes, the message redelivers →
duplicates → you need an idempotent
consumer.

---

**Q11.** MongoDB: is a multi-document update
atomic?

A. Not by default; single-document
operations are atomic. Multi-document ACID
transactions exist (4.0+) at a cost — good
schema design avoids needing them.

---

**Q12.** Circuit breaker states, and what
happens in OPEN?

A. CLOSED → OPEN (threshold tripped) →
HALF_OPEN (probes) → back. In OPEN, calls
fail immediately (fail fast) and the
fallback serves.

---

**Q13.** A client retries a payment after a
timeout. Walk through your defenses.

A. Same idempotency key hits the unique
constraint → return the original result;
no second charge. Behind it: the state
machine, @Version, and the ledger.

---

**Q14.** Two users book the last seat in the
same millisecond. What happens with plain
read-then-write?

A. Both see AVAILABLE, both write SOLD →
double booking. Fix: conditional UPDATE
... WHERE status = 'AVAILABLE' (the rowcount
decides), plus UNIQUE(event_id, seat_id).

---

**Q15.** Name a JWT weakness and a
mitigation.

A. Revocation. Mitigate with short-lived
access tokens plus refresh tokens (and a
denylist for emergencies). Also: the
payload is only encoded, never encrypted —
no secrets inside.

---

## Night-Before Recap

- Read-only: @Immutable / setReadOnly /
  readOnly = true. Never "no setter".
- Column lock: @Column(updatable = false).
- Default: updates only dirty entities, but
  ALL their columns; @DynamicUpdate narrows
  the SET clause.
- Functional interface = one abstract
  method; lambdas are its instances.
- OAuth2 authorizes, OIDC authenticates,
  SAML does enterprise SSO, MFA adds
  factors.
- Circuit breaker = fail-fast for
  dependencies; filter = fail-fast for
  data.
- Duplicates: idempotency key + unique
  constraint + state machine + dedup
  consumer.
- Collisions: atomic conditional UPDATE,
  locks, unique constraint, serialization
  by partition.
