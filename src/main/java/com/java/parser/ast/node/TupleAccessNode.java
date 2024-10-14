package com.java.parser.ast.node;

import com.java.lexer.Token;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TupleAccessNode extends ASTNode {
    private final Token literal;
    private final Token identifier;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitTupleAccessNode(this);
    }
}
