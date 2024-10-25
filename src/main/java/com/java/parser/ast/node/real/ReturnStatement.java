package com.java.parser.ast.node.real;

import com.java.parser.ast.node.ephemeral.ExpressionEphemeral;
import com.java.parser.ast.node.ephemeral.Statement;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ReturnStatement extends Statement {
    private final ExpressionEphemeral expression;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitReturnStatement(this);
    }
}
