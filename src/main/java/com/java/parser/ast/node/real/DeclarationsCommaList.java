package com.java.parser.ast.node.real;

import com.java.parser.ast.visitor.ASTVisitor;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class DeclarationsCommaList extends com.java.parser.ast.node.ephemeral.List {
    private final List<IdentifierWithValue> declarations;

    public DeclarationsCommaList() {
        declarations = new ArrayList<>();
    }

    public DeclarationsCommaList(IdentifierWithValue declaration) {
        declarations = new ArrayList<>();
        declarations.add(declaration);
    }

    public DeclarationsCommaList(IdentifierWithValue declaration, DeclarationsCommaList declarationsCommaList) {
        declarations = new ArrayList<>();
        declarations.add(declaration);
        declarations.addAll(declarationsCommaList.declarations);
    }

    public void append(IdentifierWithValue expression) {
        declarations.add(expression);
    }

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitDeclarationsCommaList(this);
    }
}
