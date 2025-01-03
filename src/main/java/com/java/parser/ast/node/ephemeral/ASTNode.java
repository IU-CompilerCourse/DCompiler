package com.java.parser.ast.node.ephemeral;

import com.java.parser.ast.visitor.ASTVisitor;

public abstract class ASTNode {
    public abstract <R> R accept(ASTVisitor<R> visitor);
}
