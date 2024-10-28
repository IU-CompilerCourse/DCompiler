package com.java.optimizer;

import com.java.lexer.Lexer;
import com.java.optimizer.visitor.ExpressionsSimplifier;
import com.java.parser.Parser;
import com.java.parser.ast.visitor.PrintVisitor;
import java.io.IOException;

public class Optimizer {
    public static void main(String[] args) throws IOException {
        var program = """
            var arr := [true and true, "2" + "3", 3*8, (25 + 32 - 48) * 0 = 0];
            var temp := arr[1 + 5];
            """;
        var p = Parser.makeAST(new Lexer(program));
        p.accept(new PrintVisitor(0));
        p.accept(new ExpressionsSimplifier());
        p.accept(new PrintVisitor(0));
    }
}
