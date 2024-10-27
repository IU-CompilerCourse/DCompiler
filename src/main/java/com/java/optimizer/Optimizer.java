package com.java.optimizer;

import com.java.lexer.Lexer;
import com.java.optimizer.visitor.UnreachableCodeOmitter;
import com.java.optimizer.visitor.UnusedVariablesOptimizer;
import com.java.parser.Parser;
import com.java.parser.ast.visitor.PrintVisitor;
import java.io.IOException;

public class Optimizer {
    public static void main(String[] args) throws IOException {
        var program = """
                var arr := [1, 2, 3];
                var x := [2, 3];

                for item in arr loop
                    var x := item;
                    print (x * x);
                end
                """;
        var p = Parser.makeAST(new Lexer(program));
        p.accept(new PrintVisitor(0));
        p.accept(new UnusedVariablesOptimizer());
        p.accept(new PrintVisitor(0));
    }
}
