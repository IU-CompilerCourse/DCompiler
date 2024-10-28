package com.java.parser.ast.node.real;

import com.java.parser.ast.node.ephemeral.ASTNode;
import com.java.parser.ast.node.ephemeral.Relation;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FunctionLiteral extends Relation {
    private final IdentifiersCommaList parameters;
    private ASTNode funBody; // can be either StatementsList || Expression (in case of lambda function)

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitFunctionLiteral(this);
    }
}
