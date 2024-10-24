package com.java.parser.ast.node;

import com.java.lexer.Token;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VarDecl extends ASTNode {
    private final TokenList identifiers;
    private final ASTNode initialValue;

    public VarDecl(Token identifier, ASTNode initialValue) {
        this.identifiers = new TokenList(identifier);
        this.initialValue = initialValue;
    }

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitVarDecl(this);
    }
}
