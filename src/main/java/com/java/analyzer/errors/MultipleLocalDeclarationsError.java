package com.java.analyzer.errors;

import com.java.lexer.Token;

public class MultipleLocalDeclarationsError extends Error {

    int line;

    public final int atLine() {
        return line;
    }

    private final Token undeclared;
    public MultipleLocalDeclarationsError(Token tkn) {
        line = tkn.line();
        undeclared = tkn;
    }

    @Override
    public String error() {
        return String.format("Name `%s` in local scope is declared multiple times", undeclared.lexeme());
    }
}
