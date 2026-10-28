package com.training.solutions;

import com.training.model.Employee;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.*;

/**
 * MasterySolutions contains executable implementations for all exercises.
 * Each method corresponds to an exercise and demonstrates the idiomatic
 * Java API usage for that requirement.
 */
public class MasterySolutions {

    // ============================================
    // SECTION 1: Stream Fundamentals (001-016)
    // ============================================

    /**
     * Exercise 001: Sum all elements in an int array using IntStream
     */
    public static int exercise001(int[] values) {
        return Arrays.stream(values).sum();
    }

    /**
     * Exercise 002: Calculate average of int array
     */
    public static double exercise002(int[] values) {
        return Arrays.stream(values)
                .average()
                .orElse(0.0);
    }

    /**
     * Exercise 003: Get summary statistics of int array
     */
    public static IntSummaryStatistics exercise003(int[] values) {
        return Arrays.stream(values).summaryStatistics();
    }

    /**
     * Exercise 004: Filter employees by department
     */
    public static List<Employee> exercise004(List<Employee> employees, String department) {
        return employees.stream()
                .filter(e -> e.department().equals(department))
                .toList();
    }

    /**
     * Exercise 005: Map employees to their names
     */
    public static List<String> exercise005(List<Employee> employees) {
        return employees.stream()
                .map(Employee::name)
                .toList();
    }

    /**
     * Exercise 006: FlatMap to get all unique skills
     */
    public static List<String> exercise006(List<Employee> employees) {
        return employees.stream()
                .flatMap(e -> e.skills().stream())
                .distinct()
                .toList();
    }

    /**
     * Exercise 007: Get distinct departments
     */
    public static List<String> exercise007(List<Employee> employees) {
        return employees.stream()
                .map(Employee::department)
                .distinct()
                .sorted()
                .toList();
    }

    /**
     * Exercise 008: Sort employees by salary descending
     */
    public static List<Employee> exercise008(List<Employee> employees) {
        return employees.stream()
                .sorted(Comparator.comparing(Employee::salary).reversed())
                .toList();
    }

    /**
     * Exercise 009: Get top 3 highest paid employees
     */
    public static List<Employee> exercise009(List<Employee> employees) {
        return employees.stream()
                .sorted(Comparator.comparing(Employee::salary).reversed())
                .limit(3)
                .toList();
    }

    /**
     * Exercise 010: Skip first 5 employees, then take while salary > 50000
     */
    public static List<Employee> exercise010(List<Employee> employees) {
        return employees.stream()
                .skip(5)
                .takeWhile(e -> e.salary().compareTo(BigDecimal.valueOf(50000)) > 0)
                .toList();
    }

    /**
     * Exercise 011: Find first employee with salary > 100000
     */
    public static Optional<Employee> exercise011(List<Employee> employees) {
        return employees.stream()
                .filter(e -> e.salary().compareTo(BigDecimal.valueOf(100000)) > 0)
                .findFirst();
    }

    /**
     * Exercise 012: Check if any employee has salary > 200000
     */
    public static boolean exercise012(List<Employee> employees) {
        return employees.stream()
                .anyMatch(e -> e.salary().compareTo(BigDecimal.valueOf(200000)) > 0);
    }

    /**
     * Exercise 013: Find employee with max salary
     */
    public static Optional<Employee> exercise013(List<Employee> employees) {
        return employees.stream()
                .max(Comparator.comparing(Employee::salary));
    }

    /**
     * Exercise 014: Reduce to concatenate employee names with commas
     */
    public static String exercise014(List<Employee> employees) {
        return employees.stream()
                .map(Employee::name)
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
    }

    /**
     * Exercise 015: Count employees with salary > 75000
     */
    public static long exercise015(List<Employee> employees) {
        return employees.stream()
                .filter(e -> e.salary().compareTo(BigDecimal.valueOf(75000)) > 0)
                .count();
    }

    /**
     * Exercise 016: Collect employees to List
     */
    public static List<Employee> exercise016(List<Employee> employees) {
        return employees.stream().toList();
    }

