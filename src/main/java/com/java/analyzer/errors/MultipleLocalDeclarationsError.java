package com.java.analyzer.errors;

import com.java.lexer.Token;

public class MultipleLocalDeclarationsError extends UsageCheckError {
    private final Token undeclared;
    public MultipleLocalDeclarationsError(Token tkn) {
        super(tkn.line());
        undeclared = tkn;
    }

    @Override
    public String error() {
        return String.format("Name `%s` in local scope is declared multiple times", undeclared.lexeme());
    }
}
