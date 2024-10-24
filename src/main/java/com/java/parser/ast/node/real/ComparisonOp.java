package com.java.parser.ast.node.real;

import com.java.lexer.Token;
import com.java.parser.ast.node.ephemeral.Factor;
import com.java.parser.ast.node.ephemeral.Relation;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ComparisonOp extends Relation {
    private final Token operator;
    private final Factor left;
    private final Factor right;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitComparisonOp(this);
    }
}
