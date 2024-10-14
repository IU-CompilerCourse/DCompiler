package com.java.parser.ast.node;

import com.java.lexer.Token;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ReferenceAssignNode extends ASTNode {
    private final Token identifier;
    private final ASTNode tail;
    private final ASTNode expression;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return null;
    }
}
