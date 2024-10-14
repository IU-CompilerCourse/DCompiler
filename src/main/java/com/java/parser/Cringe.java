package com.java.parser;

import com.java.lexer.Lexer;
import com.java.parser.ast.visitor.PrintVisitor;
import java.io.IOException;

public class Cringe {
    public static void main(String[] args) throws IOException {
        Lexer lx = new Lexer("var tup := {a := 5, 10, b := 15, c := -256};");
        var ast = Parser.makeAST(lx);
        if (ast != null) {
            ast.accept(new PrintVisitor(0));
        }
    }
}
