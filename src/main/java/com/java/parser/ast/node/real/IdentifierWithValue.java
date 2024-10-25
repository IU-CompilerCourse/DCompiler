package com.java.parser.ast.node.real;

import com.java.lexer.Token;
import com.java.parser.ast.node.ephemeral.ASTNode;
import com.java.parser.ast.node.ephemeral.ExpressionEphemeral;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class IdentifierWithValue extends ASTNode {
    private final Token identifier;
    private final ExpressionEphemeral value;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitIdentifierWithValue(this);
    }
}
