package com.java.evaluation;

import com.java.lexer.Lexer;
import com.java.parser.Parser;
import com.java.parser.ast.visitor.PrintVisitorV2;
import java.io.IOException;

public class Eval {
    private static final String program = """
                var arr := [1, 2];

                f(x)[2].a[3] := 3;

                print arr[2];
                """;
    public static void main(String[] args) throws IOException {

        var ast = Parser.makeAST(new Lexer(program));
        ast.accept(new PrintVisitorV2(0));
        // ast = new Analyzer().analyze(ast).ast();
        // ast.accept(new PrintVisitorV2(0));
        ast.accept(new Evaluator());
    }
}
