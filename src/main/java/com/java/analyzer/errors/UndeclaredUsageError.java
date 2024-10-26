package com.java.analyzer.errors;

import com.java.lexer.Token;

public class UndeclaredUsageError extends UsageCheckError {
    private final Token undeclared;
    public UndeclaredUsageError(Token tkn) {
        super(tkn.line());
        undeclared = tkn;
    }

    @Override
    public String error() {
        return String.format("Usage of undeclared name `%s`", undeclared.lexeme());
    }
}
