package com.java.optimizer;

import com.java.lexer.Lexer;
import com.java.optimizer.visitor.UnreachableCodeOmitter;
import com.java.optimizer.visitor.UnusedVariablesOptimizer;
import com.java.parser.Parser;
import com.java.parser.ast.visitor.PrintVisitor;
import com.java.parser.ast.visitor.PrintVisitorV2;
import java.io.IOException;

public class Optimizer {
    public static void main(String[] args) throws IOException {
        var program = """
                var arr := [1, 2, 3];
                var x := [2, 3];
                var y;
                """;
        var p = Parser.makeAST(new Lexer(program));
        p.accept(new PrintVisitorV2(0));
        p.accept(new UnusedVariablesOptimizer());
        p.accept(new PrintVisitorV2(0));
    }
}
