package com.java;

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

    public static Map<String, String> readAllFilesToMemory(@NotNull File[] files) {
        Map<String, String> map = new HashMap<>();

        Arrays.stream(files)
            .forEach(file -> {
                var filename = file.getName();

                try {
                    map.put(filename, Files.readString(file.toPath()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        return map;
    }

    public static void writeToFile(@NotNull Path dir, String filename, @NotBlank String content) {
        var file = dir.resolve(filename);
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
}
