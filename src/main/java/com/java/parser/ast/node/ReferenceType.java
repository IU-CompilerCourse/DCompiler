package com.java.parser.ast.node;

import com.java.lexer.Token;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;

@Getter
public class ReferenceType extends ASTNode {
    private final ASTNode reference;
    private final Token type;

    public ReferenceType(ASTNode reference) {
        this.reference = reference;
        this.type = null;
    }

    public ReferenceType(ASTNode reference, Token type) {
        this.reference = reference;
        this.type = type;
    }

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return null;
    }
}
