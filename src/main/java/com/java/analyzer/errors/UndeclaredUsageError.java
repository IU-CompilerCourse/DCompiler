package com.java.analyzer.errors;

import com.java.lexer.Token;

public class UndeclaredUsageError extends Error {
    private final Token undeclared;
    int line;

    public final int atLine() {
        return line;
    }

    public UndeclaredUsageError(Token tkn) {
        line = tkn.line();
        undeclared = tkn;
    }

    @Override
    public String error() {
        return String.format("Usage of undeclared name `%s`", undeclared.lexeme());
    }
}
