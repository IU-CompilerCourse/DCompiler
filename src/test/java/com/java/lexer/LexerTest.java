package com.java.lexer;

import com.java.TestUtils;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LexerTest {
    private static final HashMap<String, String> TESTCASES_INPUT = new HashMap<>();
    private static final HashMap<String, String> TESTCASES_CORRECT_OUTPUT = new HashMap<>();
    private static final HashMap<String, String> TESTCASES_ACTUAL_OUTPUT = new HashMap<>();
    private static Path actualOutputDirectory;

    @BeforeAll
    static void tearUp() {
        Path testInputDirectory = Path.of("src", "test", "resources", "test-cases");

        Path testOutputDirectory = Path.of("src", "test", "resources", "lexer-test-output");
        Path correctTestOutputDirectory = Path.of(testOutputDirectory.toAbsolutePath().toString(), "correct-output");
        actualOutputDirectory = Path.of(testOutputDirectory.toAbsolutePath().toString(), "actual-output");

        actualOutputDirectory = TestUtils.makeDirIfNotExists(actualOutputDirectory);

        TESTCASES_INPUT.putAll(TestUtils.readAllFilesToMemory(testInputDirectory.toFile().listFiles()));
        TESTCASES_CORRECT_OUTPUT.putAll(TestUtils.readAllFilesToMemory(correctTestOutputDirectory.toFile()
            .listFiles()));
    }

    @AfterAll
    static void tearDown() {
        TESTCASES_ACTUAL_OUTPUT.forEach((key, entry) -> {
            TestUtils.writeToFile(actualOutputDirectory, key, entry);
        });
    }

    private static void assertHashMapsEqual(Map<String, String> mapA, Map<String, String> mapB) {
        try {
            Assertions.assertThat(mapA).containsExactlyInAnyOrderEntriesOf(mapB);
            System.out.println("The maps are equal.");
        } catch (AssertionError e) {
            for (Map.Entry<String, String> entry : mapA.entrySet()) {
                String key = entry.getKey();
                String valueA = entry.getValue();
                String valueB = mapB.get(key);

                if (!valueA.equals(valueB)) {
                    System.err.println("Inconsistency found at key: " + key);
                }
            }
            throw e;
        }
    }

    @Test
    void givenTestCases_whenLexerUsed_thenCorrectOutput() {
        TESTCASES_INPUT.forEach((key, value) -> {
            var lexer = new Lexer(value);

            TESTCASES_ACTUAL_OUTPUT.put(key, TestUtils.lexerFormatOutput(lexer.scanTokens()));
        });

        assertHashMapsEqual(TESTCASES_ACTUAL_OUTPUT, TESTCASES_CORRECT_OUTPUT);
    }
}
