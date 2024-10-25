package com.java.parser.ast.node.real;

import com.java.lexer.Token;
import com.java.parser.ast.node.ephemeral.ExpressionEphemeral;
import com.java.parser.ast.visitor.ASTVisitor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class TupleList extends com.java.parser.ast.node.ephemeral.List {
    private final Map<Token, ExpressionEphemeral> namedExpressions; // again, too hard to make it typed
    private final List<ExpressionEphemeral> allExpressions; // can be either

    public TupleList() {
        namedExpressions = new HashMap<>();
        allExpressions = new ArrayList<>();
    }

    public TupleList(ExpressionEphemeral expression) {
        namedExpressions = new HashMap<>();
        allExpressions = new ArrayList<>();

        allExpressions.add(expression);
    }

    public TupleList(IdentifierAssign identifierAssign) {
        namedExpressions = new HashMap<>();
        allExpressions = new ArrayList<>();

        namedExpressions.put(identifierAssign.getIdentifier(), identifierAssign.getExpression());
        allExpressions.add(identifierAssign.getExpression());
    }

    public void append(ExpressionEphemeral expression) {
        allExpressions.add(expression);
    }

    public void append(IdentifierAssign identifierAssign) {
        namedExpressions.put(identifierAssign.getIdentifier(), identifierAssign.getExpression());
        allExpressions.add(identifierAssign.getExpression());
    }

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitTupleList(this);
    }
}
