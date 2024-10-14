package com.java.parser.ast.node;

import com.java.lexer.Token;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ReferenceTailNode extends ASTNode {
    private final Token identifier;
    private final ASTNode tail;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return null;
    }
}
