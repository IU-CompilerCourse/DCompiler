package com.java.parser;

import com.java.lexer.Lexer;
import com.java.lexer.Token;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class LexerAdapter implements Parser.Lexer {
    private final List<Token> tokens;
    private int currentTokenIndex;
    private Token currentToken;

    public LexerAdapter(Lexer lexer) {
        var output = lexer.scanTokens();

        tokens = output.tokens();
        currentTokenIndex = 0;
    }

    @Override public Object getLVal() {
        return currentToken;
    }

    @Override public int yylex() throws IOException {
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
            e.printStackTrace();
        }

        System.out.printf("yylex({%s}) --> {%d}\n", name, tokenCode);

        return tokenCode;
    }

    @Override public void yyerror(String msg) {
        System.out.println(msg);
    }
}
