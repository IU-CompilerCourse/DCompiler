package com.java.evaluation;

import com.java.lexer.Lexer;
import com.java.parser.Parser;
import com.java.parser.ast.visitor.PrintVisitorV2;
import java.io.IOException;

public class Eval {
    private static final String program = """
                var n;
                readInt n;

                var arr := [];

                var i := 1;
                while i <= n loop
                    var x;
                    readInt x;
                    arr := arr + x;
                    i := i + 1;
                end

                print arr;
                """;
    public static void main(String[] args) throws IOException {

        var ast = Parser.makeAST(new Lexer(program));
        // ast.accept(new PrintVisitorV2(0));
        // ast = new Analyzer().analyze(ast).ast();
        // ast.accept(new PrintVisitorV2(0));
        ast.accept(new Evaluator());
    }
}
