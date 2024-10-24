/**
 * To improve type safety in classes, such as for a function where parameters must be identifiers,
 * use the new IdentifierList class , which explicitly states that only identifiers are expected, not any AST nodes.
 */

package com.java.parser.ast.node;

import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class IdentifierList extends ASTNode {
    private final List<Token> identifiers;

    public IdentifierList() {
        this.identifiers = new ArrayList<>();
    }

    public IdentifierList(Token identifier) {
        this.identifiers = new ArrayList<>();
        this.identifiers.add(identifier);
    }

    public void append(Token identifier) {
        this.identifiers.add(identifier);
    }

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitIdentifierList(this);
    }
}
