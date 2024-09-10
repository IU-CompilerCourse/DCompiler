package com.java.lexer.errors;

public class UnterminatedStringError extends LexerError {
    public UnterminatedStringError(int line) {
        super(line);
    }

    @Override
    public String error() {
        return "Unterminated string literal";
    }
}
