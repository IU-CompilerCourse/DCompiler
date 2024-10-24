package com.java.parser.ast.node.real;

import com.java.lexer.Token;
import com.java.parser.ast.node.ephemeral.ASTNode;
import com.java.parser.ast.node.ephemeral.UnaryExpression;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UnaryOp extends UnaryExpression {
    private final Token operator;
    private final ASTNode unary; // again too hard to make it typed

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitUnaryOp(this);
    }
}
