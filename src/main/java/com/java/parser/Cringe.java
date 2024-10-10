package com.java.parser;

import com.java.lexer.Lexer;
import java.io.IOException;

public class Cringe {
    public static void main(String[] args) throws IOException {
        Lexer lx = new Lexer("()()(())");
        Parser ps = new Parser(new LexerAdapter(lx));
        var b = ps.parse();
        System.out.println(b);
    }
}
