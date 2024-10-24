package com.java.parser.ast.visitor;

import com.java.parser.ast.ASTree;
import com.java.parser.ast.node.ASTList;
import com.java.parser.ast.node.ASTLiteral;
import com.java.parser.ast.node.ArrayAccess;
import com.java.parser.ast.node.BinaryOp;
import com.java.parser.ast.node.EmptyTail;
import com.java.parser.ast.node.ExpressionStatement;
import com.java.parser.ast.node.For;
import com.java.parser.ast.node.FunctionCall;
import com.java.parser.ast.node.FunctionLiteral;
import com.java.parser.ast.node.IdentifierAssign;
import com.java.parser.ast.node.If;
import com.java.parser.ast.node.LoopBody;
import com.java.parser.ast.node.MultipleDeclaration;
import com.java.parser.ast.node.Print;
import com.java.parser.ast.node.ReadStatement;
import com.java.parser.ast.node.ReferenceAssign;
import com.java.parser.ast.node.ReferenceTail;
import com.java.parser.ast.node.ReferenceType;
import com.java.parser.ast.node.Return;
import com.java.parser.ast.node.TokenList;
import com.java.parser.ast.node.TokenLiteral;
import com.java.parser.ast.node.TupleAccess;
import com.java.parser.ast.node.TupleList;
import com.java.parser.ast.node.UnaryOp;
import com.java.parser.ast.node.VarDecl;
import com.java.parser.ast.node.While;
import com.java.parser.ast.node.EmptyFunctionArgs;
import com.java.parser.ast.node.EmptyStatementList;

public interface ASTVisitor<R> {
    R visitAST(ASTree ast);

    R visitVarDecl(VarDecl node);

    R visitLiteral(TokenLiteral node);

    R visitBinaryOperation(BinaryOp node);

    R visitList(ASTList astListNode);

    R visitAstLiteral(ASTLiteral astLiteralNode);

    R visitFor(For forNode);

    R visitFunctionCall(FunctionCall funCall);

    R visitFunctionLiteral(FunctionLiteral funLiteral);

    R visitIf(If ifNode);

    R visitLoopBody(LoopBody loopBodyNode);

    R visitPrint(Print printNode);

    R visitReadStatement(ReadStatement readNode);

    R visitReturn(Return returnNode);

    R visitTokenList(TokenList tokenListNode);

    R visitTupleAccess(TupleAccess tupleAccess);

    R visitUnaryOperation(UnaryOp unaryOpNode);

    R visitWhile(While whileNode);

    R visitArrayAccess(ArrayAccess arrayAccessNode);

    R visitMultipleDeclarations(MultipleDeclaration multipleDeclarations);

    R visitReferenceAssign(ReferenceAssign referenceAssignNode);

    R visitIdentifierAssign(IdentifierAssign identifierAssignNode);

    R visitExpressionStatement(ExpressionStatement expressionStatementNode);

    R visitReferenceType(ReferenceType referenceTypeNode);

    R visitReferenceTail(ReferenceTail referenceTail);

    R visitEmptyTail(EmptyTail emptyTailNode);

    R visitTupleList(TupleList tupleListNode);

    R visitEmptyStatementList(EmptyStatementList emptyStatementListNode);

    R visitEmptyFunctionArgs(EmptyFunctionArgs emptyFunctionArgsNode);

    R visitIdentifierList(IdentifierList identifierList);
}