    // ============================================
    // SECTION 2: Primitive Streams (017-028)
    // ============================================

    /**
     * Exercise 017: IntStream.range - sum of numbers from 1 to n
     */
    public static int exercise017(int n) {
        return IntStream.range(1, n).sum();
    }

    /**
     * Exercise 018: IntStream.rangeClosed - sum including n
     */
    public static int exercise018(int n) {
        return IntStream.rangeClosed(1, n).sum();
    }

    /**
     * Exercise 019: IntStream.iterate - generate squares
     */
    public static List<Integer> exercise019(int count) {
        return IntStream.iterate(1, i -> i + 1)
                .map(i -> i * i)
                .limit(count)
                .boxed()
                .toList();
    }

    /**
     * Exercise 020: IntStream.generate - random numbers
     */
    public static List<Integer> exercise020(int count) {
        Random random = new Random(42); // fixed seed for reproducibility
        return IntStream.generate(() -> random.nextInt(100))
                .limit(count)
                .boxed()
                .toList();
    }

    /**
     * Exercise 021: mapToInt - sum of employee ages
     */
    public static int exercise021(List<Employee> employees) {
        return employees.stream()
                .mapToInt(Employee::getAge)
                .sum();
    }

    /**
     * Exercise 022: mapToInt - average employee age
     */
    public static double exercise022(List<Employee> employees) {
        return employees.stream()
                .mapToInt(Employee::getAge)
                .average()
                .orElse(0.0);
    }

    /**
     * Exercise 023: mapToInt - summary statistics of ages
     */
    public static IntSummaryStatistics exercise023(List<Employee> employees) {
        return employees.stream()
                .mapToInt(Employee::getAge)
                .summaryStatistics();
    }

    /**
     * Exercise 024: boxed() - convert ints to Integers
     */
    public static List<Integer> exercise024(int[] values) {
        return Arrays.stream(values)
                .boxed()
                .toList();
    }

    /**
     * Exercise 025: mapToObj - map int to Employee stub
     */
    public static List<String> exercise025(int n) {
        return IntStream.rangeClosed(1, n)
                .mapToObj(i -> "Employee-" + i)
                .toList();
    }

    /**
     * Exercise 026: LongStream operations
     */
    public static long exercise026(long[] values) {            
        return Arrays.stream(values)
                .filter(v -> v % 2 == 0)
                .sum();
    }

    /**
     * Exercise 027: DoubleStream operations
     */
    public static double exercise027(double[] values) {
        return Arrays.stream(values)
                .filter(v -> v > 0)
                .average()
                .orElse(0.0);
    }

    /**
     * Exercise 028: Arrays.stream vs Stream.of for int arrays
     */
    public static int exercise028(int[] values) {
        // Arrays.stream(values) on int[] gives IntStream
        return Arrays.stream(values).sum();
    }

    // ============================================
    // SECTION 3: Basic Collectors (029-037)
    // ============================================

    /**
     * Exercise 029: Collectors.toList
     */
    public static List<Employee> exercise029(List<Employee> employees) {
        return employees.stream()
                .filter(e -> e.salary().compareTo(BigDecimal.valueOf(80000)) > 0)
                .collect(toList());
    }

    /**
     * Exercise 030: Collectors.toSet - unique department names
     */
    public static Set<String> exercise030(List<Employee> employees) {
        return employees.stream()
                .map(Employee::department)
                .collect(toSet());
    }

    /**
     * Exercise 031: Collectors.toCollection - TreeSet sorted departments
     */
    public static TreeSet<String> exercise031(List<Employee> employees) {
        return employees.stream()
                .map(Employee::department)
                .collect(toCollection(TreeSet::new));
    }

    /**
     * Exercise 032: Collectors.toUnmodifiableList
     */
    public static List<Employee> exercise032(List<Employee> employees) {
        return employees.stream()
                .sorted(Comparator.comparing(Employee::name))
                .collect(toUnmodifiableList());
    }

    /**
     * Exercise 033: Collectors.toMap - employee id to name
     */
    public static Map<Long, String> exercise033(List<Employee> employees) {
        return employees.stream()
                .collect(toMap(Employee::id, Employee::name));
    }

