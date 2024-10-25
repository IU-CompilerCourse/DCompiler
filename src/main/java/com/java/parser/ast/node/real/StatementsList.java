package com.java.parser.ast.node.real;

import com.java.parser.ast.node.ephemeral.Statement;
import com.java.parser.ast.visitor.ASTVisitor;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class StatementsList extends com.java.parser.ast.node.ephemeral.List {
    private final List<Statement> statements;

    public StatementsList() {
        statements = new ArrayList<>();
    }

    public StatementsList(Statement statement) {
        statements = new ArrayList<>();
        statements.add(statement);
    }

    public StatementsList(Statement statement, StatementsList statementsList) {
        statements = new ArrayList<>();
        statements.add(statement);
        statements.addAll(statementsList.statements);
    }

    public void append(Statement statement) {
        statements.add(statement);
    }

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitStatementsList(this);
    }
}
