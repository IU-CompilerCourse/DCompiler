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
import com.java.semantic.errors.ReturnContextError;
import java.util.LinkedList;
import java.util.List;

public class ContextCheckerVisitor implements ASTVisitor<List<Error>> {
    private int nestedFunctionLevel = 0;

    @Override
    public List<Error> visitAST(ASTree ast) {
        return ast.getNodes().accept(this);
    }

    @Override
    public List<Error> visitAccessTailList(AccessTailList accessTailList) {
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitArrayAccess(ArrayAccess arrayAccessNode) {
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitArray(Array array) {
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitBinaryOperation(BinaryOp node) {
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitComparisonOp(ComparisonOp comparisonOp) {
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitDeclarationsCommaList(DeclarationsCommaList declarationsCommaList) {
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitEmptyTail(EmptyTail emptyTailNode) {
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitExpression(Expression expression) {
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitExpressionStatement(ExpressionStatement expressionStatementNode) {
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitExpressionsCommaList(ExpressionsCommaList expressionsCommaList) {
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitForStatement(ForStatement forStatementNode) {
        return forStatementNode.getLoopBody().accept(this);
    }

    @Override
    public List<Error> visitFunctionCall(FunctionCall funCall) {
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitFunctionLiteral(FunctionLiteral funLiteral) {
        nestedFunctionLevel++;
        var errors = funLiteral.getFunBody().accept(this);
        nestedFunctionLevel--;
        return errors;
    }

    @Override
    public List<Error> visitIdentifierAssign(IdentifierAssign identifierAssignNode) {
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitIdentifierWithValue(IdentifierWithValue identifierWithValue) {
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitIdentifiersCommaList(IdentifiersCommaList tokenListNode) {
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitIdentifiersWithValueDeclarationStatement(
        IdentifiersWithValueDeclarationStatement multipleDeclarationsStatement
    ) {
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitIfStatement(IfStatement ifStatementNode) {
        var errors = new LinkedList<>(ifStatementNode.getTrueBody().accept(this));
        if (ifStatementNode.getFalseBody() != null) {
            errors.addAll(ifStatementNode.getFalseBody().accept(this));
        }
        return errors;
    }

    @Override
    public List<Error> visitLogicalOp(LogicalOp logicalOp) {
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitLoopBody(LoopBody loopBodyNode) {
        return loopBodyNode.getLoopBody().accept(this);
    }

    @Override
    public List<Error> visitOnlyIdentifiersDeclarationStatement(OnlyIdentifiersDeclarationStatement node) {
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitPrintStatement(PrintStatement printStatementNode) {
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitReadStatement(ReadStatement readNode) {
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitReferenceAssignStatement(
        ReferenceAssignStatement referenceAssignStatementNode
    ) {
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitReferenceTail(ReferenceTail referenceTail) {
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitReferenceType(ReferenceType referenceTypeNode) {
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitReturnStatement(ReturnStatement returnStatementNode) {
        if (nestedFunctionLevel == 0) {
            var errors = new LinkedList<Error>();
            errors.add(new ReturnContextError());
            return errors;
        }
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitStatementsList(StatementsList statementsList) {
        var errors = new LinkedList<Error>();
        for (var stmt : statementsList.getStatements()) {
            errors.addAll(stmt.accept(this));
        }
        return errors;
    }

    @Override
    public List<Error> visitTokenLiteral(TokenLiteral node) {
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitTuple(Tuple tuple) {
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitTupleAccess(TupleAccess tupleAccess) {
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitTupleList(TupleList tupleListNode) {
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitUnaryOp(UnaryOp unaryOpNode) {
        return new LinkedList<>();
    }

    @Override
    public List<Error> visitWhileStatement(WhileStatement whileStatementNode) {
        return whileStatementNode.getLoopBody().accept(this);
    }
}
