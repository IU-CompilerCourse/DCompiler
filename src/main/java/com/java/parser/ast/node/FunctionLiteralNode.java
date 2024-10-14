package com.java.parser.ast.node;

import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class FunctionLiteralNode extends ASTNode {
    private final ASTNode parameters;
    private final ASTNode funBody;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitFunctionLiteral(this);
    }
}
