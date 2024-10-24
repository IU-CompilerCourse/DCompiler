package com.java.parser.ast.node;

import com.java.parser.ast.ASTList;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Array extends ASTNode {
    private final ASTList elements;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitArray(this);
    }
}
