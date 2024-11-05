package com.java.evaluation;

import com.java.lexer.Lexer;
import com.java.parser.Parser;
import com.java.parser.ast.visitor.PrintVisitorV2;
import com.java.semantic.Analyzer;
import java.io.IOException;

@SuppressWarnings("all")
public class Eval {
    private static final String PROGRAM = """
        var year := 2004;
        if not (year <= 2004) then
            print "Dick";
        end
        """;

    public static void main(String[] args) throws IOException {

        var ast = Parser.makeAST(new Lexer(PROGRAM));
//         ast.accept(new PrintVisitorV2(0));
        var temp = new Analyzer().analyze(ast);
        ast = temp.ast();
        ast.accept(new PrintVisitorV2(0));
        ast.accept(new Evaluator());
    }
}
