package org.tryout.junit;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

/*
https://github.com/mkyong/junit5-examples
 */

@DisplayName("Display name Class Level")
public class MessageServiceTest {

    Map<String, String> fixture;

    @BeforeAll
    void setup()
    {
        // fixture initialize
        fixture = new HashMap<>();
    }

    @DisplayName("Test parameters with nice names")
    @ParameterizedTest(name = "Use the value {0} for test")

    void sunnyDay(String orig, String beneficiary) {

    }

    @DisplayName("Test MessageService.get()")
    @Test
    @Tag("Smoke")
    @Tag("Regression")
    void testGet() {
        assertEquals("Hello JUnit 5", "Hello JUnit 5");
    }

    @DisplayName("Test parameters with nice names")
    @ParameterizedTest(name = "Use the value {0} for test")
    @ValueSource(ints = {-1, -4})
    void isValidYear(int number) {
        assertTrue(number < 0, "Is Number greater than 0?");
    }

    @ParameterizedTest(name = "Test fruit \"{0}\" with rank {1}")
    @CsvSource({
            "apple,         1",
            "banana,        2",
            "'lemon, lime', 3"
    })
    void testWithCsvSource(String fruit, int rank) {
        assertNotNull(fruit);
        assertNotEquals(0, rank);
    }

    @RepeatedTest(
            value = 9,
            name = "{displayName}-{currentRepetition}/{totalRepetitions}")
    void valuesCannotPassTen(RepetitionInfo info) {
        assertTrue(info.getCurrentRepetition() < 10);
    }

    @Test
    @Disabled
    void neverRun() {
    }

    @Test
    @EnabledOnOs({OS.LINUX, OS.MAC})
    void runOnSpecificOS() {
    }

    @Test
    @DisabledIfSystemProperty(named = "ci-server", matches = "true")
    void notOnCiServer() {
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "ENV", matches = "test-env")
    void onlyOnTestServer() {
    }


    @Test
    void shouldThrowException() {
        //parse should throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException();
        });
    }

    @Test
    void testTimeout() {
        // underTest.longRunningOperation should run in less than 500 milliseconds
        assertTimeout(Duration.ofMillis(500), () -> {
            //underTest.longRunningOperation()
        });
    }

    Map<String, String> testMap = new HashMap<String, String>(){{
        put("Palindrome 3 letters", "mom");
        put("Palindrome 5 letters", "radar");
        put("Palindrome 6 letters", "redder");
    }};

    @TestFactory
    Stream<DynamicTest> dynamicTestsFromStream() {
        return testMap.keySet().stream()
                .map(key -> dynamicTest(key, () -> {
                    String word = testMap.get(key);
                    assertTrue(word!=null);
                }));
    }

}
