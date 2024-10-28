package com.java.semantic.checkers;

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
import com.java.semantic.errors.Error;
import com.java.semantic.errors.MultipleLocalDeclarationsError;
import com.java.semantic.errors.UndeclaredUsageError;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class UsageChecksVisitor implements ASTVisitor<List<Error>> {
    private final List<Set<String>> scopedDeclarations = new LinkedList<>();

    @Override
    public List<Error> visitAST(ASTree ast) {
        return ast.getNodes().accept(this);
    }

    @Override
    public List<Error> visitAccessTailList(AccessTailList accessTailList) {
        var errors = new LinkedList<Error>();
        for (var t : accessTailList.getTails()) {
            errors.addAll(t.accept(this));
        }
        return errors;
    }

    @Override
    public List<Error> visitArrayAccess(ArrayAccess arrayAccessNode) {
        return arrayAccessNode.getExpression().accept(this);
    }

    @Override
    public List<Error> visitArray(Array array) {
        return array.getElements().accept(this);
    }

    @Override
    public List<Error> visitBinaryOperation(BinaryOp node) {
        var errors = new LinkedList<Error>();
        errors.addAll(node.getLeft().accept(this));
        errors.addAll(node.getRight().accept(this));
        return errors;
    }

    @Override
    public List<Error> visitComparisonOp(ComparisonOp comparisonOp) {
        var errors = new LinkedList<Error>();
        errors.addAll(comparisonOp.getLeft().accept(this));
        errors.addAll(comparisonOp.getRight().accept(this));
        return errors;
    }

    @Override
    public List<Error> visitDeclarationsCommaList(DeclarationsCommaList declarationsCommaList) {
        var errors = new LinkedList<Error>();
        for (var decl : declarationsCommaList.getDeclarations()) {
            errors.addAll(decl.accept(this));
        }
        return errors;
    }

    @Override
    public List<Error> visitEmptyTail(EmptyTail emptyTailNode) {
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitExpression(Expression expression) {
        return expression.getBody().accept(this);
    }

    @Override
    public List<Error> visitExpressionStatement(ExpressionStatement expressionStatementNode) {
        return expressionStatementNode.getExpression().accept(this);
    }

    @Override
    public List<Error> visitExpressionsCommaList(ExpressionsCommaList expressionsCommaList) {
        var errors = new LinkedList<Error>();
        for (var expr : expressionsCommaList.getExpressions()) {
            errors.addAll(expr.accept(this));
        }
        return errors;
    }

    @Override
    public List<Error> visitForStatement(ForStatement forStatementNode) {
        var errors = new LinkedList<Error>();
        if (isNotDeclared(forStatementNode.getTypeIndicator().lexeme())) {
            errors.add(new UndeclaredUsageError(forStatementNode.getTypeIndicator()));
        }
        scopedDeclarations.add(new HashSet<>());
        scopedDeclarations.getLast().add(forStatementNode.getIdentifier().lexeme());
        errors.addAll(forStatementNode.getLoopBody().accept(this));
        scopedDeclarations.removeLast();
        return errors;
    }

    @Override
    public List<Error> visitFunctionCall(FunctionCall funCall) {
        return funCall.getExpressions().accept(this);
    }

    @Override
    public List<Error> visitFunctionLiteral(FunctionLiteral funLiteral) {
        var errors = new LinkedList<Error>();
        scopedDeclarations.add(new HashSet<>());
        for (var param : funLiteral.getParameters().getTokens()) {
            if (isDeclaredLocally(param.lexeme())) {
                errors.add(new MultipleLocalDeclarationsError(param));
            }
            scopedDeclarations.getLast().add(param.lexeme());
        }
        errors.addAll(funLiteral.getFunBody().accept(this));
        scopedDeclarations.removeLast();
        return errors;
    }

    @Override
    public List<Error> visitIdentifierAssign(IdentifierAssign identifierAssignNode) {
        var errors = new LinkedList<Error>();
        if (isNotDeclared(identifierAssignNode.getIdentifier().lexeme())) {
            errors.add(new UndeclaredUsageError(identifierAssignNode.getIdentifier()));
        }
        errors.addAll(identifierAssignNode.getExpression().accept(this));
        // TODO: тут внимательно
        return errors;
    }

    @Override
    public List<Error> visitIdentifierWithValue(IdentifierWithValue identifierWithValue) {
        var errors = new LinkedList<Error>();
        if (isDeclaredLocally(identifierWithValue.getIdentifier().lexeme())) {
            errors.add(new MultipleLocalDeclarationsError(identifierWithValue.getIdentifier()));
        }
        if (identifierWithValue.getValue() instanceof FunctionLiteral) {
            scopedDeclarations.getLast().add(identifierWithValue.getIdentifier().lexeme());
        }
        errors.addAll(identifierWithValue.getValue().accept(this));
        if (!(identifierWithValue.getValue() instanceof FunctionLiteral)) {
            scopedDeclarations.getLast().add(identifierWithValue.getIdentifier().lexeme());
        }
        return errors;
    }

    @Override
    public List<Error> visitIdentifiersCommaList(IdentifiersCommaList identifiersList) {
        var errors = new LinkedList<Error>();
        for (var ident : identifiersList.getTokens()) {
            if (isDeclaredLocally(ident.lexeme())) {
                errors.add(new MultipleLocalDeclarationsError(ident));
            }
            scopedDeclarations.getLast().add(ident.lexeme());
        }
        return errors;
    }

    @Override
    public List<Error> visitIdentifiersWithValueDeclarationStatement(
        IdentifiersWithValueDeclarationStatement multipleDeclarationsStatement
    ) {
        return multipleDeclarationsStatement.getDeclarations().accept(this);
    }

    @Override
    public List<Error> visitIfStatement(IfStatement ifStatementNode) {
        var errors = new LinkedList<Error>();
        errors.addAll(ifStatementNode.getExpression().accept(this));
        errors.addAll(ifStatementNode.getTrueBody().accept(this));
        if (ifStatementNode.getFalseBody() != null) {
            errors.addAll(ifStatementNode.getFalseBody().accept(this));
        }
        return errors;
    }

    @Override
    public List<Error> visitLogicalOp(LogicalOp logicalOp) {
        var errors = new LinkedList<Error>();
        errors.addAll(logicalOp.getLeft().accept(this));
        errors.addAll(logicalOp.getRight().accept(this));
        return errors;
    }

    @Override
    public List<Error> visitLoopBody(LoopBody loopBodyNode) {
        return loopBodyNode.getLoopBody().accept(this);
    }

    @Override
    public List<Error> visitOnlyIdentifiersDeclarationStatement(OnlyIdentifiersDeclarationStatement decl) {
        return decl.getIdentifiers().accept(this);
    }

    @Override
    public List<Error> visitPrintStatement(PrintStatement printStatementNode) {
        return printStatementNode.getExpressions().accept(this);
    }

    @Override
    public List<Error> visitReadStatement(ReadStatement readNode) {
        return readNode.getDest().accept(this);
    }

    @Override
    public List<Error> visitReferenceAssignStatement(ReferenceAssignStatement referenceAssignStatementNode) {
        var errors = new LinkedList<Error>();
        if (isNotDeclared(referenceAssignStatementNode.getIdentifier().lexeme())) {
            errors.add(new UndeclaredUsageError(referenceAssignStatementNode.getIdentifier()));
        }
        errors.addAll(referenceAssignStatementNode.getExpression().accept(this));
        return errors;
    }

    @Override
    public List<Error> visitReferenceTail(ReferenceTail referenceTail) {
        var errors = new LinkedList<Error>();
        if (isNotDeclared(referenceTail.getIdentifier().lexeme())) {
            errors.add(new UndeclaredUsageError(referenceTail.getIdentifier()));
        }
        if (referenceTail.getTail() != null) {
            errors.addAll(referenceTail.getTail().accept(this));
        }
        return errors; // TODO: не чекать весь хвост
    }

    @Override
    public List<Error> visitReferenceType(ReferenceType referenceTypeNode) {
        return referenceTypeNode.getReference().accept(this);
    }

    @Override
    public List<Error> visitReturnStatement(ReturnStatement returnStatementNode) {
        if (returnStatementNode.getExpression() != null) {
            return returnStatementNode.getExpression().accept(this);
        }
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitStatementsList(StatementsList statementsList) {
        var errors = new LinkedList<Error>();
        scopedDeclarations.add(new HashSet<>());
        for (var stmt : statementsList.getStatements()) {
            var res = stmt.accept(this);
            errors.addAll(res);
        }
        scopedDeclarations.removeLast();
        return errors;
    }

    @Override
    public List<Error> visitTokenLiteral(TokenLiteral node) {
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitTuple(Tuple tuple) {
        return tuple.getElements().accept(this);
    }

    @Override
    public List<Error> visitTupleAccess(TupleAccess tupleAccess) {
        return new LinkedList<>(); // TODO: ???
    }

    @Override
    public List<Error> visitTupleList(TupleList tupleListNode) {
        var errors = new LinkedList<Error>();
        for (var expr : tupleListNode.getAllExpressions()) {
            errors.addAll(expr.accept(this));
        }
        return errors;
    }

    @Override
    public List<Error> visitUnaryOp(UnaryOp unaryOpNode) {
        return unaryOpNode.getUnary().accept(this);
    }

    @Override
    public List<Error> visitWhileStatement(WhileStatement whileStatementNode) {
        var errors = new LinkedList<Error>();
        errors.addAll(whileStatementNode.getExpression().accept(this));
        errors.addAll(whileStatementNode.getLoopBody().accept(this));
        return errors;
    }

    private boolean isNotDeclared(String name) {
        for (var scope : scopedDeclarations.reversed()) {
            if (scope.contains(name)) {
                return false;
            }
        }
        return true;
    }

    private boolean isDeclaredLocally(String name) {
        if (scopedDeclarations.isEmpty()) {
            return false;
        }
        return scopedDeclarations.getLast().contains(name);
    }
}
