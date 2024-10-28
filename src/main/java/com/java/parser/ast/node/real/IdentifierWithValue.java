package com.java.parser.ast.node.real;

import com.java.lexer.Token;
import com.java.parser.ast.node.ephemeral.ASTNode;
import com.java.parser.ast.node.ephemeral.ExpressionEphemeral;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class IdentifierWithValue extends ASTNode {
    private final Token identifier;
    private ExpressionEphemeral value;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitIdentifierWithValue(this);
    }
}
