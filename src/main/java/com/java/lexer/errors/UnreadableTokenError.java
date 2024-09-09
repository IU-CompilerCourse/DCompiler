package com.java.lexer.errors;

public class UnreadableTokenError extends LexerError {
    private final String tokenStart;

    public UnreadableTokenError(int line, String tokenStart) {
        super(line);
        this.tokenStart = tokenStart;
    }

    public UnreadableTokenError(int line, char tokenStart) {
        super(line);
        this.tokenStart = Character.toString(tokenStart);
    }

    @Override
    public String error() {
        return "Cannot read token starting as \"%s\"".formatted(tokenStart);
    }
}
