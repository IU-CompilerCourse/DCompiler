package com.java.parser.ast.node.real;

import com.java.lexer.Token;
import com.java.parser.ast.node.ephemeral.Term;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;

@Getter
public class ReferenceType extends Term {
    private final ReferenceTail reference;
    private final Token type;

    public ReferenceType(ReferenceTail reference) {
        this.reference = reference;
        this.type = null;
    }

    public ReferenceType(ReferenceTail reference, Token type) {
        this.reference = reference;
        this.type = type;
    }

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitReferenceType(this);
    }
}
