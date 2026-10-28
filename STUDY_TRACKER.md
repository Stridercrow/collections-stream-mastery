
### STUDY_TRACKER.md
```markdown
# Study Tracker

## Progress Legend
- ⬜ Not attempted
- 🟡 First attempt (needed API lookup)
- 🟢 Solution reviewed
- 🔵 Reimplemented from memory
- ✅ Mastered (can implement without lookup)

## Exercise Progress

| # | Exercise | First Attempt | API Lookup | Solution Reviewed | Reimplemented | Mastered |
|---|----------|---------------|------------|-------------------|---------------|----------|
| 001 | Sum int[] with IntStream | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 002 | Average int[] | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 003 | IntStream statistics | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 004 | Filter employees by department | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 005 | Map employee names | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 006 | FlatMap skills | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 007 | Distinct departments | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 008 | Sort employees by salary | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 009 | Limit and skip | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 010 | TakeWhile/DropWhile | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 011 | FindFirst and findAny | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 012 | AnyMatch/allMatch/noneMatch | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 013 | Min and max with comparator | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 014 | Reduce to concatenate | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 015 | Count with Stream | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 016 | toList from Stream | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 017 | IntStream range | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 018 | IntStream rangeClosed | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 019 | IntStream iterate | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 020 | IntStream generate | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 021 | Primitive sum with mapToInt | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 022 | Primitive average with mapToInt | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 023 | Primitive summaryStatistics | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 024 | boxed() primitive to wrapper | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 025 | mapToObj from IntStream | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 026 | LongStream operations | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 027 | DoubleStream operations | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 028 | Arrays.stream vs Stream.of | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 029 | Collectors.toList | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 030 | Collectors.toSet | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 031 | Collectors.toCollection | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 032 | Collectors.toUnmodifiableList | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 033 | Collectors.toMap simple | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 034 | Collectors.toMap with duplicates | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 035 | Collectors.toMap with merge | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 036 | Collectors.toMap with mapSupplier | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 037 | Collectors.joining | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 038 | Group by department | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 039 | Group by with counting | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 040 | Group by with summing | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 041 | Group by with averaging | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 042 | Group by with summarizing | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 043 | Group by with maxBy | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 044 | Group by with minBy | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 045 | Partitioning by | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 046 | Map.put vs putIfAbsent | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 047 | Map.getOrDefault | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 048 | Map.computeIfAbsent with List | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 049 | Map.computeIfAbsent with Set | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 050 | Map.merge for counting | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 051 | Map.merge for summing | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 052 | Map.computeIfPresent | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 053 | Map.compute | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 054 | Map.replace | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 055 | Map.replaceAll | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 056 | Map.remove | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 057 | Map.containsKey/containsValue | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 058 | Map.entrySet iteration | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 059 | Map.forEach | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 060 | GroupingBy with mapping | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 061 | GroupingBy with filtering | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 062 | GroupingBy with flatMapping | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 063 | GroupingBy with collectingAndThen | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 064 | Nested groupingBy | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 065 | GroupingBy with TreeMap | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 066 | GroupingBy with LinkedHashMap | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 067 | Downstream: counting | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 068 | Downstream: summingInt | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 069 | Downstream: averagingInt | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 070 | Downstream: summarizingInt | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 071 | Downstream: minBy | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 072 | Downstream: maxBy | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 073 | Downstream: joining | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 074 | Downstream: reducing | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 075 | Downstream: mapping | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 076 | Downstream: filtering | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 077 | Downstream: flatMapping | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 078 | Downstream: collectingAndThen | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 079 | Map.Entry.comparingByKey | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 080 | Map.Entry.comparingByValue | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 081 | Filter map entries by value | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 082 | Transform map entries | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 083 | Find max entry | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 084 | Find min entry | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 085 | Sort map entries | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 086 | Convert map to list | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 087 | Stream to map with duplicates | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 088 | toConcurrentMap | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 089 | groupingByConcurrent | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 090 | Optional.map | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 091 | Optional.filter | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 092 | Optional.orElse | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 093 | Optional.orElseGet | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 094 | Optional.orElseThrow | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 095 | Optional with min/max | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 096 | Optional with findFirst | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 097 | Optional with findAny | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 098 | Mixed: groupingBy + mapToInt | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 099 | Mixed: computeIfAbsent + stream | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| 100 | Mixed: full API recognition | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |