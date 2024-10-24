package com.java.parser.ast.node.real;

import com.java.parser.ast.node.ephemeral.Tail;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmptyTail extends Tail {
    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitEmptyTail(this);
    }
}
