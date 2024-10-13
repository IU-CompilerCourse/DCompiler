package com.java.parser.ast.node;

import com.java.lexer.Token;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VarNode extends ASTNode {
    private final Token name;
    private final ASTNode tailList;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitVarNode(this);
    }
}
