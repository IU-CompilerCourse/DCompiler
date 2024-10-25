package com.java.parser.ast;

import com.java.parser.ast.node.ephemeral.ASTNode;
import com.java.parser.ast.node.real.StatementsList;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ASTree extends ASTNode {
    private final StatementsList nodes;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitAST(this);
    }
}
