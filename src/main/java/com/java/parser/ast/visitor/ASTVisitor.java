package com.java.parser.ast.visitor;

import com.java.parser.ast.ASTree;
import com.java.parser.ast.node.ASTListNode;
import com.java.parser.ast.node.ASTLiteralNode;
import com.java.parser.ast.node.ArrayAccessNode;
import com.java.parser.ast.node.BinaryOpNode;
import com.java.parser.ast.node.EmptyTailNode;
import com.java.parser.ast.node.ExpressionStatementNode;
import com.java.parser.ast.node.ForNode;
import com.java.parser.ast.node.FunctionCallNode;
import com.java.parser.ast.node.FunctionLiteralNode;
import com.java.parser.ast.node.IdentifierAssignNode;
import com.java.parser.ast.node.IfNode;
import com.java.parser.ast.node.LoopBodyNode;
import com.java.parser.ast.node.MultipleDeclarationNode;
import com.java.parser.ast.node.PrintNode;
import com.java.parser.ast.node.ReadStatementNode;
import com.java.parser.ast.node.ReferenceAssignNode;
import com.java.parser.ast.node.ReferenceTailNode;
import com.java.parser.ast.node.ReferenceTypeNode;
import com.java.parser.ast.node.ReturnNode;
import com.java.parser.ast.node.TokenListNode;
import com.java.parser.ast.node.TokenLiteralNode;
import com.java.parser.ast.node.TupleAccessNode;
import com.java.parser.ast.node.TupleListNode;
import com.java.parser.ast.node.UnaryOpNode;
import com.java.parser.ast.node.VarDeclNode;
import com.java.parser.ast.node.WhileNode;
import com.java.parser.ast.node.EmptyFunctionArgsNode;
import com.java.parser.ast.node.EmptyStatementListNode;

public interface ASTVisitor<R> {
    R visitAST(ASTree ast);

    R visitVarDeclNode(VarDeclNode node);

    R visitLiteralNode(TokenLiteralNode node);

    R visitBinaryOperation(BinaryOpNode node);

    R visitListNode(ASTListNode astListNode);

    R visitAstLiteralNode(ASTLiteralNode astLiteralNode);

    R visitForNode(ForNode forNode);

    R visitFunctionCall(FunctionCallNode funCall);

    R visitFunctionLiteral(FunctionLiteralNode funLiteral);

    R visitIfNode(IfNode ifNode);

    R visitLoopBodyNode(LoopBodyNode loopBodyNode);

    R visitPrintNode(PrintNode printNode);

    R visitReadStatementNode(ReadStatementNode readNode);

    R visitReturnNode(ReturnNode returnNode);

    R visitTokenList(TokenListNode tokenListNode);

    R visitTupleAccessNode(TupleAccessNode tupleAccess);

    R visitUnaryOperation(UnaryOpNode unaryOpNode);

    R visitWhileNode(WhileNode whileNode);

    R visitArrayAccess(ArrayAccessNode arrayAccessNode);

    R visitMultipleDeclarations(MultipleDeclarationNode multipleDeclarations);

    R visitReferenceAssign(ReferenceAssignNode referenceAssignNode);

    R visitIdentifierAssign(IdentifierAssignNode identifierAssignNode);

    R visitExpressionStatement(ExpressionStatementNode expressionStatementNode);

    R visitReferenceType(ReferenceTypeNode referenceTypeNode);

    R visitReferenceTail(ReferenceTailNode referenceTail);

    R visitEmptyTail(EmptyTailNode emptyTailNode);

    R visitTupleList(TupleListNode tupleListNode);

    R visitEmptyStatementList(EmptyStatementListNode emptyStatementListNode);

    R visitEmptyFunctionArgs(EmptyFunctionArgsNode emptyFunctionArgsNode);
}
