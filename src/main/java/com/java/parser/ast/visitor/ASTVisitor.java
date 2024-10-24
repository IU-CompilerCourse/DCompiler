package com.java.parser.ast.visitor;

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

public interface ASTVisitor<R> {
    R visitAST(ASTree ast);

    R visitAccessTailList(AccessTailList accessTailList);

    R visitArrayAccess(ArrayAccess arrayAccessNode);

    R visitArray(Array array);

    R visitBinaryOperation(BinaryOp node);

    R visitComparisonOp(ComparisonOp comparisonOp);

    R visitDeclarationsCommaList(DeclarationsCommaList declarationsCommaList);

    R visitEmptyTail(EmptyTail emptyTailNode);

    R visitExpression(Expression expression);

    R visitExpressionStatement(ExpressionStatement expressionStatementNode);

    R visitExpressionsCommaList(ExpressionsCommaList expressionsCommaList);

    R visitForStatement(ForStatement forStatementNode);

    R visitFunctionCall(FunctionCall funCall);

    R visitFunctionLiteral(FunctionLiteral funLiteral);

    R visitIdentifierAssign(IdentifierAssign identifierAssignNode);

    R visitIdentifierWithValue(IdentifierWithValue identifierWithValue);

    R visitIdentifiersCommaList(IdentifiersCommaList tokenListNode);

    R visitIdentifiersWithValueDeclarationStatement(IdentifiersWithValueDeclarationStatement multipleDeclarationsStatement);

    R visitIfStatement(IfStatement ifStatementNode);

    R visitLogicalOp(LogicalOp logicalOp);

    R visitLoopBody(LoopBody loopBodyNode);

    R visitOnlyIdentifiersDeclarationStatement(OnlyIdentifiersDeclarationStatement node);

    R visitPrintStatement(PrintStatement printStatementNode);

    R visitReadStatement(ReadStatement readNode);

    R visitReferenceAssignStatement(ReferenceAssignStatement referenceAssignStatementNode);

    R visitReferenceTail(ReferenceTail referenceTail);

    R visitReferenceType(ReferenceType referenceTypeNode);

    R visitReturnStatement(ReturnStatement returnStatementNode);

    R visitStatementsList(StatementsList statementsList);

    R visitTokenLiteral(TokenLiteral node);

    R visitTuple(Tuple tuple);

    R visitTupleAccess(TupleAccess tupleAccess);

    R visitTupleList(TupleList tupleListNode);

    R visitUnaryOp(UnaryOp unaryOpNode);

    R visitWhileStatement(WhileStatement whileStatementNode);
}
