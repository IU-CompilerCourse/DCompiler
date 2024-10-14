package com.java.parser.ast.node;

import com.java.lexer.Token;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TokenListNode extends ASTNode {
    private final List<Token> tokens;

    public TokenListNode() {
        tokens = new ArrayList<>();
    }

    public TokenListNode(Token token) {
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
