package com.java.parser.ast.node;

import com.java.lexer.Token;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BinaryOp extends ASTNode {
    private final Token operator;
    private final ASTNode left;
    private final ASTNode right;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitBinaryOperation(this);
    }
}
