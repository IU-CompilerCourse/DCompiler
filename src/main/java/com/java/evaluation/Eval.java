package com.java.evaluation;

import com.java.lexer.Lexer;
import com.java.parser.Parser;
import com.java.semantic.Analyzer;
import java.io.IOException;

@SuppressWarnings("all")
public class Eval {
    private static final String PROGRAM = """
        var a := 5 * 5 + 100;
        print a;
        """;

    public static void main(String[] args) throws IOException {

        var ast = Parser.makeAST(new Lexer(PROGRAM));
//        System.out.println(ast);
//         ast.accept(new PrintVisitorV2(0));
        var temp = new Analyzer().analyze(ast);
        ast = temp.ast();
//        ast.accept(new PrintVisitorV2(0));
        ast.accept(new Evaluator());
    }
}
