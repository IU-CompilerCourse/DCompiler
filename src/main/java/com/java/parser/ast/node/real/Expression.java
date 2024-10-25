package com.java.parser.ast.node.real;

import com.java.parser.ast.node.ephemeral.ExpressionEphemeral;
import com.java.parser.ast.node.ephemeral.Relation;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Expression extends ExpressionEphemeral {
    private final Relation body;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitExpression(this);
    }
}