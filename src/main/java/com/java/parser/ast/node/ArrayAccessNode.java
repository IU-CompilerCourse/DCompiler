package com.java.parser.ast.node;

import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ArrayAccessNode extends ASTNode {
    private final ASTNode expression;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return null;
    }
}
