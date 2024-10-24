package com.java.parser.ast.node;

import com.java.parser.ast.ASTNode;
import com.java.parser.ast.visitor.ASTVisitor;

public class EmptyStatementList extends ASTNode {

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitEmptyStatementList(this);
    }
}
