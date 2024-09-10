package com.java.lexer.errors;

public class MultilineStringError extends LexerError {
    public MultilineStringError(int line) {
        super(line);
    }

    @Override
    public String error() {
        return "Multiline string literals are not allowed";
    }
}
