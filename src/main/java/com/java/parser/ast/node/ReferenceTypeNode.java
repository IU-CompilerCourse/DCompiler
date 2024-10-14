package com.java.parser.ast.node;

import com.java.lexer.Token;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;

@Getter
public class ReferenceTypeNode extends ASTNode {
    private final ASTNode reference;
    private final Token type;

    public ReferenceTypeNode(ASTNode reference) {
        this.reference = reference;
        this.type = null;
    }

    public ReferenceTypeNode(ASTNode reference, Token type) {
        this.reference = reference;
        this.type = type;
    }

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return null;
    }
}
