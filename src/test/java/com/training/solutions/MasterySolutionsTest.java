package com.training.solutions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.training.model.Employee;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MasterySolutionsTest {

    private List<Employee> employees;
    private Map<String, Integer> sampleMap;

    @BeforeEach
    void setUp() {
        employees = createTestEmployees();
        sampleMap = new HashMap<>();
        sampleMap.put("A", 10);
        sampleMap.put("B", 20);
        sampleMap.put("C", 30);
    }

    // Test data creation
    private List<Employee> createTestEmployees() {
        return List.of(
            new Employee(1, "Alice", "Engineering", "Senior", BigDecimal.valueOf(120000),
                List.of("Java", "Python", "SQL")),
            new Employee(2, "Bob", "Engineering", "Junior", BigDecimal.valueOf(85000),
                List.of("Java", "JavaScript")),
            new Employee(3, "Charlie", "Sales", "Manager", BigDecimal.valueOf(140000),
                List.of("Sales", "CRM", "Negotiation")),
            new Employee(4, "Diana", "Sales", "Representative", BigDecimal.valueOf(70000),
                List.of("Sales", "Customer Service")),
            new Employee(5, "Eve", "Engineering", "Senior", BigDecimal.valueOf(130000),
                List.of("Python", "C++", "SQL", "Java")),
            new Employee(6, "Frank", "Finance", "Analyst", BigDecimal.valueOf(95000),
                List.of("Finance", "Excel", "SQL")),
            new Employee(7, "Grace", "Finance", "Manager", BigDecimal.valueOf(145000),
                List.of("Finance", "Leadership", "Excel"))
        );
    }

    // ============================================
    // Tests for Section 1: Stream Fundamentals (001-016)
    // ============================================

    @Test
    void testExercise001() {
        int[] values = {1, 2, 3, 4, 5};
        assertEquals(15, MasteryMySolutions.exercise001(values));
        assertEquals(0, MasteryMySolutions.exercise001(new int[0]));
    }

    @Test
    void testExercise002() {
        int[] values = {1, 2, 3, 4, 5};
        assertEquals(3.0, MasterySolutions.exercise002(values));
        assertEquals(0.0, MasterySolutions.exercise002(new int[0]));
    }

    @Test
    void testExercise003() {
        int[] values = {1, 2, 3, 4, 5};
        IntSummaryStatistics stats = MasterySolutions.exercise003(values);
        assertEquals(5, stats.getCount());
        assertEquals(1, stats.getMin());
        assertEquals(5, stats.getMax());
        assertEquals(15, stats.getSum());
    }

    @Test
    void testExercise004() {
        List<Employee> eng = MasterySolutions.exercise004(employees, "Engineering");
        assertEquals(3, eng.size());
        assertTrue(eng.stream().allMatch(e -> e.department().equals("Engineering")));
    }

    @Test
    void testExercise005() {
        List<String> names = MasterySolutions.exercise005(employees);
        assertEquals(7, names.size());
        assertEquals("Alice", names.get(0));
    }

    @Test
    void testExercise006() {
        List<String> skills = MasterySolutions.exercise006(employees);
        assertTrue(skills.contains("Java"));
        assertTrue(skills.contains("Python"));
        assertTrue(skills.contains("Sales"));
        /*assertTrue(skills.size() <= Stream.of(employees)
            .flatMap(e -> e.skills().stream())
            .distinct()
            .count());*/
    }

    @Test
    void testExercise007() {
        List<String> depts = MasterySolutions.exercise007(employees);
        assertEquals(3, depts.size());
        assertEquals("Engineering", depts.get(0));
        assertEquals("Finance", depts.get(1));
        assertEquals("Sales", depts.get(2));
    }

    @Test
    void testExercise008() {
        List<Employee> sorted = MasterySolutions.exercise008(employees);
        assertEquals(7, sorted.size());
        assertTrue(sorted.get(0).salary().compareTo(sorted.get(sorted.size() - 1).salary()) >= 0);
    }

    @Test
    void testExercise009() {
        List<Employee> top3 = MasterySolutions.exercise009(employees);
        assertEquals(3, top3.size());
        assertEquals("Grace", top3.get(0).name());
        assertEquals("Charlie", top3.get(1).name());
        assertEquals("Eve", top3.get(2).name());
    }

    @Test
    void testExercise010() {
        List<Employee> result = MasterySolutions.exercise010(employees);
        // Skip first 5, then take while salary > 50000
        assertNotNull(result);
        assertTrue(result.stream().allMatch(e -> e.salary().compareTo(BigDecimal.valueOf(50000)) > 0));
    }

    @Test
    void testExercise011() {
        Optional<Employee> result = MasterySolutions.exercise011(employees);
        assertTrue(result.isPresent());
        assertTrue(result.get().salary().compareTo(BigDecimal.valueOf(100000)) > 0);
    }

    @Test
    void testExercise012() {
        assertFalse(MasterySolutions.exercise012(employees)); // no employee > 200000
    }

    @Test
    void testExercise013() {
        Optional<Employee> max = MasterySolutions.exercise013(employees);
        assertTrue(max.isPresent());
        assertEquals("Grace", max.get().name());
    }

    @Test
    void testExercise014() {
        String result = MasterySolutions.exercise014(employees);
        assertTrue(result.contains("Alice"));
        assertTrue(result.contains("Grace"));
        assertTrue(result.startsWith("Alice"));
    }

    @Test
    void testExercise015() {
        long count = MasterySolutions.exercise015(employees);
        assertEquals(4, count); // Alice, Charlie, Eve, Grace
    }

    @Test
    void testExercise016() {
        List<Employee> result = MasterySolutions.exercise016(employees);
        assertEquals(employees.size(), result.size());
        assertTrue(result.containsAll(employees));
    }

    // ============================================
    // Tests for Section 2: Primitive Streams (017-028)
    // ============================================

    @Test
    void testExercise017() {
        assertEquals(45, MasterySolutions.exercise017(10)); // 1+2+...+9 = 45
        assertEquals(0, MasterySolutions.exercise017(1));
    }

    @Test
    void testExercise018() {
        assertEquals(55, MasterySolutions.exercise018(10)); // 1+2+...+10 = 55
        assertEquals(1, MasterySolutions.exercise018(1));
    }

    @Test
    void testExercise019() {
        List<Integer> squares = MasterySolutions.exercise019(5);
        assertEquals(List.of(1, 4, 9, 16, 25), squares);
    }

    @Test
    void testExercise020() {
        List<Integer> numbers = MasterySolutions.exercise020(10);
        assertEquals(10, numbers.size());
        assertTrue(numbers.stream().allMatch(n -> n >= 0 && n < 100));
    }

    @Test
    void testExercise021() {
        int sum = MasterySolutions.exercise021(employees);
        assertTrue(sum > 0);
    }

    @Test
    void testExercise022() {
        double avg = MasterySolutions.exercise022(employees);
        assertTrue(avg > 0);
    }

    @Test
    void testExercise023() {
        IntSummaryStatistics stats = MasterySolutions.exercise023(employees);
        assertEquals(7, stats.getCount());
        assertTrue(stats.getMin() >= 0);
    }

    @Test
    void testExercise024() {
        int[] arr = {1, 2, 3};
        List<Integer> result = MasterySolutions.exercise024(arr);
        assertEquals(List.of(1, 2, 3), result);
        assertInstanceOf(Integer.class, result.get(0));
    }

    @Test
    void testExercise025() {
        List<String> result = MasterySolutions.exercise025(3);
        assertEquals(List.of("Employee-1", "Employee-2", "Employee-3"), result);
    }

    @Test
    void testExercise026() {
        long[] values = {1L, 2L, 3L, 4L};
        assertEquals(6L, MasterySolutions.exercise026(values)); // 2+4=6
    }

    @Test
    void testExercise027() {
        double[] values = {1.0, -1.0, 2.0, -2.0, 3.0};
        assertEquals(2.0, MasterySolutions.exercise027(values)); // (1+2+3)/3 = 2
    }

    @Test
    void testExercise028() {
        int[] values = {1, 2, 3};
        assertEquals(6, MasterySolutions.exercise028(values));
    }

    // ============================================
    // Tests for Section 3: Basic Collectors (029-037)
    // ============================================

    @Test
    void testExercise029() {
        List<Employee> result = MasterySolutions.exercise029(employees);
        assertTrue(result.stream().allMatch(e -> e.salary().compareTo(BigDecimal.valueOf(80000)) > 0));
    }

    @Test
    void testExercise030() {
        Set<String> depts = MasterySolutions.exercise030(employees);
        assertEquals(3, depts.size());
        assertTrue(depts.contains("Engineering"));
        assertTrue(depts.contains("Sales"));
        assertTrue(depts.contains("Finance"));
    }

    @Test
    void testExercise031() {
        TreeSet<String> depts = MasterySolutions.exercise031(employees);
        assertInstanceOf(TreeSet.class, depts);
        assertEquals(3, depts.size());
        assertEquals("Engineering", depts.first());
        assertEquals("Sales", depts.last());
    }

    @Test
    void testExercise032() {
        List<Employee> result = MasterySolutions.exercise032(employees);
        assertThrows(UnsupportedOperationException.class, () -> result.add(employees.get(0)));
    }

    @Test
    void testExercise033() {
        Map<Long, String> result = MasterySolutions.exercise033(employees);
        assertEquals(7, result.size());
        assertEquals("Alice", result.get(1L));
    }

    @Test
    void testExercise034() {
        Map<String, Employee> result = MasterySolutions.exercise034(employees);
        assertEquals(3, result.size());
        // Should have one employee per department
    }

    @Test
    void testExercise035() {
        Map<String, BigDecimal> result = MasterySolutions.exercise035(employees);
        assertEquals(3, result.size());
        BigDecimal engTotal = employees.stream()
            .filter(e -> e.department().equals("Engineering"))
            .map(Employee::salary)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        assertEquals(engTotal, result.get("Engineering"));
    }

    @Test
    void testExercise036() {
        TreeMap<String, Long> result = MasterySolutions.exercise036(employees);
        assertInstanceOf(TreeMap.class, result);
        assertEquals(3, result.size());
        assertEquals(3L, result.get("Engineering"));
        assertEquals(2L, result.get("Sales"));
        assertEquals(2L, result.get("Finance"));
    }

    @Test
    void testExercise037() {
        String result = MasterySolutions.exercise037(employees);
        assertTrue(result.startsWith("Alice"));
        assertTrue(result.contains("Grace"));
        assertTrue(result.matches(".*\\w+(,\\s+\\w+)*"));
    }

    // ============================================
    // Tests for Section 4: Grouping (038-045)
    // ============================================

    @Test
    void testExercise038() {
        Map<String, List<Employee>> result = MasterySolutions.exercise038(employees);
        assertEquals(3, result.size());
        assertEquals(3, result.get("Engineering").size());
        assertEquals(2, result.get("Sales").size());
        assertEquals(2, result.get("Finance").size());
    }

    @Test
    void testExercise039() {
        Map<String, Long> result = MasterySolutions.exercise039(employees);
        assertEquals(3, result.size());
        assertEquals(3L, result.get("Engineering"));
        assertEquals(2L, result.get("Sales"));
        assertEquals(2L, result.get("Finance"));
    }

    @Test
    void testExercise040() {
        Map<String, Integer> result = MasterySolutions.exercise040(employees);
        assertNotNull(result.get("Engineering"));
        assertTrue(result.get("Engineering") > 0);
    }

    @Test
    void testExercise041() {
        Map<String, Double> result = MasterySolutions.exercise041(employees);
        assertNotNull(result.get("Engineering"));
        assertTrue(result.get("Engineering") > 0);
    }

    @Test
    void testExercise042() {
        Map<String, IntSummaryStatistics> result = MasterySolutions.exercise042(employees);
        assertNotNull(result.get("Engineering"));
        assertEquals(3, result.get("Engineering").getCount());
    }

    @Test
    void testExercise043() {
        Map<String, Optional<Employee>> result = MasterySolutions.exercise043(employees);
        assertEquals("Eve", result.get("Engineering").get().name());
        assertEquals("Charlie", result.get("Sales").get().name());
        assertEquals("Grace", result.get("Finance").get().name());
    }

    @Test
    void testExercise044() {
        Map<String, Optional<Employee>> result = MasterySolutions.exercise044(employees);
        assertEquals("Bob", result.get("Engineering").get().name());
        assertEquals("Diana", result.get("Sales").get().name());
        assertEquals("Frank", result.get("Finance").get().name());
    }

    @Test
    void testExercise045() {
        Map<Boolean, List<Employee>> result = MasterySolutions.exercise045(employees);
        assertTrue(result.containsKey(true));
        assertTrue(result.containsKey(false));
        assertTrue(result.get(true).stream().allMatch(e -> e.salary().compareTo(BigDecimal.valueOf(100000)) > 0));
    }

    // ============================================
    // Tests for Section 5: Map API (046-059)
    // ============================================

    @Test
    void testExercise046() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "old");
        MasterySolutions.exercise046(map, "key", "new");
        assertEquals("old", map.get("key")); // putIfAbsent doesn't replace existing

        MasterySolutions.exercise046(map, "newkey", "new");
        assertEquals("new", map.get("newkey"));
    }

    @Test
    void testExercise047() {
        Map<String, String> map = Map.of("key", "value");
        assertEquals("value", MasterySolutions.exercise047(map, "key"));
        assertEquals("NOT FOUND", MasterySolutions.exercise047(map, "missing"));
    }

    @Test
    void testExercise048() {
        Map<String, List<Integer>> map = new HashMap<>();
        List<Integer> result = MasterySolutions.exercise048(map, "key", 42);
        assertEquals(List.of(42), result);
        assertEquals(1, map.size());

        List<Integer> result2 = MasterySolutions.exercise048(map, "key", 43);
        assertEquals(List.of(42, 43), result2);
    }

    @Test
    void testExercise049() {
        Map<String, Set<Integer>> map = new HashMap<>();
        Set<Integer> result = MasterySolutions.exercise049(map, "key", 42);
        assertEquals(Set.of(42), result);
        assertEquals(1, map.size());

        Set<Integer> result2 = MasterySolutions.exercise049(map, "key", 42);
        assertEquals(Set.of(42), result2); // duplicate not added
    }

    @Test
    void testExercise050() {
        List<String> words = List.of("a", "b", "a", "c", "b", "a");
        Map<String, Integer> result = MasterySolutions.exercise050(words);
        assertEquals(3, result.get("a"));
        assertEquals(2, result.get("b"));
        assertEquals(1, result.get("c"));
    }

    @Test
    void testExercise051() {
        Map<String, Integer> map = new HashMap<>();
        map.put("key", 5);
        MasterySolutions.exercise051(map, "key", 3);
        assertEquals(8, (int) map.get("key"));

        MasterySolutions.exercise051(map, "newkey", 3);
        assertEquals(3, (int) map.get("newkey"));
    }

    @Test
    void testExercise052() {
        Map<String, Integer> map = new HashMap<>();
        map.put("key", 5);
        MasterySolutions.exercise052(map, "key", 3);
        assertEquals(8, (int) map.get("key"));

        MasterySolutions.exercise052(map, "missing", 3);
        assertNull(map.get("missing"));
    }

    @Test
    void testExercise053() {
        Map<String, Integer> map = new HashMap<>();
        map.put("key", 5);
        MasterySolutions.exercise053(map, "key");
        assertEquals(6, (int) map.get("key"));

        MasterySolutions.exercise053(map, "new");
        assertEquals(1, (int) map.get("new"));
    }

    @Test
    void testExercise054() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "old");
        MasterySolutions.exercise054(map, "key", "new");
        assertEquals("new", map.get("key"));

        MasterySolutions.exercise054(map, "missing", "new");
        assertNull(map.get("missing"));
    }

    @Test
    void testExercise055() {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 5);
        map.put("b", 10);
        MasterySolutions.exercise055(map);
        assertEquals(10, (int) map.get("a"));
        assertEquals(20, (int) map.get("b"));
    }

    @Test
    void testExercise056() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        MasterySolutions.exercise056(map, "key");
        assertNull(map.get("key"));
    }

    @Test
    void testExercise057() {
        Map<String, String> map = Map.of("key", "value");
        assertTrue(MasterySolutions.exercise057(map, "key", "value"));
        assertFalse(MasterySolutions.exercise057(map, "key", "wrong"));
        assertFalse(MasterySolutions.exercise057(map, "wrong", "value"));
    }

    @Test
    void testExercise058() {
        Map<String, String> map = Map.of("a", "1", "b", "2");
        Map<String, String> result = MasterySolutions.exercise058(map);
        assertEquals("a", result.get("1"));
        assertEquals("b", result.get("2"));
    }

    @Test
    void testExercise059() {
        Map<String, Integer> map = Map.of("a", 5, "b", 10, "c", 15);
        assertEquals(30, MasterySolutions.exercise059(map));
    }

    // ============================================
    // Tests for Section 6: Downstream (060-078)
    // ============================================

    @Test
    void testExercise060() {
        Map<String, Set<String>> result = MasterySolutions.exercise060(employees);
        assertEquals(3, result.size());
        assertTrue(result.get("Engineering").contains("Alice"));
        assertTrue(result.get("Engineering").contains("Bob"));
        assertTrue(result.get("Engineering").contains("Eve"));
    }

    @Test
    void testExercise061() {
        Map<String, List<Employee>> result = MasterySolutions.exercise061(employees);
        assertTrue(result.get("Engineering").stream().allMatch(e -> e.salary().compareTo(BigDecimal.valueOf(90000)) > 0));
        assertTrue(result.get("Sales").stream().allMatch(e -> e.salary().compareTo(BigDecimal.valueOf(90000)) > 0));
        assertTrue(result.get("Finance").stream().allMatch(e -> e.salary().compareTo(BigDecimal.valueOf(90000)) > 0));
    }

    @Test
    void testExercise062() {
        Map<String, Set<String>> result = MasterySolutions.exercise062(employees);
        assertTrue(result.get("Engineering").contains("Java"));
        assertTrue(result.get("Engineering").contains("Python"));
        assertTrue(result.get("Sales").contains("Sales"));
        assertTrue(result.get("Finance").contains("Finance"));
    }

    @Test
    void testExercise063() {
        Map<String, List<Employee>> result = MasterySolutions.exercise063(employees);
        assertThrows(UnsupportedOperationException.class,
            () -> result.get("Engineering").add(employees.get(0)));
    }

    @Test
    void testExercise064() {
        Map<String, Map<String, List<Employee>>> result = MasterySolutions.exercise064(employees);
        assertEquals(3, result.size());
        assertNotNull(result.get("Engineering").get("Senior"));
        assertNotNull(result.get("Engineering").get("Junior"));
    }

    @Test
    void testExercise065() {
        TreeMap<String, List<Employee>> result = MasterySolutions.exercise065(employees);
        assertInstanceOf(TreeMap.class, result);
        assertEquals(3, result.size());
    }

    @Test
    void testExercise066() {
        LinkedHashMap<String, List<Employee>> result = MasterySolutions.exercise066(employees);
        assertInstanceOf(LinkedHashMap.class, result);
        assertEquals(3, result.size());
    }

    @Test
    void testExercise067() {
        Map<String, Long> result = MasterySolutions.exercise067(employees);
        assertEquals(3L, result.get("Engineering"));
        assertEquals(2L, result.get("Sales"));
        assertEquals(2L, result.get("Finance"));
    }

    @Test
    void testExercise068() {
        Map<String, Integer> result = MasterySolutions.exercise068(employees);
        assertTrue(result.get("Engineering") > 0);
    }

    @Test
    void testExercise069() {
        Map<String, Double> result = MasterySolutions.exercise069(employees);
        assertTrue(result.get("Engineering") > 0);
    }

    @Test
    void testExercise070() {
        Map<String, IntSummaryStatistics> result = MasterySolutions.exercise070(employees);
        assertEquals(3, result.get("Engineering").getCount());
    }

    @Test
    void testExercise071() {
        Map<String, Optional<Employee>> result = MasterySolutions.exercise071(employees);
        assertTrue(result.get("Engineering").isPresent());
    }

    @Test
    void testExercise072() {
        Map<String, Optional<Employee>> result = MasterySolutions.exercise072(employees);
        assertTrue(result.get("Engineering").isPresent());
    }

    @Test
    void testExercise073() {
        Map<String, String> result = MasterySolutions.exercise073(employees);
        assertTrue(result.get("Engineering").contains("Alice"));
        assertTrue(result.get("Engineering").contains("Bob"));
    }

    @Test
    void testExercise074() {
        Map<String, BigDecimal> result = MasterySolutions.exercise074(employees);
        assertTrue(result.get("Engineering").compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void testExercise075() {
        Map<String, List<Long>> result = MasterySolutions.exercise075(employees);
        assertEquals(3, result.get("Engineering").size());
        assertTrue(result.get("Engineering").contains(1L));
    }

    @Test
    void testExercise076() {
        Map<String, List<Employee>> result = MasterySolutions.exercise076(employees);
        assertTrue(result.get("Engineering").stream().allMatch(e -> e.salary().compareTo(BigDecimal.valueOf(100000)) > 0));
    }

    @Test
    void testExercise077() {
        Map<String, List<String>> result = MasterySolutions.exercise077(employees);
        assertTrue(result.get("Engineering").contains("Java"));
        assertTrue(result.get("Engineering").contains("Python"));
    }

    @Test
    void testExercise078() {
        Map<String, Optional<Long>> result = MasterySolutions.exercise078(employees);
        assertTrue(result.get("Engineering").isPresent());
        assertEquals(3L, result.get("Engineering").get());
    }

    // ============================================
    // Tests for Section 7: Map + Stream (079-087)
    // ============================================

    @Test
    void testExercise079() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("c", 3);
        map.put("a", 1);
        map.put("b", 2);
        List<Map.Entry<String, Integer>> result = MasterySolutions.exercise079(map);
        assertEquals("a", result.get(0).getKey());
        assertEquals("b", result.get(1).getKey());
        assertEquals("c", result.get(2).getKey());
    }

    @Test
    void testExercise080() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 3);
        map.put("b", 1);
        map.put("c", 2);
        List<Map.Entry<String, Integer>> result = MasterySolutions.exercise080(map);
        assertEquals(1, result.get(0).getValue());
        assertEquals(2, result.get(1).getValue());
        assertEquals(3, result.get(2).getValue());
    }

    @Test
    void testExercise081() {
        Map<String, Integer> map = Map.of("a", 5, "b", 10, "c", 15);
        Map<String, Integer> result = MasterySolutions.exercise081(map, 10);
        assertEquals(2, result.size());
        assertTrue(result.containsKey("b"));
        assertTrue(result.containsKey("c"));
    }

    @Test
    void testExercise082() {
        Map<String, Integer> map = Map.of("a", 5, "b", 10);
        Map<String, String> result = MasterySolutions.exercise082(map);
        assertEquals("Value: 5", result.get("a"));
        assertEquals("Value: 10", result.get("b"));
    }

    @Test
    void testExercise083() {
        Map<String, Integer> map = Map.of("a", 5, "b", 10, "c", 15);
        Optional<Map.Entry<String, Integer>> result = MasterySolutions.exercise083(map);
        assertTrue(result.isPresent());
        assertEquals("c", result.get().getKey());
        assertEquals(15, result.get().getValue());
    }

    @Test
    void testExercise084() {
        Map<String, Integer> map = Map.of("a", 5, "b", 10, "c", 15);
        Optional<Map.Entry<String, Integer>> result = MasterySolutions.exercise084(map);
        assertTrue(result.isPresent());
        assertEquals("a", result.get().getKey());
        assertEquals(5, result.get().getValue());
    }

    @Test
    void testExercise085() {
        Map<String, Integer> map = Map.of("a", 5, "b", 10, "c", 15);
        List<Map.Entry<String, Integer>> result = MasterySolutions.exercise085(map, 2);
        assertEquals(2, result.size());
        assertEquals(15, result.get(0).getValue());
        assertEquals(10, result.get(1).getValue());
    }

    @Test
    void testExercise086() {
        Map<String, Integer> map = Map.of("a", 5, "b", 10);
        List<String> result = MasterySolutions.exercise086(map);
        assertEquals(2, result.size());
        assertTrue(result.contains("a=5"));
        assertTrue(result.contains("b=10"));
    }

    @Test
    void testExercise087() {
        List<String> words = List.of("apple", "banana", "apple", "cherry");
        Map<String, Integer> result = MasterySolutions.exercise087(words);
        assertEquals(3, result.size());
        assertEquals(5, result.get("apple"));
        assertEquals(6, result.get("banana"));
        assertEquals(6, result.get("cherry"));
    }

    // ============================================
    // Tests for Section 8: Concurrent (088-089)
    // ============================================

    @Test
    void testExercise088() {
        Map<String, Long> result = MasterySolutions.exercise088(employees);
        assertEquals(3L, result.get("Engineering"));
        assertEquals(2L, result.get("Sales"));
        assertEquals(2L, result.get("Finance"));
    }

    @Test
    void testExercise089() {
        Map<String, List<Employee>> result = MasterySolutions.exercise089(employees);
        assertEquals(3, result.get("Engineering").size());
        assertEquals(2, result.get("Sales").size());
        assertEquals(2, result.get("Finance").size());
    }

    // ============================================
    // Tests for Section 9: Optional (090-097)
    // ============================================

    @Test
    void testExercise090() {
        assertEquals(Optional.of(5), MasterySolutions.exercise090(Optional.of("Hello")));
        assertEquals(Optional.empty(), MasterySolutions.exercise090(Optional.empty()));
    }

    @Test
    void testExercise091() {
        assertEquals(Optional.of(5), MasterySolutions.exercise091(Optional.of(5)));
        assertEquals(Optional.empty(), MasterySolutions.exercise091(Optional.of(-1)));
        assertEquals(Optional.empty(), MasterySolutions.exercise091(Optional.empty()));
    }

    @Test
    void testExercise092() {
        assertEquals(5, MasterySolutions.exercise092(Optional.of(5)));
        assertEquals(0, MasterySolutions.exercise092(Optional.empty()));
    }

    @Test
    void testExercise093() {
        assertEquals(5, MasterySolutions.exercise093(Optional.of(5)));
        // For empty, returns a value (can't predict exactly due to timestamp)
        assertNotNull(MasterySolutions.exercise093(Optional.empty()));
    }

    @Test
    void testExercise094() {
        assertEquals(5, MasterySolutions.exercise094(Optional.of(5)));
        assertThrows(NoSuchElementException.class,
            () -> MasterySolutions.exercise094(Optional.empty()));
    }

    @Test
    void testExercise095() {
        Optional<Employee> result = MasterySolutions.exercise095(employees);
        assertTrue(result.isPresent());
        // Oldest employee (based on age calculation from id)
        assertNotNull(result.get());
    }

    @Test
    void testExercise096() {
        Optional<Employee> result = MasterySolutions.exercise096(employees);
        assertTrue(result.isPresent());
        assertEquals("Alice", result.get().name());
    }

    @Test
    void testExercise097() {
        Optional<Employee> result = MasterySolutions.exercise097(employees);
        assertTrue(result.isPresent());
        assertTrue(result.get().salary().compareTo(BigDecimal.valueOf(150000)) > 0);
    }

    // ============================================
    // Tests for Section 10: Mixed (098-100)
    // ============================================

    @Test
    void testExercise098() {
        Map<String, Integer> result = MasterySolutions.exercise098(employees);
        assertTrue(result.get("Engineering") > 0);
        assertTrue(result.get("Sales") > 0);
        assertTrue(result.get("Finance") > 0);
    }

    @Test
    void testExercise099() {
        Map<String, List<Employee>> map = new HashMap<>();
        Employee newEmp = new Employee(8, "Henry", "Engineering", "Junior",
            BigDecimal.valueOf(75000), List.of("Java"));
        MasterySolutions.exercise099(map, newEmp);
        assertEquals(1, map.size());
        assertEquals(1, map.get("Engineering").size());
        assertEquals("Henry", map.get("Engineering").get(0).name());
    }

    @Test
    void testExercise100() {
        Map.Entry<String, Double> result = MasterySolutions.exercise100(employees);
        assertNotNull(result);
        // The department with highest average salary
        assertTrue(result.getValue() > 0);
    }

    // ============================================
    // Tests for Advanced Map Exercises (101-106)
    // ============================================

    @Test
    void testExercise101() {
        Map<String, List<Employee>> result = MasterySolutions.exercise101(employees);
        assertEquals(3, result.size());
        assertEquals(3, result.get("Engineering").size());
        assertEquals(2, result.get("Sales").size());
        assertEquals(2, result.get("Finance").size());
    }

    @Test
    void testExercise102() {
        List<String> words = List.of("a", "b", "a", "c", "b", "a");
        Map<String, Integer> result = MasterySolutions.exercise102(words);
        assertEquals(3, result.get("a"));
        assertEquals(2, result.get("b"));
        assertEquals(1, result.get("c"));
    }

    @Test
    void testExercise103() {
        Map<String, BigDecimal> result = MasterySolutions.exercise103(employees);
        BigDecimal engTotal = employees.stream()
            .filter(e -> e.department().equals("Engineering"))
            .map(Employee::salary)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        assertEquals(engTotal, result.get("Engineering"));
    }

    @Test
    void testExercise104() {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 5);
        map.put("b", 10);
        Map<String, String> result = MasterySolutions.exercise104(map);
        assertEquals("5 items", result.get("a"));
        assertEquals("10 items", result.get("b"));
    }

    @Test
    void testExercise105() {
        Map<String, Integer> map = new HashMap<>();
        map.put("active", 5);
        map.put("inactive", 3);
        MasterySolutions.exercise105(map);
        assertEquals(6, map.get("active"));
        assertEquals(3, map.get("inactive"));
    }

    @Test
    void testExercise106() {
        Map<String, Integer> map = new HashMap<>();
        map.put("key", 5);
        MasterySolutions.exercise106(map, "key");
        assertEquals(6, map.get("key"));

        MasterySolutions.exercise106(map, "new");
        assertEquals(0, map.get("new"));
    }
}