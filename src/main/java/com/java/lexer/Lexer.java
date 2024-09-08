package com.java.lexer;

import com.java.lexer.errors.LexerError;
import com.java.lexer.errors.MultilineStringError;
import com.java.lexer.errors.UnreadableTokenError;
import com.java.lexer.errors.UnterminatedStringError;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Lexer {
    private static final Map<String, TokenType> keywords;
    static {
        keywords = new HashMap<>();

        keywords.put("and", TokenType.And);
        keywords.put("or", TokenType.Or);
        keywords.put("xor", TokenType.Xor);
        keywords.put("not", TokenType.Not);

        keywords.put("is", TokenType.Is);

        keywords.put("int", TokenType.Int);
        keywords.put("real", TokenType.Real);
        keywords.put("string", TokenType.String);
        keywords.put("bool", TokenType.Bool);
        keywords.put("empty", TokenType.Empty);
        keywords.put("func", TokenType.Func);

        keywords.put("var", TokenType.Var);

        keywords.put("readInt", TokenType.ReadInt);
        keywords.put("readReal", TokenType.ReadReal);
        keywords.put("readString", TokenType.ReadString);
        keywords.put("print", TokenType.Print);

        keywords.put("return", TokenType.Return);
        keywords.put("if", TokenType.If);
        keywords.put("then", TokenType.Then);
        keywords.put("else", TokenType.Else);
        keywords.put("end", TokenType.End);
        keywords.put("while", TokenType.While);
        keywords.put("for", TokenType.For);
        keywords.put("in", TokenType.In);
        keywords.put("loop", TokenType.Loop);
    }

    private static final Map<String, TokenType> boolLiterals;
    static {
        boolLiterals = new HashMap<>();
        boolLiterals.put("false", TokenType.BooleanLiteral);
        boolLiterals.put("true",TokenType.BooleanLiteral);
    }

    private final String src;
    private final List<Token> tokens = new ArrayList<>();
    private final List<LexerError> errors = new ArrayList<>();

    private int current = 0;
    private int start = 0;

    private int line = 1;

    public Lexer(String src) {
        this.src = src;
    }

    public LexerOutput scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }
        addToken(TokenType.EOF);
        return new LexerOutput(errors, tokens);
    }

    private void addToken(TokenType type, Object literal) {
        String lexeme = getLexeme();
        tokens.add(new Token(type, lexeme, literal, line));
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void scanToken() {
        char c = eat();
        switch (c) {
            // brackets, parenthesis, braces
            case '(': addToken(TokenType.OpenParen); break;
            case ')': addToken(TokenType.CloseParen); break;
            case '{': addToken(TokenType.OpenBrace); break;
            case '}': addToken(TokenType.CloseBrace); break;
            case '[': addToken(TokenType.OpenBracket); break;
            case ']': addToken(TokenType.CloseBracket); break;

            // operators
            case '.': addToken(TokenType.Dot); break;
            case ',': addToken(TokenType.Comma); break;
            case '-': addToken(TokenType.Minus); break;
            case '+': addToken(TokenType.Plus); break;
            case ';': addToken(TokenType.Semicolon); break;
            case '*': addToken(TokenType.Star); break;
            case '=':
                addToken(match('>') ? TokenType.Arrow : TokenType.Equal);
                break;
            case '<':
                addToken(match('=') ? TokenType.LessEqual : TokenType.Less);
                break;
            case '>':
                addToken(match('=') ? TokenType.GreaterEqual : TokenType.Greater);
                break;
            case '/':
                if (match('=')) {
                    addToken(TokenType.NotEqual);
                } else if (match('/')) { // comment
                    comment();
                } else {
                    addToken(TokenType.Slash);
                }
                break;
            case ':':
                if (match('=')) {
                    addToken(TokenType.Assignment);
                } else {
                    errors.add(new UnreadableTokenError(line, getLexeme()));
                }
                break;

            // whitespaces & new line character
            case ' ':
            case '\r':
            case '\t':
                break;
            case '\n':
                line++;
                break;

            // quotes
            case '"':
                stringLiteral();
                break;

            default:
                if (isDigit(c)) {
                    numberLiteral();
                } else if (isAlpha(c)) {
                    identifier();
                } else {
                    errors.add(new UnreadableTokenError(line, getLexeme()));
                }
                break;
        }
    }

    private void identifier() {
        while (isAlphaNumeric(peek())) {
            eat();
        }

        var id = getLexeme();
        // bool literal
        if (boolLiterals.containsKey(id)) {
            var literal = id.equals("true");
            addToken(TokenType.BooleanLiteral, literal);
        } else if (keywords.containsKey(id)) {
            var keyword = keywords.get(id);
            addToken(keyword);
        } else {
            addToken(TokenType.Identifier);
        }
    }

    private void numberLiteral() {
        while (isDigit(peek())) {
            eat();
        }

        if (peek() == '.' && isDigit(peekNext())) {
            do {
                eat();
            } while (isDigit(peek()));
            addToken(TokenType.DoubleLiteral, Double.parseDouble(getLexeme()));
            return;
        }

        addToken(TokenType.IntLiteral, Integer.parseInt(getLexeme()));
    }

    private void stringLiteral() {
        while (peek() != '"' && !isAtEnd()) {
            // escaping
            if (peek() == '\\') {
                eat();
                eat();
                continue;
            }

            // prohibit multiline strings
            if (peek() == '\n') {
                errors.add(new MultilineStringError(line));
                return;
            }

            eat();
        }

        if (isAtEnd()) {
            errors.add(new UnterminatedStringError(line));
            return;
        }

        eat();
        String literal = src.substring(start + 1, current - 1);
        addToken(TokenType.StringLiteral, literal);
    }

    private void comment() {
        while (peek() != '\n' && !isAtEnd()) {
            eat();
        }
    }

    private char eat() {
        return src.charAt(current++);
    }

    private char peek() {
        if (isAtEnd()) {
            return '\0';
        }
        return src.charAt(current);
    }

    private char peekNext() {
        if (current + 1 >= src.length()) {
            return '\0';
        }
        return src.charAt(current + 1);
    }

    private boolean match(char exp) {
        if (isAtEnd()) {
            return false;
        }
        if (src.charAt(current) != exp) {
            return false;
        }
        current++;
        return true;
    }

    private boolean isAtEnd() {
        return current >= src.length();
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
            (c >= 'A' && c <= 'Z') ||
            c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private String getLexeme() {
        return src.substring(start, current);
    }
}
