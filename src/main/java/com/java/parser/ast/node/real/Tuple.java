package com.java.parser.ast.node.real;

import com.java.parser.ast.node.ephemeral.Term;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Tuple extends Term {
    private final TupleList elements;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitTuple(this);
    }
}
