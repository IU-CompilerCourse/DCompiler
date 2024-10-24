package com.java.parser.ast.node.real;

import com.java.lexer.Token;
import com.java.parser.ast.visitor.ASTVisitor;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class IdentifiersCommaList extends com.java.parser.ast.node.ephemeral.List {
    private final List<Token> tokens;

    public IdentifiersCommaList() {
        tokens = new ArrayList<>();
    }

    public IdentifiersCommaList(Token token) {
        tokens = new ArrayList<>();
        tokens.add(token);
    }

    public void append(Token token) {
        tokens.add(token);
    }

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitIdentifiersCommaList(this);
    }
}
