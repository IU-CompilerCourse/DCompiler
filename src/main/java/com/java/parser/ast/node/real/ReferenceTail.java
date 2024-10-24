package com.java.parser.ast.node.real;

import com.java.lexer.Token;
import com.java.parser.ast.node.ephemeral.Tail;
import com.java.parser.ast.node.ephemeral.Term;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ReferenceTail extends Term {
    private final Token identifier;
    private final Tail tail;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitReferenceTail(this);
    }
}
