package com.java.parser.ast.node.real;

import com.java.parser.ast.node.ephemeral.ExpressionEphemeral;
import com.java.parser.ast.node.ephemeral.LoopStatement;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class WhileStatement extends LoopStatement {
    private ExpressionEphemeral expression;
    private final LoopBody loopBody;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitWhileStatement(this);
    }
}
