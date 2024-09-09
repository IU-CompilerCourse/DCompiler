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
    private static final Map<String, TokenType> KEYWORDS;
    private static final Map<String, TokenType> BOOL_LITERALS;

    static {
        KEYWORDS = new HashMap<>();

        KEYWORDS.put("and", TokenType.And);
        KEYWORDS.put("or", TokenType.Or);
        KEYWORDS.put("xor", TokenType.Xor);
        KEYWORDS.put("not", TokenType.Not);

        KEYWORDS.put("is", TokenType.Is);

        KEYWORDS.put("int", TokenType.Int);
        KEYWORDS.put("real", TokenType.Real);
        KEYWORDS.put("string", TokenType.String);
        KEYWORDS.put("bool", TokenType.Bool);
        KEYWORDS.put("empty", TokenType.Empty);
        KEYWORDS.put("func", TokenType.Func);

        KEYWORDS.put("var", TokenType.Var);

        KEYWORDS.put("readInt", TokenType.ReadInt);
        KEYWORDS.put("readReal", TokenType.ReadReal);
        KEYWORDS.put("readString", TokenType.ReadString);
        KEYWORDS.put("print", TokenType.Print);

        KEYWORDS.put("return", TokenType.Return);
        KEYWORDS.put("if", TokenType.If);
        KEYWORDS.put("then", TokenType.Then);
        KEYWORDS.put("else", TokenType.Else);
        KEYWORDS.put("end", TokenType.End);
        KEYWORDS.put("while", TokenType.While);
        KEYWORDS.put("for", TokenType.For);
        KEYWORDS.put("in", TokenType.In);
        KEYWORDS.put("loop", TokenType.Loop);
    }

    static {
        BOOL_LITERALS = new HashMap<>();
        BOOL_LITERALS.put(Boolean.FALSE.toString(), TokenType.BooleanLiteral);
        BOOL_LITERALS.put(Boolean.TRUE.toString(), TokenType.BooleanLiteral);
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

    @SuppressWarnings("checkstyle:CyclomaticComplexity") private void scanToken() {
        char c = eat();
        switch (c) {
            // brackets, parenthesis, braces
            case '(':
                addToken(TokenType.OpenParen);
                break;
            case ')':
                addToken(TokenType.CloseParen);
                break;
            case '{':
                addToken(TokenType.OpenBrace);
                break;
            case '}':
                addToken(TokenType.CloseBrace);
                break;
            case '[':
                addToken(TokenType.OpenBracket);
                break;
            case ']':
                addToken(TokenType.CloseBracket);
                break;

            // operators
            case '.':
                addToken(TokenType.Dot);
                break;
            case ',':
                addToken(TokenType.Comma);
                break;
            case '-':
                addToken(TokenType.Minus);
                break;
            case '+':
                addToken(TokenType.Plus);
                break;
            case ';':
                addToken(TokenType.Semicolon);
                break;
            case '*':
                addToken(TokenType.Star);
                break;
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
        if (BOOL_LITERALS.containsKey(id)) {
            var literal = id.equals(Boolean.TRUE.toString());
            addToken(TokenType.BooleanLiteral, literal);
        } else if (KEYWORDS.containsKey(id)) {
            var keyword = KEYWORDS.get(id);
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

    @SuppressWarnings("checkstyle:ReturnCount") private void stringLiteral() {
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
        return (c >= 'a' && c <= 'z')
               || (c >= 'A' && c <= 'Z')
               || c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private String getLexeme() {
        var lexeme = src.substring(start, current);
        return lexeme.equals("\n") ? "" : lexeme;
    }
}
