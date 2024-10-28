package com.java.semantic;

import com.java.parser.ast.ASTree;
import com.java.semantic.checkers.ContextCheckerVisitor;
import com.java.semantic.checkers.UsageChecksVisitor;
import com.java.semantic.errors.Error;
import com.java.semantic.optimizers.ExpressionsSimplifier;
import com.java.semantic.optimizers.UnreachableCodeOmitter;
import com.java.semantic.optimizers.UnusedVariablesOptimizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Analyzer {
    private final Map<String, List<Error>> map = new HashMap<>();

    public AnalyzerOutput analyze(ASTree tree) {
        map.clear();

        check(tree);
        if (isErrorsFound()) {
            return new AnalyzerOutput(tree, map);
        }

        optimise(tree);
        return new AnalyzerOutput(tree, map);
    }

    public void check(ASTree tree) {
        var contextCheckerVisitor = new ContextCheckerVisitor();
        map.put(contextCheckerVisitor.getClass().getSimpleName(), tree.accept(contextCheckerVisitor));

        var usageChecksVisitor = new UsageChecksVisitor();
        map.put(usageChecksVisitor.getClass().getSimpleName(), tree.accept(usageChecksVisitor));
    }

    public void optimise(ASTree tree) {
        var unreachableCodeOmitter = new UnreachableCodeOmitter();
        tree.accept(unreachableCodeOmitter);

        var unusedVariablesOptimizer = new UnusedVariablesOptimizer();
        tree.accept(unusedVariablesOptimizer);

        var expressionsSimplifier = new ExpressionsSimplifier();
        tree.accept(expressionsSimplifier);
        map.put(expressionsSimplifier.getClass().getSimpleName(), expressionsSimplifier.getErrors());
    }

    private boolean isErrorsFound() {
        return !map.values().stream()
            .filter(list -> !list.isEmpty())
            .toList().isEmpty();
    }
}
