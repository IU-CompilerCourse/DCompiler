package com.java.analyzer.visitor;

import com.java.analyzer.errors.MultipleLocalDeclarationsError;
import com.java.analyzer.errors.UndeclaredUsageError;
import com.java.analyzer.errors.UsageCheckError;
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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class UsageChecksVisitor implements ASTVisitor<List<UsageCheckError>> {
    private final List<Set<String>> scopedDeclarations = new LinkedList<>();
    @Override
    public List<UsageCheckError> visitAST(ASTree ast) {
        return ast.getNodes().accept(this);
    }

    @Override
    public List<UsageCheckError> visitAccessTailList(AccessTailList accessTailList) {
        return new LinkedList<>();
    }

    @Override
    public List<UsageCheckError> visitArrayAccess(ArrayAccess arrayAccessNode) {
        return arrayAccessNode.getExpression().accept(this);
    }

    @Override
    public List<UsageCheckError> visitArray(Array array) {
        return array.getElements().accept(this);
    }

    @Override
    public List<UsageCheckError> visitBinaryOperation(BinaryOp node) {
        var errors = new LinkedList<UsageCheckError>();
        errors.addAll(node.getLeft().accept(this));
        errors.addAll(node.getRight().accept(this));
        return errors;
    }

    @Override
    public List<UsageCheckError> visitComparisonOp(ComparisonOp comparisonOp) {
        var errors = new LinkedList<UsageCheckError>();
        errors.addAll(comparisonOp.getLeft().accept(this));
        errors.addAll(comparisonOp.getRight().accept(this));
        return errors;
    }

    @Override
    public List<UsageCheckError> visitDeclarationsCommaList(DeclarationsCommaList declarationsCommaList) {
        var errors = new LinkedList<UsageCheckError>();
        for (var decl: declarationsCommaList.getDeclarations()) {
            errors.addAll(decl.accept(this));
        }
        return errors;
    }

    @Override
    public List<UsageCheckError> visitEmptyTail(EmptyTail emptyTailNode) {
        return new LinkedList<>();
    }

    @Override
    public List<UsageCheckError> visitExpression(Expression expression) {
        return expression.getBody().accept(this);
    }

    @Override
    public List<UsageCheckError> visitExpressionStatement(ExpressionStatement expressionStatementNode) {
        return expressionStatementNode.getExpression().accept(this);
    }

    @Override
    public List<UsageCheckError> visitExpressionsCommaList(ExpressionsCommaList expressionsCommaList) {
        var errors = new LinkedList<UsageCheckError>();
        for (var expr: expressionsCommaList.getExpressions()) {
            errors.addAll(expr.accept(this));
        }
        return errors;
    }

    @Override
    public List<UsageCheckError> visitForStatement(ForStatement forStatementNode) {
        var errors = new LinkedList<UsageCheckError>();
        if (!isDeclared(forStatementNode.getTypeIndicator().lexeme())) {
            errors.add(new UndeclaredUsageError(forStatementNode.getTypeIndicator()));
        }
        scopedDeclarations.add(new HashSet<>());
        scopedDeclarations.getLast().add(forStatementNode.getIdentifier().lexeme());
        errors.addAll(forStatementNode.getLoopBody().accept(this));
        scopedDeclarations.removeLast();
        return errors;
    }

    @Override
    public List<UsageCheckError> visitFunctionCall(FunctionCall funCall) {
        return funCall.getExpressions().accept(this);
    }

    @Override
    public List<UsageCheckError> visitFunctionLiteral(FunctionLiteral funLiteral) {
        var errors = new LinkedList<UsageCheckError>();
        scopedDeclarations.add(new HashSet<>());
        for (var param: funLiteral.getParameters().getTokens()) {
            if (isDeclaredLocally(param.lexeme())) {
                errors.add(new MultipleLocalDeclarationsError(param));
            }
            scopedDeclarations.getLast().add(param.lexeme());
        }
        scopedDeclarations.removeLast();
        return errors;
    }

    @Override
    public List<UsageCheckError> visitIdentifierAssign(IdentifierAssign identifierAssignNode) {
        var errors = new LinkedList<UsageCheckError>();
        if (!isDeclared(identifierAssignNode.getIdentifier().lexeme())) {
            errors.add(new UndeclaredUsageError(identifierAssignNode.getIdentifier()));
        }
        errors.addAll(identifierAssignNode.getExpression().accept(this));
        // TODO: тут внимательно
        return errors;
    }

    @Override
    public List<UsageCheckError> visitIdentifierWithValue(IdentifierWithValue identifierWithValue) {
        var errors = new LinkedList<UsageCheckError>();
        if (isDeclaredLocally(identifierWithValue.getIdentifier().lexeme())) {
            errors.add(new MultipleLocalDeclarationsError(identifierWithValue.getIdentifier()));
        }
        errors.addAll(identifierWithValue.getValue().accept(this));
        scopedDeclarations.getLast().add(identifierWithValue.getIdentifier().lexeme());
        return errors;
    }

    @Override
    public List<UsageCheckError> visitIdentifiersCommaList(IdentifiersCommaList identifiersList) {
        var errors = new LinkedList<UsageCheckError>();
        for (var ident: identifiersList.getTokens()) {
            if (isDeclaredLocally(ident.lexeme())) {
                errors.add(new MultipleLocalDeclarationsError(ident));
            }
            scopedDeclarations.getLast().add(ident.lexeme());
        }
        return new LinkedList<>();
    }

    @Override
    public List<UsageCheckError> visitIdentifiersWithValueDeclarationStatement(IdentifiersWithValueDeclarationStatement multipleDeclarationsStatement) {
        return multipleDeclarationsStatement.getDeclarations().accept(this);
    }

    @Override
    public List<UsageCheckError> visitIfStatement(IfStatement ifStatementNode) {
        var errors = new LinkedList<UsageCheckError>();
        errors.addAll(ifStatementNode.getExpression().accept(this));
        errors.addAll(ifStatementNode.getTrueBody().accept(this));
        if (ifStatementNode.getFalseBody() != null) {
            errors.addAll(ifStatementNode.getFalseBody().accept(this));
        }
        return errors;
    }

    @Override
    public List<UsageCheckError> visitLogicalOp(LogicalOp logicalOp) {
        var errors = new LinkedList<UsageCheckError>();
        errors.addAll(logicalOp.getLeft().accept(this));
        errors.addAll(logicalOp.getRight().accept(this));
        return errors;
    }

    @Override
    public List<UsageCheckError> visitLoopBody(LoopBody loopBodyNode) {
        return loopBodyNode.getLoopBody().accept(this);
    }

    @Override
    public List<UsageCheckError> visitOnlyIdentifiersDeclarationStatement(OnlyIdentifiersDeclarationStatement decl) {
        return decl.getIdentifiers().accept(this);
    }

    @Override
    public List<UsageCheckError> visitPrintStatement(PrintStatement printStatementNode) {
        return printStatementNode.getExpressions().accept(this);
    }

    @Override
    public List<UsageCheckError> visitReadStatement(ReadStatement readNode) {
        return readNode.getDest().accept(this);
    }

    @Override
    public List<UsageCheckError> visitReferenceAssignStatement(ReferenceAssignStatement referenceAssignStatementNode) {
        var errors = new LinkedList<UsageCheckError>();
        if (!isDeclared(referenceAssignStatementNode.getIdentifier().lexeme())) {
            errors.add(new UndeclaredUsageError(referenceAssignStatementNode.getIdentifier()));
        }
        errors.addAll(referenceAssignStatementNode.getExpression().accept(this));
        return errors;
    }

    @Override
    public List<UsageCheckError> visitReferenceTail(ReferenceTail referenceTail) {
        var errors = new LinkedList<UsageCheckError>();
        if (!isDeclared(referenceTail.getIdentifier().lexeme())) {
            errors.add(new UndeclaredUsageError(referenceTail.getIdentifier()));
        }
        return errors; // TODO: не чекать весь хвост
    }

    @Override
    public List<UsageCheckError> visitReferenceType(ReferenceType referenceTypeNode) {
        return referenceTypeNode.getReference().accept(this);
    }

    @Override
    public List<UsageCheckError> visitReturnStatement(ReturnStatement returnStatementNode) {
        if (returnStatementNode.getExpression() != null) {
            return returnStatementNode.getExpression().accept(this);
        }
        return new LinkedList<>();
    }

    @Override
    public List<UsageCheckError> visitStatementsList(StatementsList statementsList) {
        var errors = new LinkedList<UsageCheckError>();
        scopedDeclarations.add(new HashSet<>());
        for (var stmt: statementsList.getStatements()) {
             var res = stmt.accept(this);
             errors.addAll(res);
        }
        scopedDeclarations.removeLast();
        return errors;
    }

    @Override
    public List<UsageCheckError> visitTokenLiteral(TokenLiteral node) {
        return new LinkedList<>();
    }

    @Override
    public List<UsageCheckError> visitTuple(Tuple tuple) {
        return tuple.getElements().accept(this);
    }

    @Override
    public List<UsageCheckError> visitTupleAccess(TupleAccess tupleAccess) {
        return new LinkedList<>(); // TODO: ???
    }

    @Override
    public List<UsageCheckError> visitTupleList(TupleList tupleListNode) {
        var errors = new LinkedList<UsageCheckError>();
        for (var expr: tupleListNode.getAllExpressions()) {
            errors.addAll(expr.accept(this));
        }
        return errors;
    }

    @Override
    public List<UsageCheckError> visitUnaryOp(UnaryOp unaryOpNode) {
        return unaryOpNode.getUnary().accept(this);
    }

    @Override
    public List<UsageCheckError> visitWhileStatement(WhileStatement whileStatementNode) {
        var errors = new LinkedList<UsageCheckError>();
        errors.addAll(whileStatementNode.getExpression().accept(this));
        errors.addAll(whileStatementNode.getLoopBody().accept(this));
        return errors;
    }

    private boolean isDeclared(String name) {
        for (var scope : scopedDeclarations.reversed()) {
            if (scope.contains(name)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDeclaredLocally(String name) {
        if (scopedDeclarations.isEmpty()) {
            return false;
        }
        return scopedDeclarations.getLast().contains(name);
    }
}
