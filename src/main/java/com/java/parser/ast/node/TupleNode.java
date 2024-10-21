package com.java.parser.ast.node;

import com.java.parser.ast.TupleListNode;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TupleNode extends ASTNode {
    private final TupleListNode elements;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitTuple(this);
    }
}