    /**
     * Exercise 034: Collectors.toMap with duplicate keys - keep first
     */
    public static Map<String, Employee> exercise034(List<Employee> employees) {
        return employees.stream()
                .collect(toMap(
                        Employee::department,
                        Function.identity(),
                        (existing, replacement) -> existing
                ));
    }

    /**
     * Exercise 035: Collectors.toMap with merge function - sum salaries by department
     */
    public static Map<String, BigDecimal> exercise035(List<Employee> employees) {
        return employees.stream()
                .collect(toMap(
                        Employee::department,
                        Employee::salary,
                        BigDecimal::add
                ));
    }

    /**
     * Exercise 036: Collectors.toMap with TreeMap supplier
     */
    public static TreeMap<String, Long> exercise036(List<Employee> employees) {
        return employees.stream()
                .collect(toMap(
                        Employee::department,
                        e -> 1L,
                        Long::sum,
                        TreeMap::new
                ));
    }

    /**
     * Exercise 037: Collectors.joining - comma-separated employee names
     */
    public static String exercise037(List<Employee> employees) {
        return employees.stream()
                .map(Employee::name)
                .collect(joining(", "));
    }

    // ============================================
    // SECTION 4: Grouping and Partitioning (038-045)
    // ============================================

    /**
     * Exercise 038: groupingBy - group employees by department
     */
    public static Map<String, List<Employee>> exercise038(List<Employee> employees) {
        return employees.stream()
                .collect(groupingBy(Employee::department));
    }

    /**
     * Exercise 039: groupingBy with counting - count employees by department
     */
    public static Map<String, Long> exercise039(List<Employee> employees) {
        return employees.stream()
                .collect(groupingBy(Employee::department, counting()));
    }

    /**
     * Exercise 040: groupingBy with summingInt - total ages by department
     */
    public static Map<String, Integer> exercise040(List<Employee> employees) {
        return employees.stream()
                .collect(groupingBy(Employee::department, summingInt(Employee::getAge)));
    }

    /**
     * Exercise 041: groupingBy with averagingInt - average age by department
     */
    public static Map<String, Double> exercise041(List<Employee> employees) {
        return employees.stream()
                .collect(groupingBy(Employee::department, averagingInt(Employee::getAge)));
    }

    /**
     * Exercise 042: groupingBy with summarizingInt - age stats by department
     */
    public static Map<String, IntSummaryStatistics> exercise042(List<Employee> employees) {
        return employees.stream()
                .collect(groupingBy(Employee::department, summarizingInt(Employee::getAge)));
    }

    /**
     * Exercise 043: groupingBy with maxBy - highest paid by department
     */
    public static Map<String, Optional<Employee>> exercise043(List<Employee> employees) {
        return employees.stream()
                .collect(groupingBy(
                        Employee::department,
                        maxBy(Comparator.comparing(Employee::salary))
                ));
    }

    /**
     * Exercise 044: groupingBy with minBy - lowest paid by department
     */
    public static Map<String, Optional<Employee>> exercise044(List<Employee> employees) {
        return employees.stream()
                .collect(groupingBy(
                        Employee::department,
                        minBy(Comparator.comparing(Employee::salary))
                ));
    }

    /**
     * Exercise 045: partitioningBy - split by salary > 100000
     */
    public static Map<Boolean, List<Employee>> exercise045(List<Employee> employees) {
        return employees.stream()
                .collect(partitioningBy(
                        e -> e.salary().compareTo(BigDecimal.valueOf(100000)) > 0
                ));
    }

    // ============================================
    // SECTION 5: Map API (046-059)
    // ============================================

    /**
     * Exercise 046: Map.put vs putIfAbsent
     */
    public static Map<String, String> exercise046(Map<String, String> map, String key, String value) {
        map.putIfAbsent(key, value);
        return map;
    }

    /**
     * Exercise 047: Map.getOrDefault
     */
    public static String exercise047(Map<String, String> map, String key) {
        return map.getOrDefault(key, "NOT FOUND");
    }

