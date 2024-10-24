package com.java.parser;

import com.java.lexer.Lexer;
import com.java.parser.ast.visitor.PrintVisitor;

public class Test {
    public static void main(String[] args) {
        var str = """
            var a := 5;
            var b := 10.5;
            var c := "Hello";
            var d := true;

            a := a + 2;
            b := b - 3.5;
            c := c + " World";
            d := not d;

            print a, b, c, d;

            """;

        try {
            Parser.makeAST(new Lexer(str)).accept(new PrintVisitor(0));
        } catch (Exception e) {
            System.out.println("❌ Invalid\n");
            System.out.println(e.getMessage());
            System.out.println();
        }
    }
}
