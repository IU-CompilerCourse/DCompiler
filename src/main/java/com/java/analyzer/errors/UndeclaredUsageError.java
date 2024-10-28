package com.java.analyzer.errors;

import com.java.lexer.Token;

public class UndeclaredUsageError extends Error {
    private final Token undeclared;
    int line;

    public UndeclaredUsageError(Token tkn) {
        line = tkn.line();
        undeclared = tkn;
    }

    public final int atLine() {
        return line;
    }

    @Override
    public String error() {
        return String.format("Usage of undeclared name `%s`", undeclared.lexeme());
    }
}
