package com.java.parser.ast.node;

import com.java.lexer.Token;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ParametersNode extends ASTNode {
    private final Token necessaryIdentifier;
    private final ASTNode otherIdentifiers;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return null;
    }
}
