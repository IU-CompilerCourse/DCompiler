package com.java.parser.ast.node;

import com.java.lexer.Token;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UnaryOp extends ASTNode {
    private final Token operator;
    private final ASTNode unary;
    private final ASTNode typeIndicator;

    public UnaryOp(Token operator, ASTNode unary) {
        this.operator = operator;
        this.unary = unary;
        this.typeIndicator = null;
    }

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitUnaryOperation(this);
    }
}
