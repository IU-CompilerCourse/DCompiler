package com.java.evaluation;

import com.java.lexer.Lexer;
import com.java.parser.Parser;
import com.java.parser.ast.visitor.PrintVisitorV2;
import com.java.semantic.Analyzer;
import java.io.IOException;

public class Eval {
    private static final String program = """
        var a := 5, b := 3;

        print a, b;

        if true then
            var a := 10;
            print a, b;
        end

        print a, b;

                 """;
    public static void main(String[] args) throws IOException {

        var ast = Parser.makeAST(new Lexer(program));
        ast.accept(new PrintVisitorV2(0));
        // ast = new Analyzer().analyze(ast).ast();
        // ast.accept(new PrintVisitorV2(0));
        ast.accept(new Evaluator());
    }
}
