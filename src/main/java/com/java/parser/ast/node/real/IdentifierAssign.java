package com.java.parser.ast.node.real;

import com.java.lexer.Token;
import com.java.parser.ast.node.ephemeral.AssignmentStatement;
import com.java.parser.ast.node.ephemeral.ExpressionEphemeral;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IdentifierAssign extends AssignmentStatement {
    private final Token identifier;
    private final ExpressionEphemeral expression;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitIdentifierAssign(this);
    }
}
