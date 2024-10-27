package com.java.analyzer.errors;

public class ReturnContextError extends Error {
    public ReturnContextError() {
    }

    @Override
    public String error() {
        return "'return' keyword is used not in the context of function body";
    }
}
