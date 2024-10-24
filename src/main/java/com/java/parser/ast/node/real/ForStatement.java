package com.java.parser.ast.node.real;

import com.java.lexer.Token;
import com.java.parser.ast.node.ephemeral.LoopStatement;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ForStatement extends LoopStatement {
    private final Token identifier;
    private final Token typeIndicator;
    private final LoopBody loopBody;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitForStatement(this);
    }
}
