package com.java.parser.ast;

import com.java.parser.ast.node.ASTListNode;
import com.java.parser.ast.node.ASTNode;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ASTree extends ASTNode {
    private final ASTListNode nodes;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitAST(this);
    }
}
