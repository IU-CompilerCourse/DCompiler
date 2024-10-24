package com.java.parser.ast.node;

import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FunctionLiteral extends ASTNode {
    private final IdentifierList parameters;
    private final ASTNode funBody;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitFunctionLiteral(this);
    }
}