    /**
     * Exercise 048: Map.computeIfAbsent - initialize List
     */
    public static <K, V> List<V> exercise048(Map<K, List<V>> map, K key, V value) {
        map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
        return map.get(key);
    }

    /**
     * Exercise 049: Map.computeIfAbsent - initialize Set
     */
    public static <K, V> Set<V> exercise049(Map<K, Set<V>> map, K key, V value) {
        map.computeIfAbsent(key, k -> new HashSet<>()).add(value);
        return map.get(key);
    }

    /**
     * Exercise 050: Map.merge - counting words
     */
    public static Map<String, Integer> exercise050(List<String> words) {
        Map<String, Integer> counts = new HashMap<>();
        for (String word : words) {
            counts.merge(word, 1, Integer::sum);
        }
        return counts;
    }

    /**
     * Exercise 051: Map.merge - summing values
     */
    public static Map<String, Integer> exercise051(Map<String, Integer> map, String key, int amount) {
        map.merge(key, amount, Integer::sum);
        return map;
    }

    /**
     * Exercise 052: Map.computeIfPresent - update only if exists
     */
    public static Map<String, Integer> exercise052(Map<String, Integer> map, String key, int increment) {
        map.computeIfPresent(key, (k, v) -> v + increment);
        return map;
    }

    /**
     * Exercise 053: Map.compute - compute based on key and current value
     */
    public static Map<String, Integer> exercise053(Map<String, Integer> map, String key) {
        map.compute(key, (k, v) -> v == null ? 1 : v + 1);
        return map;
    }

    /**
     * Exercise 054: Map.replace - replace value if exists
     */
    public static Map<String, String> exercise054(Map<String, String> map, String key, String newValue) {
        map.replace(key, newValue);
        return map;
    }

    /**
     * Exercise 055: Map.replaceAll - transform all values
     */
    public static Map<String, Integer> exercise055(Map<String, Integer> map) {
        map.replaceAll((k, v) -> v * 2);
        return map;
    }

    /**
     * Exercise 056: Map.remove - remove key
     */
    public static Map<String, String> exercise056(Map<String, String> map, String key) {
        map.remove(key);
        return map;
    }

    /**
     * Exercise 057: Map.containsKey and containsValue
     */
    public static boolean exercise057(Map<String, String> map, String key, String value) {
        return map.containsKey(key) && map.containsValue(value);
    }

