package com.java.parser.ast.node;

import com.java.parser.ast.visitor.ASTVisitor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmptyTailNode extends ASTNode {
    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return null;
    }
}
