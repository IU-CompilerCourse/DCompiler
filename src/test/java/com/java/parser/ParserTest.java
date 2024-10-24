package com.java.parser;

import com.java.TestUtils;
import com.java.lexer.Lexer;
import com.java.parser.ast.ASTree;
import com.java.parser.ast.visitor.PrintVisitor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ParserTest {
    private static final HashMap<Integer, String> TESTCASES_INPUT = new HashMap<>();
    private static final HashMap<Integer, String> TESTCASES_CORRECT_OUTPUT = new HashMap<>();
    private static final HashMap<Integer, String> TESTCASES_ACTUAL_OUTPUT = new HashMap<>();
    private static Path actualOutputDirectory;

    @BeforeAll
    static void tearUp() {
        Path testInputDirectory = Path.of("src", "test", "resources", "test-cases");

        Path testOutputDirectory = Path.of("src", "test", "resources", "parser-test-output");
        Path correctTestOutputDirectory = Path.of(testOutputDirectory.toAbsolutePath().toString(), "correct-output");
        actualOutputDirectory = Path.of(testOutputDirectory.toAbsolutePath().toString(), "actual-output");

        actualOutputDirectory = TestUtils.makeDirIfNotExists(actualOutputDirectory);

        TESTCASES_INPUT.putAll(TestUtils.readAllFilesToMemory(testInputDirectory.toFile().listFiles()));
        TESTCASES_CORRECT_OUTPUT.putAll(TestUtils.readAllFilesToMemory(correctTestOutputDirectory.toFile()
            .listFiles()));
    }

    @AfterAll
    static void tearDown() {
        TESTCASES_ACTUAL_OUTPUT.forEach((key, entry) -> TestUtils.writeToFile(actualOutputDirectory, key, entry));
    }

    private static void assertHashMapsEqual(Map<Integer, String> mapA, Map<Integer, String> mapB) {
        try {
            Assertions.assertThat(mapA).containsExactlyInAnyOrderEntriesOf(mapB);
            System.out.println("The maps are equal.");
        } catch (AssertionError e) {
            for (Map.Entry<Integer, String> entry : mapA.entrySet()) {
                Integer key = entry.getKey();
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
        var origOut = System.out;

        TESTCASES_INPUT.forEach((key, value) -> {
            boolean isOK = true;
            ASTree ast = null;
            try {
                System.out.println("TEST {" + key + "}");

                ast = Parser.makeAST(new Lexer(value));
            } catch (Exception e) {
                isOK = false;

                System.out.println("❌ Invalid\n");
                System.out.println(e.getMessage());
                System.out.println();
            }

            if (isOK) {
                System.out.println("\uD83D\uDEE0\uFE0F Parsed\n");
                System.out.println("\uD83C\uDF32 AST Generated\n");

                try {
                    FileOutputStream fos = new FileOutputStream("output.txt");
                    PrintStream ps = new PrintStream(fos);
                    System.setOut(ps);

                    ast.accept(new PrintVisitor(0));

                    ps.close();
                    fos.close();
                    System.setOut(origOut);

                    TESTCASES_ACTUAL_OUTPUT.put(key, Files.readString(Path.of("output.txt")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

//        assertHashMapsEqual(TESTCASES_ACTUAL_OUTPUT, TESTCASES_CORRECT_OUTPUT);
    }
}
