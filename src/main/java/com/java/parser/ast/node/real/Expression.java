package com.java.parser.ast.node.real;

import com.java.parser.ast.node.ephemeral.ExpressionEphemeral;
import com.java.parser.ast.node.ephemeral.Relation;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Expression extends ExpressionEphemeral {
    private Relation body;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitExpression(this);
    }
}
