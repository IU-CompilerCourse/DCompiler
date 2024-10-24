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

@SuppressWarnings({"checkstyle:RegexpSinglelineJava", "checkstyle:MultipleStringLiterals"})
public class PrintVisitor implements ASTVisitor<Object> {
    private final int ident;

    public PrintVisitor(int ident) {
        this.ident = ident;
    }

    @Override
    public Object visitAST(ASTree ast) {
        ast.getNodes().accept(this);
        return null;
    }

    @Override
    public Object visitVarDecl(VarDecl node) {
        System.out.println(" ".repeat(ident) + "Declaration {");
        node.getIdentifiers().accept(new PrintVisitor(ident + 1));
        if (node.getInitialValue() != null) {
            node.getInitialValue().accept(new PrintVisitor(ident + 1));
        }
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitLiteral(TokenLiteral node) {
        System.out.println(" ".repeat(ident) + "Literal {");
        System.out.println(" ".repeat(ident + 1) + node.getValue());
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitBinaryOperation(BinaryOp node) {
        System.out.println(" ".repeat(ident) + "Binary Operation {");
        System.out.println(" ".repeat(ident + 1) + "Left {");
        node.getLeft().accept(new PrintVisitor(ident + 2));
        System.out.println(" ".repeat(ident + 1) + "}");
        System.out.println(" ".repeat(ident + 1) + node.getOperator());
        System.out.println(" ".repeat(ident + 1) + "Right {");
        node.getRight().accept(new PrintVisitor(ident + 2));
        System.out.println(" ".repeat(ident + 1) + "}");
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitList(ASTList astListNode) {
        System.out.println(" ".repeat(ident) + "ListNode {");
        for (var node : astListNode.getNodes()) {
            node.accept(new PrintVisitor(ident + 1));
        }
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitAstLiteral(ASTLiteral astLiteralNode) {
        System.out.println(" ".repeat(ident) + "ContainerNode {");
        System.out.println(" ".repeat(ident + 1) + astLiteralNode.getType());
        if (astLiteralNode.getValue() != null) {
            astLiteralNode.getValue().accept(new PrintVisitor(ident + 1));
        }
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitFor(For forNode) {
        System.out.println(" ".repeat(ident) + "For Loop {");
        System.out.println(" ".repeat(ident + 1) + "Identifier " + forNode.getIdentifier());
        System.out.println(" ".repeat(ident + 1) + "Type Indicator " + forNode.getTypeIndicator());
        forNode.getLoopBody().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitFunctionCall(FunctionCall funCall) {
        System.out.println(" ".repeat(ident) + "Function call {");
        funCall.getExpressionList().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitFunctionLiteral(FunctionLiteral funLiteral) {
        System.out.println(" ".repeat(ident) + "Function literal {");
        funLiteral.getParameters().accept(new PrintVisitor(ident + 1));
        funLiteral.getFunBody().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitIf(If ifNode) {
        System.out.println(" ".repeat(ident) + "If Statement {");
        ifNode.getExpression().accept(new PrintVisitor(ident + 1));
        ifNode.getTrueBody().accept(new PrintVisitor(ident + 1));
        if (ifNode.getFalseBody() != null) {
            ifNode.getFalseBody().accept(new PrintVisitor(ident + 1));
        }
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitLoopBody(LoopBody loopBodyNode) {
        System.out.println(" ".repeat(ident) + "Loop Body {");
        loopBodyNode.getLoopBody().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitPrint(Print printNode) {
        System.out.println(" ".repeat(ident) + "Print {");
        printNode.getNode().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitReadStatement(ReadStatement readStatementNode) {
        System.out.println(" ".repeat(ident) + "Read {");
        System.out.println(" ".repeat(ident + 1) + readStatementNode.getReadType());
        readStatementNode.getDest().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitReturn(Return returnNode) {
        System.out.println(" ".repeat(ident) + "Return {");
        returnNode.getNode().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitTokenList(TokenList tokenListNode) {
        System.out.println(" ".repeat(ident) + "Token List {");
        for (var node : tokenListNode.getTokens()) {
            System.out.println(" ".repeat(ident + 1) + node);
        }
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitTupleAccess(TupleAccess tupleAccess) {
        System.out.println(" ".repeat(ident) + "Tuple Access {");
        System.out.println(" ".repeat(ident + 1) + tupleAccess.getIdentifier());
        System.out.println(" ".repeat(ident + 1) + tupleAccess.getLiteral());
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitUnaryOperation(UnaryOp unaryOpNode) {
        System.out.println(" ".repeat(ident) + "Unary Operator {");

        if (unaryOpNode.getOperator() != null) {
            System.out.println(" ".repeat(ident + 1) + unaryOpNode.getOperator());
        }

        unaryOpNode.getUnary().accept(new PrintVisitor(ident + 1));

        if (unaryOpNode.getTypeIndicator() != null) {
            unaryOpNode.getTypeIndicator().accept(new PrintVisitor(ident + 1));
        }

        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitWhile(While whileNode) {
        System.out.println(" ".repeat(ident) + "While {");
        whileNode.getExpression().accept(new PrintVisitor(ident + 1));
        whileNode.getLoopBody().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitArrayAccess(ArrayAccess arrayAccessNode) {
        System.out.println(" ".repeat(ident) + "Array Access {");
        arrayAccessNode.getExpression().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitMultipleDeclarations(MultipleDeclaration multipleDeclarations) {
        System.out.println(" ".repeat(ident) + "Multiple Declarations {");
        multipleDeclarations.getDeclarations().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitReferenceAssign(ReferenceAssign referenceAssignNode) {
        System.out.println(" ".repeat(ident) + "Reference Assign {");
        System.out.println(" ".repeat(ident + 1) + "Identifier " + referenceAssignNode.getIdentifier());
        referenceAssignNode.getExpression().accept(new PrintVisitor(ident + 1));
        referenceAssignNode.getTail().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitIdentifierAssign(IdentifierAssign identifierAssignNode) {
        System.out.println(" ".repeat(ident) + "Identifier Assign {");
        System.out.println(" ".repeat(ident + 1) + "Identifier " + identifierAssignNode.getIdentifier());
        identifierAssignNode.getExpression().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitExpressionStatement(ExpressionStatement expressionStatementNode) {
        System.out.println(" ".repeat(ident) + "Expression statement {");
        expressionStatementNode.getExpression().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitReferenceType(ReferenceType referenceTypeNode) {
        System.out.println(" ".repeat(ident) + "Reference Type {");
        referenceTypeNode.getReference().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident + 1) + "Type " + referenceTypeNode.getType());
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitReferenceTail(ReferenceTail referenceTail) {
        System.out.println(" ".repeat(ident) + "Reference Tail {");
        System.out.println(" ".repeat(ident + 1) + "Identifier " + referenceTail.getIdentifier());
        referenceTail.getTail().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitEmptyTail(EmptyTail emptyTailNode) {
        return null;
    }

    @Override
    public Object visitTupleList(TupleList tupleListNode) {
        System.out.println(" ".repeat(ident) + "Tuple list {");
        int index = 0;
        for (var node : tupleListNode.getAllNodes()) {
            if (tupleListNode.getNamedNodes().containsValue(node)) {
                var entry = tupleListNode.getNamedNodes().entrySet().stream().toList().get(index++);

                System.out.println(" ".repeat(ident + 1) + "Named element {");
                System.out.println(" ".repeat(ident + 2) + entry.getKey());
                entry.getValue().accept(new PrintVisitor(ident + 2));
                System.out.println(" ".repeat(ident + 1) + "}");
            } else {
                node.accept(new PrintVisitor(ident + 1));
            }
        }
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitEmptyStatementList(EmptyStatementList emptyStatementListNode) {
        System.out.println(" ".repeat(ident) + "Empty Statement List");
        return null;
    }

    @Override
    public Object visitEmptyFunctionArgs(EmptyFunctionArgs emptyFunctionArgsNode) {
        System.out.println(" ".repeat(ident) + "Empty Function Arguments");
        return null;
    }

    @Override
    public Object visitIdentifierList(IdentifierList identifierList) {
        System.out.println(" ".repeat(ident) + "Identifier List {");
        for (var identifier : identifierList.getIdentifiers()) {
            System.out.println(" ".repeat(ident + 1) + identifier);
        }
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

}
