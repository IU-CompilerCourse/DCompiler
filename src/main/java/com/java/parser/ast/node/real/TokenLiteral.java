package com.java.parser.ast.node.real;

import com.java.lexer.Token;
import com.java.parser.ast.node.ephemeral.Term;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TokenLiteral extends Term {
    private final Token value;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitTokenLiteral(this);
    }
}
