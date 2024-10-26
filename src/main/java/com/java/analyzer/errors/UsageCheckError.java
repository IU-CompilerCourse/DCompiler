package com.java.analyzer.errors;

public abstract class UsageCheckError {
    int line;
    public UsageCheckError(int line) {
        this.line = line;
    }

    public final int atLine() {
        return line;
    }

    abstract public String error();
}
