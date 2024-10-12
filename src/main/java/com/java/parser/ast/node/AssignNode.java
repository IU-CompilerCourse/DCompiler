package com.java.parser.ast.node;

import com.java.lexer.Token;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AssignNode extends ASTNode {
    private final Token identifier;
    private final ASTNode expression;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitAssignNode(this);
    }
}
