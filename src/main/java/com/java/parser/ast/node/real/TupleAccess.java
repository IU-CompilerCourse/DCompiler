package com.java.parser.ast.node.real;

import com.java.lexer.Token;
import com.java.parser.ast.node.ephemeral.Tail;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TupleAccess extends Tail {
    private final Token literal;
    private final Token identifier;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitTupleAccess(this);
    }
}