    /**
     * Exercise 058: Map.entrySet iteration
     */
    public static Map<String, String> exercise058(Map<String, String> map) {
        Map<String, String> result = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            result.put(entry.getValue(), entry.getKey());
        }
        return result;
    }

    /**
     * Exercise 059: Map.forEach - iterate and accumulate
     */
    public static int exercise059(Map<String, Integer> map) {
        int[] sum = {0};
        map.forEach((k, v) -> sum[0] += v);
        return sum[0];
    }

    // ============================================
    // SECTION 6: Downstream Collectors (060-078)
    // ============================================

    /**
     * Exercise 060: groupingBy with mapping - department to skills
     */
    public static Map<String, Set<String>> exercise060(List<Employee> employees) {
        return employees.stream()
                .collect(groupingBy(
                        Employee::department,
                        mapping(Employee::name, toSet())
                ));
    }

    /**
     * Exercise 061: groupingBy with filtering - high paid by department
     */
    public static Map<String, List<Employee>> exercise061(List<Employee> employees) {
        return employees.stream()
                .collect(groupingBy(
                        Employee::department,
                        filtering(
                                e -> e.salary().compareTo(BigDecimal.valueOf(90000)) > 0,
                                toList()
                        )
                ));
    }

    /**
     * Exercise 062: groupingBy with flatMapping - unique skills per department
     */
    public static Map<String, Set<String>> exercise062(List<Employee> employees) {
        return employees.stream()
                .collect(groupingBy(
                        Employee::department,
                        flatMapping(
                                e -> e.skills().stream(),
                                toSet()
                        )
                ));
    }

    /**
     * Exercise 063: groupingBy with collectingAndThen - unmodifiable lists
     */
    public static Map<String, List<Employee>> exercise063(List<Employee> employees) {
        return employees.stream()
                .collect(groupingBy(
                        Employee::department,
                        collectingAndThen(toList(), Collections::unmodifiableList)
                ));
    }

    /**
     * Exercise 064: Nested groupingBy - department then title
     */
    public static Map<String, Map<String, List<Employee>>> exercise064(List<Employee> employees) {
        return employees.stream()
                .collect(groupingBy(Employee::department,
                        groupingBy(Employee::title)
                ));
    }

    /**
     * Exercise 065: groupingBy with TreeMap
     */
    public static TreeMap<String, List<Employee>> exercise065(List<Employee> employees) {
        return employees.stream()
                .collect(groupingBy(Employee::department, TreeMap::new, toList()));
    }

    /**
     * Exercise 066: groupingBy with LinkedHashMap
     */
    public static LinkedHashMap<String, List<Employee>> exercise066(List<Employee> employees) {
        return employees.stream()
                .collect(groupingBy(Employee::department, LinkedHashMap::new, toList()));
    }

    /**
     * Exercise 067: Downstream counting - count employees by department (already exercise039)
     * This is a duplicate for muscle memory
     */
    public static Map<String, Long> exercise067(List<Employee> employees) {
        return employees.stream()
                .collect(groupingBy(Employee::department, counting()));
    }

    /**
     * Exercise 068: Downstream summingInt - sum salaries by department
     */
    public static Map<String, Integer> exercise068(List<Employee> employees) {
        return employees.stream()
                .collect(groupingBy(
                        Employee::department,
                        summingInt(e -> e.salary().intValue())
                ));
    }

    /**
     * Exercise 069: Downstream averagingInt - average salary by department
     */
    public static Map<String, Double> exercise069(List<Employee> employees) {
        return employees.stream()
                .collect(groupingBy(
                        Employee::department,
                        averagingInt(e -> e.salary().intValue())
                ));
    }

    /**
     * Exercise 070: Downstream summarizingInt - salary stats by department
     */
    public static Map<String, IntSummaryStatistics> exercise070(List<Employee> employees) {
        return employees.stream()
                .collect(groupingBy(
                        Employee::department,
                        summarizingInt(e -> e.salary().intValue())
                ));
    }

    /**
     * Exercise 071: Downstream minBy - oldest employee by department
     */
    public static Map<String, Optional<Employee>> exercise071(List<Employee> employees) {
        return employees.stream()
                .collect(groupingBy(
                        Employee::department,
                        minBy(Comparator.comparingInt(Employee::getAge))
                ));
    }

    /**
     * Exercise 072: Downstream maxBy - youngest employee by department
     */
    public static Map<String, Optional<Employee>> exercise072(List<Employee> employees) {
        return employees.stream()
                .collect(groupingBy(
                        Employee::department,
                        maxBy(Comparator.comparingInt(Employee::getAge))
                ));
    }

    /**
     * Exercise 073: Downstream joining - department to employee names
     */
    public static Map<String, String> exercise073(List<Employee> employees) {
        return employees.stream()
                .collect(groupingBy(
                        Employee::department,
                        mapping(Employee::name, joining(", "))
                ));
    }

    /**
     * Exercise 074: Downstream reducing - total salaries by department
     */
    public static Map<String, BigDecimal> exercise074(List<Employee> employees) {
        return employees.stream()
                .collect(groupingBy(
                        Employee::department,
                        reducing(BigDecimal.ZERO, Employee::salary, BigDecimal::add)
                ));
    }

    /**
     * Exercise 075: Downstream mapping - department to employee IDs
     */
    public static Map<String, List<Long>> exercise075(List<Employee> employees) {
        return employees.stream()
                .collect(groupingBy(
                        Employee::department,
                        mapping(Employee::id, toList())
                ));
    }

    /**
     * Exercise 076: Downstream filtering - departments with employees > 100k
     */
    public static Map<String, List<Employee>> exercise076(List<Employee> employees) {
        return employees.stream()
                .collect(groupingBy(
                        Employee::department,
                        filtering(
                                e -> e.salary().compareTo(BigDecimal.valueOf(100000)) > 0,
                                toList()
                        )
                ));
    }

    /**
     * Exercise 077: Downstream flatMapping - department to skills list
     */
    public static Map<String, List<String>> exercise077(List<Employee> employees) {
        return employees.stream()
                .collect(groupingBy(
                        Employee::department,
                        flatMapping(
                                e -> e.skills().stream(),
                                toList()
                        )
                ));
    }

    /**
     * Exercise 078: Downstream collectingAndThen - count and wrap in optional
     */
    public static Map<String, Optional<Long>> exercise078(List<Employee> employees) {
        return employees.stream()
                .collect(groupingBy(
                        Employee::department,
                        collectingAndThen(counting(), Optional::of)
                ));
    }

    // ============================================
    // SECTION 7: Map + Stream composition (079-087)
    // ============================================

    /**
     * Exercise 079: Map.Entry.comparingByKey - sort entries by key
     */
    public static List<Map.Entry<String, Integer>> exercise079(Map<String, Integer> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .toList();
    }

    /**
     * Exercise 080: Map.Entry.comparingByValue - sort entries by value
     */
    public static List<Map.Entry<String, Integer>> exercise080(Map<String, Integer> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .toList();
    }

    /**
     * Exercise 081: Filter map entries by value
     */
    public static Map<String, Integer> exercise081(Map<String, Integer> map, int minValue) {
        return map.entrySet().stream()
                .filter(e -> e.getValue() >= minValue)
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Exercise 082: Transform map entries
     */
    public static Map<String, String> exercise082(Map<String, Integer> map) {
        return map.entrySet().stream()
                .collect(toMap(
                        Map.Entry::getKey,
                        e -> "Value: " + e.getValue()
                ));
    }

    /**
     * Exercise 083: Find max entry by value
     */
    public static Optional<Map.Entry<String, Integer>> exercise083(Map<String, Integer> map) {
        return map.entrySet().stream()
                .max(Map.Entry.comparingByValue());
    }

    /**
     * Exercise 084: Find min entry by value
     */
    public static Optional<Map.Entry<String, Integer>> exercise084(Map<String, Integer> map) {
        return map.entrySet().stream()
                .min(Map.Entry.comparingByValue());
    }

    /**
     * Exercise 085: Sort entries by value descending and limit
     */
    public static List<Map.Entry<String, Integer>> exercise085(Map<String, Integer> map, int limit) {
        return map.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(limit)
                .toList();
    }

    /**
     * Exercise 086: Convert map entries to list of strings
     */
    public static List<String> exercise086(Map<String, Integer> map) {
        return map.entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .toList();
    }

    /**
     * Exercise 087: Stream to map with duplicate keys handling
     */
    public static Map<String, Integer> exercise087(List<String> words) {
        return words.stream()
                .collect(toMap(
                        Function.identity(),
                        String::length,
                        (existing, replacement) -> existing
                ));
    }

    // ============================================
    // SECTION 8: Concurrent Collectors (088-089)
    // ============================================

    /**
     * Exercise 088: Collectors.toConcurrentMap
     */
    public static Map<String, Long> exercise088(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.toConcurrentMap(
                        Employee::department,
                        e -> 1L,
                        Long::sum
                ));
    }

    /**
     * Exercise 089: Collectors.groupingByConcurrent
     */
    public static Map<String, List<Employee>> exercise089(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingByConcurrent(Employee::department));
    }

    // ============================================
    // SECTION 9: Optional (090-097)
    // ============================================

    /**
     * Exercise 090: Optional.map - transform if present
     */
    public static Optional<Integer> exercise090(Optional<String> optional) {
        return optional.map(String::length);
    }

    /**
     * Exercise 091: Optional.filter - filter values
     */
    public static Optional<Integer> exercise091(Optional<Integer> optional) {
        return optional.filter(v -> v > 0);
    }

    /**
     * Exercise 092: Optional.orElse - provide default
     */
    public static int exercise092(Optional<Integer> optional) {
        return optional.orElse(0);
    }

    /**
     * Exercise 093: Optional.orElseGet - lazy default
     */
    public static int exercise093(Optional<Integer> optional) {
        return optional.orElseGet(() -> (int) (System.currentTimeMillis() % 100));
    }

    /**
     * Exercise 094: Optional.orElseThrow - throw if empty
     */
    public static int exercise094(Optional<Integer> optional) {
        return optional.orElseThrow();
    }

    /**
     * Exercise 095: Optional with min - find oldest employee
     */
    public static Optional<Employee> exercise095(List<Employee> employees) {
        return employees.stream()
                .max(Comparator.comparingInt(Employee::getAge));
    }

    /**
     * Exercise 096: Optional with findFirst
     */
    public static Optional<Employee> exercise096(List<Employee> employees) {
        return employees.stream()
                .filter(e -> e.department().equals("Engineering"))
                .findFirst();
    }

    /**
     * Exercise 097: Optional with findAny
     */
    public static Optional<Employee> exercise097(List<Employee> employees) {
        return employees.stream()
                .filter(e -> e.salary().compareTo(BigDecimal.valueOf(150000)) > 0)
                .findAny();
    }

    // ============================================
    // SECTION 10: Mixed API Challenges (098-100)
    // ============================================

    /**
     * Exercise 098: Mixed - groupingBy with mapToInt for total ages
     */
    public static Map<String, Integer> exercise098(List<Employee> employees) {
        return employees.stream()
                .collect(groupingBy(
                        Employee::department,
                        Collectors.summingInt(Employee::getAge)
                ));
    }

    /**
     * Exercise 099: Mixed - computeIfAbsent with stream processing
     */
    public static Map<String, List<Employee>> exercise099(Map<String, List<Employee>> map, Employee employee) {
        map.computeIfAbsent(employee.department(), k -> new ArrayList<>()).add(employee);
        return map;
    }

    /**
     * Exercise 100: Mixed - Full API recognition challenge
     * Given employees, find the department with highest average salary
     */
    public static Map.Entry<String, Double> exercise100(List<Employee> employees) {
        return employees.stream()
                .collect(groupingBy(
                        Employee::department,
                        averagingInt(e -> e.salary().intValue())
                ))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow();
    }

    // ============================================
    // SECTION 11: Advanced Map Exercises
    // ============================================

    /**
     * Exercise 101: Build Map with computeIfAbsent for department groups
     */
    public static Map<String, List<Employee>> exercise101(List<Employee> employees) {
        Map<String, List<Employee>> result = new HashMap<>();
        for (Employee emp : employees) {
            result.computeIfAbsent(emp.department(), k -> new ArrayList<>()).add(emp);
        }
        return result;
    }

    /**
     * Exercise 102: Build frequency map with merge
     */
    public static Map<String, Integer> exercise102(List<String> words) {
        Map<String, Integer> freq = new HashMap<>();
        for (String word : words) {
            freq.merge(word, 1, Integer::sum);
        }
        return freq;
    }

    /**
     * Exercise 103: Build department salary totals with merge
     */
    public static Map<String, BigDecimal> exercise103(List<Employee> employees) {
        Map<String, BigDecimal> totals = new HashMap<>();
        for (Employee emp : employees) {
            totals.merge(emp.department(), emp.salary(), BigDecimal::add);
        }
        return totals;
    }

    /**
     * Exercise 104: Transform map values with replaceAll
     */
    public static Map<String, String> exercise104(Map<String, Integer> map) {        
        return map.entrySet().stream()
        .collect(toMap(
            Map.Entry::getKey, e -> e.getValue() + " items"));
    }

    /**
     * Exercise 105: Update existing entries with computeIfPresent
     */
    public static Map<String, Integer> exercise105(Map<String, Integer> map) {
        map.computeIfPresent("active", (k, v) -> v + 1);
        return map;
    }

    /**
     * Exercise 106: Compute with compute
     */
    public static Map<String, Integer> exercise106(Map<String, Integer> map, String key) {
        map.compute(key, (k, v) -> v == null ? 0 : v + 1);
        return map;
    }
}
