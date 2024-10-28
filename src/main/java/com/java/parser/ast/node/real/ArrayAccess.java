package com.java.parser.ast.node.real;

import com.java.parser.ast.node.ephemeral.ExpressionEphemeral;
import com.java.parser.ast.node.ephemeral.Tail;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArrayAccess extends Tail {
    private ExpressionEphemeral expression;

    public ArrayAccess(ExpressionEphemeral expression) {
        this.expression = expression;
    }

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitArrayAccess(this);
    }
}
