package com.java.semantic;

import com.java.parser.ast.ASTree;
import com.java.semantic.errors.Error;
import java.util.List;
import java.util.Map;

public record AnalyzerOutput(
    ASTree ast,
    Map<String, List<Error>> errors
) {
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (var entry : errors.entrySet()) {
            builder.append(entry.getKey()).append(":\n");
            entry.getValue().forEach(error -> builder.append("-- ERROR -- ").append(error.error()).append("\n"));
            builder.append("\n");
        }

        return builder.toString();
    }
}
