package com.java.lexer.errors;

public abstract class LexerError {
    protected int line;

    public LexerError(int line) {
        this.line = line;
    }

    public final int atLine() {
        return line;
    }

    abstract public String error();
}
