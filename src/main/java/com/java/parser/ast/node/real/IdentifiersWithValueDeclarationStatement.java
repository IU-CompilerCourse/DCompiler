package com.java.parser.ast.node.real;

import com.java.parser.ast.node.ephemeral.DeclarationStatement;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class IdentifiersWithValueDeclarationStatement extends DeclarationStatement {
    private final DeclarationsCommaList declarations;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitIdentifiersWithValueDeclarationStatement(this);
    }
}
