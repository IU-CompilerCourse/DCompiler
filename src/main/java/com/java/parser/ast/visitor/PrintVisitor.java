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
    public Object visitVarDeclNode(VarDeclNode node) {
        System.out.println(" ".repeat(ident) + "Declaration {");
        node.getIdentifiers().accept(new PrintVisitor(ident + 1));
        if (node.getInitialValue() != null) {
            node.getInitialValue().accept(new PrintVisitor(ident + 1));
        }
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitLiteralNode(TokenLiteralNode node) {
        System.out.println(" ".repeat(ident) + "Literal {");
        System.out.println(" ".repeat(ident + 1) + node.getValue());
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitBinaryOperation(BinaryOpNode node) {
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
    public Object visitListNode(ASTListNode astListNode) {
        System.out.println(" ".repeat(ident) + "ListNode {");
        for (var node : astListNode.getNodes()) {
            node.accept(new PrintVisitor(ident + 1));
        }
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitAstLiteralNode(ASTLiteralNode astLiteralNode) {
        System.out.println(" ".repeat(ident) + "ContainerNode {");
        System.out.println(" ".repeat(ident + 1) + astLiteralNode.getType());
        if (astLiteralNode.getValue() != null) {
            astLiteralNode.getValue().accept(new PrintVisitor(ident + 1));
        }
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitForNode(ForNode forNode) {
        System.out.println(" ".repeat(ident) + "For Loop {");
        System.out.println(" ".repeat(ident + 1) + "Identifier " + forNode.getIdentifier());
        System.out.println(" ".repeat(ident + 1) + "Type Indicator " + forNode.getTypeIndicator());
        forNode.getLoopBody().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitFunctionCall(FunctionCallNode funCall) {
        System.out.println(" ".repeat(ident) + "Function call {");
        funCall.getExpressionList().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitFunctionLiteral(FunctionLiteralNode funLiteral) {
        System.out.println(" ".repeat(ident) + "Function literal {");
        funLiteral.getParameters().accept(new PrintVisitor(ident + 1));
        funLiteral.getFunBody().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitIfNode(IfNode ifNode) {
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
    public Object visitLoopBodyNode(LoopBodyNode loopBodyNode) {
        System.out.println(" ".repeat(ident) + "Loop Body {");
        loopBodyNode.getLoopBody().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitPrintNode(PrintNode printNode) {
        System.out.println(" ".repeat(ident) + "Print {");
        printNode.getNode().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitReadStatementNode(ReadStatementNode readStatementNode) {
        System.out.println(" ".repeat(ident) + "Read {");
        System.out.println(" ".repeat(ident + 1) + readStatementNode.getReadType());
        readStatementNode.getDest().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitReturnNode(ReturnNode returnNode) {
        System.out.println(" ".repeat(ident) + "Return {");
        returnNode.getNode().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitTokenList(TokenListNode tokenListNode) {
        System.out.println(" ".repeat(ident) + "Token List {");
        for (var node : tokenListNode.getTokens()) {
            System.out.println(" ".repeat(ident + 1) + node);
        }
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitTupleAccessNode(TupleAccessNode tupleAccess) {
        System.out.println(" ".repeat(ident) + "Tuple Access {");
        System.out.println(" ".repeat(ident + 1) + tupleAccess.getIdentifier());
        System.out.println(" ".repeat(ident + 1) + tupleAccess.getLiteral());
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitUnaryOperation(UnaryOpNode unaryOpNode) {
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
    public Object visitWhileNode(WhileNode whileNode) {
        System.out.println(" ".repeat(ident) + "While {");
        whileNode.getExpression().accept(new PrintVisitor(ident + 1));
        whileNode.getLoopBody().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitArrayAccess(ArrayAccessNode arrayAccessNode) {
        System.out.println(" ".repeat(ident) + "Array Access {");
        arrayAccessNode.getExpression().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitMultipleDeclarations(MultipleDeclarationNode multipleDeclarations) {
        System.out.println(" ".repeat(ident) + "Multiple Declarations {");
        multipleDeclarations.getDeclarations().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitReferenceAssign(ReferenceAssignNode referenceAssignNode) {
        System.out.println(" ".repeat(ident) + "Reference Assign {");
        System.out.println(" ".repeat(ident + 1) + "Identifier " + referenceAssignNode.getIdentifier());
        referenceAssignNode.getExpression().accept(new PrintVisitor(ident + 1));
        referenceAssignNode.getTail().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitIdentifierAssign(IdentifierAssignNode identifierAssignNode) {
        System.out.println(" ".repeat(ident) + "Identifier Assign {");
        System.out.println(" ".repeat(ident + 1) + "Identifier " + identifierAssignNode.getIdentifier());
        identifierAssignNode.getExpression().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitExpressionStatement(ExpressionStatementNode expressionStatementNode) {
        System.out.println(" ".repeat(ident) + "Expression statement {");
        expressionStatementNode.getExpression().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitReferenceType(ReferenceTypeNode referenceTypeNode) {
        System.out.println(" ".repeat(ident) + "Reference Type {");
        referenceTypeNode.getReference().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident + 1) + "Type " + referenceTypeNode.getType());
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitReferenceTail(ReferenceTailNode referenceTail) {
        System.out.println(" ".repeat(ident) + "Reference Tail {");
        System.out.println(" ".repeat(ident + 1) + "Identifier " + referenceTail.getIdentifier());
        referenceTail.getTail().accept(new PrintVisitor(ident + 1));
        System.out.println(" ".repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitEmptyTail(EmptyTailNode emptyTailNode) {
        return null;
    }

    @Override
    public Object visitTupleList(TupleListNode tupleListNode) {
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
    public Object visitEmptyStatementList(EmptyStatementListNode emptyStatementListNode) {
        System.out.println(" ".repeat(ident) + "Empty Statement List");
        return null;
    }

    @Override
    public Object visitEmptyFunctionArgs(EmptyFunctionArgsNode emptyFunctionArgsNode) {
        System.out.println(" ".repeat(ident) + "Empty Function Arguments");
        return null;
    }
}
