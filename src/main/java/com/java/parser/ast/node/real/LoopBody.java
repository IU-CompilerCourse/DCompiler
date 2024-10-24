package com.java.parser.ast.node.real;

import com.java.parser.ast.node.ephemeral.ASTNode;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LoopBody extends ASTNode {
    private final StatementsList loopBody;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitLoopBody(this);
    }
}
