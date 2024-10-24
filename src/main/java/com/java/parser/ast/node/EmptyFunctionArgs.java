package com.java.parser.ast.node;

import com.java.parser.ast.ASTNode;
import com.java.parser.ast.visitor.ASTVisitor;

public class EmptyFunctionArgs extends ASTNode {

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitEmptyFunctionArgs(this);
    }
}
