package com.java.optimizer.visitor;

import com.java.parser.ast.ASTree;
import com.java.parser.ast.node.real.AccessTailList;
import com.java.parser.ast.node.real.Array;
import com.java.parser.ast.node.real.ArrayAccess;
import com.java.parser.ast.node.real.BinaryOp;
import com.java.parser.ast.node.real.ComparisonOp;
import com.java.parser.ast.node.real.DeclarationsCommaList;
import com.java.parser.ast.node.real.EmptyTail;
import com.java.parser.ast.node.real.Expression;
import com.java.parser.ast.node.real.ExpressionStatement;
import com.java.parser.ast.node.real.ExpressionsCommaList;
import com.java.parser.ast.node.real.ForStatement;
import com.java.parser.ast.node.real.FunctionCall;
import com.java.parser.ast.node.real.FunctionLiteral;
import com.java.parser.ast.node.real.IdentifierAssign;
import com.java.parser.ast.node.real.IdentifierWithValue;
import com.java.parser.ast.node.real.IdentifiersCommaList;
import com.java.parser.ast.node.real.IdentifiersWithValueDeclarationStatement;
import com.java.parser.ast.node.real.IfStatement;
import com.java.parser.ast.node.real.LogicalOp;
import com.java.parser.ast.node.real.LoopBody;
import com.java.parser.ast.node.real.OnlyIdentifiersDeclarationStatement;
import com.java.parser.ast.node.real.PrintStatement;
import com.java.parser.ast.node.real.ReadStatement;
import com.java.parser.ast.node.real.ReferenceAssignStatement;
import com.java.parser.ast.node.real.ReferenceTail;
import com.java.parser.ast.node.real.ReferenceType;
import com.java.parser.ast.node.real.ReturnStatement;
import com.java.parser.ast.node.real.StatementsList;
import com.java.parser.ast.node.real.TokenLiteral;
import com.java.parser.ast.node.real.Tuple;
import com.java.parser.ast.node.real.TupleAccess;
import com.java.parser.ast.node.real.TupleList;
import com.java.parser.ast.node.real.UnaryOp;
import com.java.parser.ast.node.real.WhileStatement;
import com.java.parser.ast.visitor.ASTVisitor;

import java.util.*;

public class UnusedVariablesOptimizer implements ASTVisitor<Object> {
    private final List<Map<String, Boolean>> scopesUsage = new LinkedList<>();
    private final List<Map<String, DeclarationsCommaList>> scopesDeclValues = new LinkedList<>();
    private final List<Map<String, OnlyIdentifiersDeclarationStatement>> scopesDeclEmpty = new LinkedList<>();

    @Override
    public Object visitAST(ASTree ast) {
        return ast.getNodes().accept(this);
    }

    @Override
    public Object visitAccessTailList(AccessTailList accessTailList) {
        for (var t: accessTailList.getTails()) {
            t.accept(this);
        }
        return null;
    }

    @Override
    public Object visitArrayAccess(ArrayAccess arrayAccessNode) {
        return arrayAccessNode.getExpression().accept(this);
    }

    @Override
    public Object visitArray(Array array) {
        return array.getElements().accept(this);
    }

    @Override
    public Object visitBinaryOperation(BinaryOp node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
        return null;
    }

    @Override
    public Object visitComparisonOp(ComparisonOp comparisonOp) {
        comparisonOp.getLeft().accept(this);
        comparisonOp.getRight().accept(this);
        return null;
    }

    @Override
    public Object visitDeclarationsCommaList(DeclarationsCommaList declarationsCommaList) {
        for (var decl: declarationsCommaList.getDeclarations()) {
            var name = decl.getIdentifier().lexeme();
            var scope = declarationsCommaList;
            declare(name, scope);
            decl.accept(this);
        }
        return null;
    }

    @Override
    public Object visitEmptyTail(EmptyTail emptyTailNode) {
        return null;
    }

    @Override
    public Object visitExpression(Expression expression) {
        return expression.getBody().accept(this);
    }

    @Override
    public Object visitExpressionStatement(ExpressionStatement expressionStatementNode) {
        return expressionStatementNode.getExpression().accept(this);
    }

    @Override
    public Object visitExpressionsCommaList(ExpressionsCommaList expressionsCommaList) {
        for (var expr: expressionsCommaList.getExpressions()) {
            expr.accept(this);
        }
        return null;
    }

    @Override
    public Object visitForStatement(ForStatement forStmt) {
        use(forStmt.getTypeIndicator().lexeme());
        prepareScope();
        forStmt.getLoopBody().accept(this);
        optimizeUsagesAndLeave();
        return null;
    }

    @Override
    public Object visitFunctionCall(FunctionCall funCall) {
        return funCall.getExpressions().accept(this);
    }

    @Override
    public Object visitFunctionLiteral(FunctionLiteral funLiteral) {
        prepareScope();
        funLiteral.getFunBody().accept(this);
        optimizeUsagesAndLeave();
        return null;
    }

    @Override
    public Object visitIdentifierAssign(IdentifierAssign identifierAssignNode) {
        use(identifierAssignNode.getIdentifier().lexeme());
        identifierAssignNode.getExpression().accept(this);
        return null;
    }

