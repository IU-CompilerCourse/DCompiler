package com.java.semantic.errors;

import com.java.lexer.TokenType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor public class CorruptedOpException implements Error {
    private final String leftValue;
    private final String rightValue;
    private final TokenType op;
    private final TokenType type;

    @Override public String error() {
        return "Sign '" + op + "' is used incorrectly: Left={" + leftValue + "} - Right{" + rightValue
               + "} - Final Type{" + type + "}";
    }
}
