package com.java.parser.ast.node.real;

import com.java.lexer.Token;
import com.java.parser.ast.node.ephemeral.DeclarationStatement;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OnlyIdentifiersDeclarationStatement extends DeclarationStatement {
    private final IdentifiersCommaList identifiers;

    public OnlyIdentifiersDeclarationStatement(Token identifier) {
        this.identifiers = new IdentifiersCommaList(identifier);
    }

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitOnlyIdentifiersDeclarationStatement(this);
    }
}
