package com.java.parser.ast.node.real;

import com.java.parser.ast.node.ephemeral.ExpressionEphemeral;
import com.java.parser.ast.visitor.ASTVisitor;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class ExpressionsCommaList extends com.java.parser.ast.node.ephemeral.List {
    private final List<ExpressionEphemeral> expressions;

    public ExpressionsCommaList() {
        expressions = new ArrayList<>();
    }

    public ExpressionsCommaList(ExpressionEphemeral expression) {
        expressions = new ArrayList<>();
        expressions.add(expression);
    }

    public ExpressionsCommaList(ExpressionEphemeral expression, ExpressionsCommaList expressionsCommaList) {
        expressions = new ArrayList<>();
        expressions.add(expression);
        expressions.addAll(expressionsCommaList.expressions);
    }

    public void append(ExpressionEphemeral expression) {
        expressions.add(expression);
    }

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitExpressionsCommaList(this);
    }
}
