package com.java.parser.ast.node;

import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LoopBodyNode extends ASTNode {
    private final ASTNode loopBody;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return null;
    }
}