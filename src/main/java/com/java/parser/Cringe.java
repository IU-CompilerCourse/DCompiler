package com.java.parser;

import com.java.lexer.Lexer;
import java.io.IOException;

public class Cringe {
    public static void main(String[] args) throws IOException {
        Lexer lx = new Lexer("var a := 1 + 3 + 5;");
        var ast = Parser.makeAST(lx);


    }
}
