package com.java.analyzer.errors;

import com.java.lexer.Token;

public class MultipleLocalDeclarationsError extends Error {

    private final Token undeclared;
    int line;

    public MultipleLocalDeclarationsError(Token tkn) {
        line = tkn.line();
        undeclared = tkn;
    }

    public final int atLine() {
        return line;
    }

    @Override
    public String error() {
        return String.format("Name `%s` in local scope is declared multiple times", undeclared.lexeme());
    }
}
