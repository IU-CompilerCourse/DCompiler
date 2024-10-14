package com.java.parser;

import com.java.lexer.Lexer;
import java.io.IOException;

public class Cringe {
    public static void main(String[] args) throws IOException {
        Lexer lx = new Lexer("if 1 + 2 then 1 + 3 end;");
        var ast = Parser.makeAST(lx);
    }
}
