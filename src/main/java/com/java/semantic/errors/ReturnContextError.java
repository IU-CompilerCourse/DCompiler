package com.java.semantic.errors;

public class ReturnContextError implements Error {
    public ReturnContextError() {
    }

    @Override
    public String error() {
        return "'return' keyword is used not in the context of function body";
    }
}
