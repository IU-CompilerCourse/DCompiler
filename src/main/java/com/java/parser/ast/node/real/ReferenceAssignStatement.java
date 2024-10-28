package com.java.parser.ast.node.real;

import com.java.lexer.Token;
import com.java.parser.ast.node.ephemeral.AssignmentStatement;
import com.java.parser.ast.node.ephemeral.ExpressionEphemeral;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ReferenceAssignStatement extends AssignmentStatement {
    private final Token identifier;
    private final AccessTailList tail;
    private ExpressionEphemeral expression;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitReferenceAssignStatement(this);
    }
}
