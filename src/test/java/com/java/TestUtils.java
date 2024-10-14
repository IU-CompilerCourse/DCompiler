package com.java;

import com.java.lexer.LexerOutput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class TestUtils {
    public static Path makeDirIfNotExists(@NotNull Path dir) {
        if (!Files.isDirectory(dir)) {
            try {
                return Files.createDirectory(dir.toAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return dir;
    }

    public static Map<Integer, String> readAllFilesToMemory(@NotNull File[] files) {
        Map<Integer, String> map = new HashMap<>();

        Arrays.stream(files)
            .forEach(file -> {
                var filename = file.getName();
                int testNum = Integer.parseInt(filename.substring("test".length(), filename.lastIndexOf('.')));

                try {
                    map.put(testNum, Files.readString(file.toPath()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        return map;
    }

    public static void writeToFile(@NotNull Path dir, int testNum, @NotBlank String content) {
        var file = dir.resolve("test" + testNum + ".txt");
        var fileExists = Files.exists(file);

        if (!fileExists) {
            try {
                Files.createFile(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            Files.writeString(file, content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String lexerFormatOutput(@NotNull LexerOutput output) {
        StringBuilder sb = new StringBuilder();

        sb.append("TOKENS\r\n\r\n");
        for (var token : output.tokens()) {
            sb.append(token).append("\r\n");
        }

        sb.append("\r\nERRORS\r\n\r\n");
        for (var error : output.lexingErrors()) {
            sb.append(String.format("Found error: '%s' at line %d", error.error(), error.atLine())).append("\r\n");
        }

        return sb.toString();
    }
}
