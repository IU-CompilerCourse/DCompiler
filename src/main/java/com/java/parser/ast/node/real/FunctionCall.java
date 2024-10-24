package com.java.parser.ast.node.real;

import com.java.parser.ast.node.ephemeral.Tail;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class FunctionCall extends Tail {
    private final ExpressionsCommaList expressions;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitFunctionCall(this);
    }
}
