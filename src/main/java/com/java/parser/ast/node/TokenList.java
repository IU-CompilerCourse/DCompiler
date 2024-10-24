package com.java.parser.ast.node;

import com.java.lexer.Token;
import com.java.parser.ast.visitor.ASTVisitor;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class TokenList extends ASTNode {
    private final List<Token> tokens;

    public TokenList() {
        tokens = new ArrayList<>();
    }

    public TokenList(Token token) {
        tokens = new ArrayList<>();
        tokens.add(token);
    }

    public void append(Token token) {
        tokens.add(token);
    }

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitTokenList(this);
    }
}