    @Override
    public Object visitIdentifierWithValue(IdentifierWithValue identifierWithValue) {
        identifierWithValue.getValue().accept(this);
        return null;
    }

    @Override
    public Object visitIdentifiersCommaList(IdentifiersCommaList idents) {
        return null;
    }

    @Override
    public Object visitIdentifiersWithValueDeclarationStatement(IdentifiersWithValueDeclarationStatement multipleDeclarationsStatement) {
        return multipleDeclarationsStatement.getDeclarations().accept(this);
    }

    @Override
    public Object visitIfStatement(IfStatement ifStatementNode) {
        ifStatementNode.getExpression().accept(this);
        ifStatementNode.getTrueBody().accept(this);
        if (ifStatementNode.getFalseBody() != null) {
            ifStatementNode.getFalseBody().accept(this);
        }
        return null;
    }

    @Override
    public Object visitLogicalOp(LogicalOp logicalOp) {
        logicalOp.getLeft().accept(this);
        logicalOp.getRight().accept(this);
        return null;
    }

    @Override
    public Object visitLoopBody(LoopBody loopBodyNode) {
        loopBodyNode.getLoopBody().accept(this);
        return null;
    }

    @Override
    public Object visitOnlyIdentifiersDeclarationStatement(OnlyIdentifiersDeclarationStatement node) {
        for (var ident: node.getIdentifiers().getTokens()) {
            declare(ident.lexeme(), node);
        }
        return null;
    }

    @Override
    public Object visitPrintStatement(PrintStatement printStatementNode) {
        printStatementNode.getExpressions().accept(this);
        return null;
    }

    @Override
    public Object visitReadStatement(ReadStatement readNode) {
        readNode.getDest().accept(this);
        return null;
    }

    @Override
    public Object visitReferenceAssignStatement(ReferenceAssignStatement referenceAssignStatementNode) {
        use(referenceAssignStatementNode.getIdentifier().lexeme());
        referenceAssignStatementNode.getExpression().accept(this);
        return null;
    }

    @Override
    public Object visitReferenceTail(ReferenceTail referenceTail) {
        use(referenceTail.getIdentifier().lexeme());
        if (referenceTail.getTail() != null) {
            referenceTail.getTail().accept(this);
        }
        return null;
    }

    @Override
    public Object visitReferenceType(ReferenceType referenceTypeNode) {
        referenceTypeNode.getReference().accept(this);
        return null;
    }

    @Override
    public Object visitReturnStatement(ReturnStatement returnStatementNode) {
        if (returnStatementNode.getExpression() != null) {
            returnStatementNode.getExpression().accept(this);
        }
        return null;
    }

    @Override
    public Object visitStatementsList(StatementsList statementsList) {
        prepareScope();
        for (var stmt: statementsList.getStatements()) {
            stmt.accept(this);
        }
        optimizeUsagesAndLeave();
        return null;
    }

    @Override
    public Object visitTokenLiteral(TokenLiteral node) {
        return null;
    }

    @Override
    public Object visitTuple(Tuple tuple) {
        return tuple.getElements().accept(this);
    }

    @Override
    public Object visitTupleAccess(TupleAccess tupleAccess) {
        return null;
    }

    @Override
    public Object visitTupleList(TupleList tupleListNode) {
        for (var expr: tupleListNode.getAllExpressions()) {
            expr.accept(this);
        }
        return null;
    }

    @Override
    public Object visitUnaryOp(UnaryOp unaryOpNode) {
        unaryOpNode.getUnary().accept(this);
        return null;
    }

    @Override
    public Object visitWhileStatement(WhileStatement whileStatementNode) {
        whileStatementNode.getExpression().accept(this);
        whileStatementNode.getLoopBody().accept(this);
        return null;
    }

    private void use(String name) {
        for (var scope: scopesUsage.reversed()) {
            if (scope.containsKey(name)) {
                scope.put(name, true);
                break;
            }
        }
    }

    private void declare(String name, DeclarationsCommaList parent) {
        scopesUsage.getLast().put(name, false);
        scopesDeclValues.getLast().put(name, parent);
    }

    private void declare(String name, OnlyIdentifiersDeclarationStatement parent) {
        scopesUsage.getLast().put(name, false);
        scopesDeclEmpty.getLast().put(name, parent);
    }

    private void optimizeUsagesAndLeave() {
        var usage = scopesUsage.getLast();
        for (var entry: usage.entrySet()) {
            if (entry.getValue()) {
                continue;
            }
            removeUnusedDeclaration(entry.getKey());
        }
    }

    private void removeUnusedDeclaration(String name) {
        var scopeDeclared = scopesDeclValues.getLast();
        var scopeEmpty = scopesDeclEmpty.getLast();

        var declaration = scopeDeclared.get(name);
        if (declaration != null) {
            declaration.getDeclarations().removeIf(id -> id.getIdentifier().lexeme().equals(name));
        }

        var emptyDecl = scopeEmpty.get(name);
        if (emptyDecl != null) {
            emptyDecl.getIdentifiers().getTokens().removeIf(tkn -> tkn.lexeme().equals(name));
        }
    }

    private void prepareScope() {
        scopesUsage.add(new HashMap<>());
        scopesDeclValues.add(new HashMap<>());
        scopesDeclEmpty.add(new HashMap<>());
    }
}
