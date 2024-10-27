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

public class UnreachableCodeVisitor implements ASTVisitor<Object> {
    @Override
    public Object visitAST(ASTree ast) {
        return ast.getNodes().accept(this);
    }

    @Override
    public Object visitAccessTailList(AccessTailList accessTailList) {
        return null;
    }

    @Override
    public Object visitArrayAccess(ArrayAccess arrayAccessNode) {
        return null;
    }

    @Override
    public Object visitArray(Array array) {
        return null;
    }

    @Override
    public Object visitBinaryOperation(BinaryOp node) {
        return null;
    }

    @Override
    public Object visitComparisonOp(ComparisonOp comparisonOp) {
        return null;
    }

    @Override
    public Object visitDeclarationsCommaList(DeclarationsCommaList declarationsCommaList) {
        for (var decl : declarationsCommaList.getDeclarations()) {
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
        return null;
    }

    @Override
    public Object visitExpressionStatement(ExpressionStatement expressionStatementNode) {
        return null;
    }

    @Override
    public Object visitExpressionsCommaList(ExpressionsCommaList expressionsCommaList) {
        return null;
    }

    @Override
    public Object visitForStatement(ForStatement forStatementNode) {
        return forStatementNode.getLoopBody().accept(this);
    }

    @Override
    public Object visitFunctionCall(FunctionCall funCall) {
        return null;
    }

    @Override
    public Object visitFunctionLiteral(FunctionLiteral funLiteral) {
        if (funLiteral.getFunBody() instanceof StatementsList) {
            return funLiteral.getFunBody().accept(this);
        }
        return null;
    }

    @Override
    public Object visitIdentifierAssign(IdentifierAssign identifierAssignNode) {
        return null;
    }

    @Override
    public Object visitIdentifierWithValue(IdentifierWithValue identifierWithValue) {
        return identifierWithValue.getValue().accept(this);
    }

    @Override
    public Object visitIdentifiersCommaList(IdentifiersCommaList tokenListNode) {
        return null;
    }

    @Override
    public Object visitIdentifiersWithValueDeclarationStatement(IdentifiersWithValueDeclarationStatement multipleDeclarationsStatement) {
        System.out.println("DECLS STMT");
        return multipleDeclarationsStatement.getDeclarations().accept(this);
    }

    @Override
    public Object visitIfStatement(IfStatement ifStatementNode) {
        ifStatementNode.getTrueBody().accept(this);
        if (ifStatementNode.getFalseBody() != null) {
            return ifStatementNode.getFalseBody().accept(this);
        }
        return null;
    }

    @Override
    public Object visitLogicalOp(LogicalOp logicalOp) {
        return null;
    }

    @Override
    public Object visitLoopBody(LoopBody loopBodyNode) {
        return loopBodyNode.getLoopBody().accept(this);
    }

    @Override
    public Object visitOnlyIdentifiersDeclarationStatement(OnlyIdentifiersDeclarationStatement node) {
        return null;
    }

    @Override
    public Object visitPrintStatement(PrintStatement printStatementNode) {
        return null;
    }

    @Override
    public Object visitReadStatement(ReadStatement readNode) {
        return null;
    }

    @Override
    public Object visitReferenceAssignStatement(ReferenceAssignStatement referenceAssignStatementNode) {
        return null;
    }

    @Override
    public Object visitReferenceTail(ReferenceTail referenceTail) {
        return null;
    }

    @Override
    public Object visitReferenceType(ReferenceType referenceTypeNode) {
        return null;
    }

    @Override
    public Object visitReturnStatement(ReturnStatement returnStatementNode) {
        return null;
    }

    @Override
    public Object visitStatementsList(StatementsList statementsList) {
        System.out.println("STMTS");
        var stmts = statementsList.getStatements();
        for (int i = 0; i < stmts.size(); i++) {
            if (stmts.get(i) instanceof ReturnStatement) {
                while (stmts.size() > i + 1) {
                    stmts.removeLast();
                }
                break;
            }
            stmts.get(i).accept(this);
        }
        return null;
    }

    @Override
    public Object visitTokenLiteral(TokenLiteral node) {
        return null;
    }

    @Override
    public Object visitTuple(Tuple tuple) {
        return null;
    }

    @Override
    public Object visitTupleAccess(TupleAccess tupleAccess) {
        return null;
    }

    @Override
    public Object visitTupleList(TupleList tupleListNode) {
        return null;
    }

    @Override
    public Object visitUnaryOp(UnaryOp unaryOpNode) {
        return null;
    }

    @Override
    public Object visitWhileStatement(WhileStatement whileStatementNode) {
        return whileStatementNode.getLoopBody().accept(this);
    }
}
