package com.java.parser.ast.node;

import com.java.lexer.Token;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VarDeclNode extends ASTNode {
    private final TokenListNode identifiers;
    private final ASTNode initialValue;

    public VarDeclNode(Token identifier, ASTNode initialValue) {
        this.identifiers = new TokenListNode(identifier);
        this.initialValue = initialValue;
    }

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitVarDeclNode(this);
    }
}
