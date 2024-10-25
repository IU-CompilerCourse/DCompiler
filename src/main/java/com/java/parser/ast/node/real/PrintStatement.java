package com.java.parser.ast.node.real;

import com.java.parser.ast.node.ephemeral.Statement;
import com.java.parser.ast.visitor.ASTVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PrintStatement extends Statement {
    private final ExpressionsCommaList expressions;

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitPrintStatement(this);
    }
}
