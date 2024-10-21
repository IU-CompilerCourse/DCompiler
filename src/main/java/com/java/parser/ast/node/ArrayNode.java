package com.java.parser.ast.node;

import com.java.parser.ast.ASTListNode;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ArrayNode extends ASTNode {
    private final ASTListNode elements;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitArray(this);
    }
}
