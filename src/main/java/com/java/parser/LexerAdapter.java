package com.java.parser;

import com.java.lexer.Lexer;
import com.java.lexer.Token;
import java.lang.reflect.Field;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings({"checkstyle:RegexpSinglelineJava", "checkstyle:MultipleStringLiterals"})
@Slf4j
public class LexerAdapter implements Parser.Lexer {
    private final List<Token> tokens;
    private int currentTokenIndex;
    private Token currentToken;

    public LexerAdapter(Lexer lexer) {
        var output = lexer.scanTokens();

        if (!output.lexingErrors().isEmpty()) {
            throw new RuntimeException(
                "\uD83D\uDCDD Errors in lexer: \n" + java.lang.String.join("\n", output.lexingErrors().stream()
                    .map(lexerError -> java.lang.String.format("| --- %s", lexerError.error()))
                    .toList()));
        }

        tokens = output.tokens();
        currentTokenIndex = 0;
    }

    @Override public Object getLVal() {
        return currentToken;
    }

    @Override public int yylex() {
        if (currentTokenIndex == tokens.size()) {
            return YYEOF;
        }

        currentToken = tokens.get(currentTokenIndex++);

        var name = currentToken.type().name();
        int tokenCode = YYerror;
        try {
            Field field = Parser.Lexer.class.getDeclaredField(name);
            tokenCode = (Integer) field.get(Parser.Lexer.class);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.info(e.getMessage());
        }

//        System.out.printf("yylex({%s}) --> {%d}\n", name, tokenCode);

        return tokenCode;
    }

    @Override public void yyerror(String msg) {
        System.out.println(msg);
    }
}
