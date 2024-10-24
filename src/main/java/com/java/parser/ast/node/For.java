package com.java.parser.ast.node;

import com.java.lexer.Token;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class For extends ASTNode {
    private final Token identifier;
    private final Token typeIndicator;
    private final ASTNode loopBody;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitFor(this);
    }
}
