package com.java.lexer;

import com.java.lexer.errors.LexerError;
import java.util.List;

public record LexerOutput(List<LexerError> lexingErrors, List<Token> tokens) {
}
