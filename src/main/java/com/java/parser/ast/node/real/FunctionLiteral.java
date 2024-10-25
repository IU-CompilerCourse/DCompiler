package com.java.parser.ast.node.real;

import com.java.parser.ast.node.ephemeral.ASTNode;
import com.java.parser.ast.node.ephemeral.Relation;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FunctionLiteral extends Relation {
    private final IdentifiersCommaList parameters;
    private final ASTNode funBody; // can be either StatementsList || Expression (in case of lambda function)

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitFunctionLiteral(this);
    }
}
