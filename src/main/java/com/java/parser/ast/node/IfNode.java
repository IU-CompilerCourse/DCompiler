package com.java.parser.ast.node;

import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class IfNode extends ASTNode {
    private final ASTNode expression;
    private final ASTNode trueBody;
    private final ASTNode falseBody;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return null;
    }
}
