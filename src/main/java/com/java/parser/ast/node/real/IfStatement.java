package com.java.parser.ast.node.real;

import com.java.parser.ast.node.ephemeral.ExpressionEphemeral;
import com.java.parser.ast.node.ephemeral.Statement;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class IfStatement extends Statement {
    private final ExpressionEphemeral expression;
    private final StatementsList trueBody;
    private final StatementsList falseBody;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitIfStatement(this);
    }
}
