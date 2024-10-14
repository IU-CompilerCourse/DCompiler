package com.java.parser;

import com.java.lexer.Lexer;
import com.java.parser.ast.visitor.PrintVisitor;
import java.io.IOException;

public class Cringe {
    public static void main(String[] args) throws IOException {
        Lexer lx = new Lexer("var tup := {a := 5, b := \"test\"};\n" +
            "\n" +
            "arr[4] := 4;\n" +
            "tup := tup + {c := true};");
        var ast = Parser.makeAST(lx);
        if (ast != null) {
            ast.nodes().accept(new PrintVisitor(0));
        }
    }
}
