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
import java.util.Comparator;

@SuppressWarnings({"checkstyle:RegexpSinglelineJava", "checkstyle:MultipleStringLiterals"})
public class PrintVisitor implements ASTVisitor<Object> {
    private static final String SEP = " ";
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
    public Object visitAccessTailList(AccessTailList accessTailList) {
        System.out.println(SEP.repeat(ident) + "Tail {");
        for (var node : accessTailList.getTails()) {
            node.accept(new PrintVisitor(ident + 1));
        }
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitBinaryOperation(BinaryOp binaryOp) {
        System.out.println(SEP.repeat(ident) + "Binary Operation {");
        System.out.println(SEP.repeat(ident + 1) + "Left {");
        binaryOp.getLeft().accept(new PrintVisitor(ident + 2));
        System.out.println(SEP.repeat(ident + 1) + "}");
        System.out.println(SEP.repeat(ident + 1) + binaryOp.getOperator());
        System.out.println(SEP.repeat(ident + 1) + "Right {");
        binaryOp.getRight().accept(new PrintVisitor(ident + 2));
        System.out.println(SEP.repeat(ident + 1) + "}");
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitComparisonOp(ComparisonOp comparisonOp) {
        System.out.println(SEP.repeat(ident) + "Comparison Operation {");
        System.out.println(SEP.repeat(ident + 1) + "Left {");
        comparisonOp.getLeft().accept(new PrintVisitor(ident + 2));
        System.out.println(SEP.repeat(ident + 1) + "}");
        System.out.println(SEP.repeat(ident + 1) + comparisonOp.getOperator());
        System.out.println(SEP.repeat(ident + 1) + "Right {");
        comparisonOp.getRight().accept(new PrintVisitor(ident + 2));
        System.out.println(SEP.repeat(ident + 1) + "}");
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitDeclarationsCommaList(DeclarationsCommaList declarationsCommaList) {
        System.out.println(SEP.repeat(ident) + "Declarations {");
        for (var node : declarationsCommaList.getDeclarations()) {
            node.accept(new PrintVisitor(ident + 1));
        }
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitForStatement(ForStatement forStatementNode) {
        System.out.println(SEP.repeat(ident) + "For Loop {");
        System.out.println(SEP.repeat(ident + 1) + "Identifier " + forStatementNode.getIdentifier());
        System.out.println(SEP.repeat(ident + 1) + "Type Indicator " + forStatementNode.getTypeIndicator());
        forStatementNode.getLoopBody().accept(new PrintVisitor(ident + 1));
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitFunctionCall(FunctionCall funCall) {
        System.out.println(SEP.repeat(ident) + "Function call {");
        funCall.getExpressions().accept(new PrintVisitor(ident + 1));
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitFunctionLiteral(FunctionLiteral funLiteral) {
        System.out.println(SEP.repeat(ident) + "Function literal {");
        funLiteral.getParameters().accept(new PrintVisitor(ident + 1));
        funLiteral.getFunBody().accept(new PrintVisitor(ident + 1));
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitIfStatement(IfStatement ifStatementNode) {
        System.out.println(SEP.repeat(ident) + "If {");
        ifStatementNode.getExpression().accept(new PrintVisitor(ident + 1));
        ifStatementNode.getTrueBody().accept(new PrintVisitor(ident + 1));
        if (ifStatementNode.getFalseBody() != null) {
            ifStatementNode.getFalseBody().accept(new PrintVisitor(ident + 1));
        }
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitLogicalOp(LogicalOp logicalOp) {
        System.out.println(SEP.repeat(ident) + "Logical Operation {");
        System.out.println(SEP.repeat(ident + 1) + "Left {");
        logicalOp.getLeft().accept(new PrintVisitor(ident + 2));
        System.out.println(SEP.repeat(ident + 1) + "}");
        System.out.println(SEP.repeat(ident + 1) + logicalOp.getOperator());
        System.out.println(SEP.repeat(ident + 1) + "Right {");
        logicalOp.getRight().accept(new PrintVisitor(ident + 2));
        System.out.println(SEP.repeat(ident + 1) + "}");
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitLoopBody(LoopBody loopBodyNode) {
        System.out.println(SEP.repeat(ident) + "Loop Body {");
        loopBodyNode.getLoopBody().accept(new PrintVisitor(ident + 1));
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitOnlyIdentifiersDeclarationStatement(OnlyIdentifiersDeclarationStatement node) {
        System.out.println(SEP.repeat(ident) + "Declarations (identifiers only) {");
        node.getIdentifiers().accept(new PrintVisitor(ident + 1));
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitPrintStatement(PrintStatement printStatementNode) {
        System.out.println(SEP.repeat(ident) + "Print {");
        printStatementNode.getExpressions().accept(new PrintVisitor(ident + 1));
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitReadStatement(ReadStatement readStatementNode) {
        System.out.println(SEP.repeat(ident) + "Read {");
        System.out.println(SEP.repeat(ident + 1) + readStatementNode.getReadType());
        readStatementNode.getDest().accept(new PrintVisitor(ident + 1));
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitReferenceAssignStatement(ReferenceAssignStatement referenceAssignStatementNode) {
        System.out.println(SEP.repeat(ident) + "Reference Assign {");
        System.out.println(SEP.repeat(ident + 1) + "Identifier " + referenceAssignStatementNode.getIdentifier());
        referenceAssignStatementNode.getExpression().accept(new PrintVisitor(ident + 1));
        referenceAssignStatementNode.getTail().accept(new PrintVisitor(ident + 1));
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitReturnStatement(ReturnStatement returnStatementNode) {
        System.out.println(SEP.repeat(ident) + "Return {");
        returnStatementNode.getExpression().accept(new PrintVisitor(ident + 1));
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitStatementsList(StatementsList statementsList) {
        System.out.println(SEP.repeat(ident) + "Statements {");
        for (var node : statementsList.getStatements()) {
            node.accept(new PrintVisitor(ident + 1));
        }
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitTokenLiteral(TokenLiteral node) {
        System.out.println(SEP.repeat(ident) + "Literal {");
        System.out.println(SEP.repeat(ident + 1) + node.getValue());
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitTuple(Tuple tuple) {
        System.out.println(SEP.repeat(ident) + "Tuple {");
        tuple.getElements().accept(new PrintVisitor(ident + 1));
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitTupleAccess(TupleAccess tupleAccess) {
        System.out.println(SEP.repeat(ident) + "Tuple Access {");
        System.out.println(SEP.repeat(ident + 1) + tupleAccess.getIdentifier());
        System.out.println(SEP.repeat(ident + 1) + tupleAccess.getLiteral());
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitUnaryOp(UnaryOp unaryOpNode) {
        System.out.println(SEP.repeat(ident) + "Unary Operation {");

        if (unaryOpNode.getOperator() != null) {
            System.out.println(SEP.repeat(ident + 1) + unaryOpNode.getOperator());
        }

        unaryOpNode.getUnary().accept(new PrintVisitor(ident + 1));

        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitWhileStatement(WhileStatement whileStatementNode) {
        System.out.println(SEP.repeat(ident) + "While {");
        whileStatementNode.getExpression().accept(new PrintVisitor(ident + 1));
        whileStatementNode.getLoopBody().accept(new PrintVisitor(ident + 1));
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitArrayAccess(ArrayAccess arrayAccessNode) {
        System.out.println(SEP.repeat(ident) + "Array Access {");
        arrayAccessNode.getExpression().accept(new PrintVisitor(ident + 1));
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitArray(Array array) {
        System.out.println(SEP.repeat(ident) + "Array {");
        array.getElements().accept(new PrintVisitor(ident + 1));
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitIdentifierAssign(IdentifierAssign identifierAssignNode) {
        System.out.println(SEP.repeat(ident) + "Assignment {");
        System.out.println(SEP.repeat(ident + 1) + "Identifier " + identifierAssignNode.getIdentifier());
        identifierAssignNode.getExpression().accept(new PrintVisitor(ident + 1));
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitIdentifierWithValue(IdentifierWithValue identifierWithValue) {
        System.out.println(SEP.repeat(ident) + "Identifier with value {");
        System.out.println(SEP.repeat(ident + 1) + "Identifier " + identifierWithValue.getIdentifier());
        identifierWithValue.getValue().accept(new PrintVisitor(ident + 1));
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitIdentifiersCommaList(IdentifiersCommaList tokenListNode) {
        System.out.println(SEP.repeat(ident) + "Identifiers {");
        for (var identifier : tokenListNode.getTokens()) {
            System.out.println(SEP.repeat(ident + 1) + identifier);
        }
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitIdentifiersWithValueDeclarationStatement(
        IdentifiersWithValueDeclarationStatement multipleDeclarationsStatement
    ) {
        System.out.println(SEP.repeat(ident) + "Declarations with values {");
        multipleDeclarationsStatement.getDeclarations().accept(new PrintVisitor(ident + 1));
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitExpressionStatement(ExpressionStatement expressionStatementNode) {
        System.out.println(SEP.repeat(ident) + "Expression statement {");
        expressionStatementNode.getExpression().accept(new PrintVisitor(ident + 1));
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitExpressionsCommaList(ExpressionsCommaList expressionsCommaList) {
        System.out.println(SEP.repeat(ident) + "Expressions {");
        for (var node : expressionsCommaList.getExpressions()) {
            node.accept(new PrintVisitor(ident + 1));
        }
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitReferenceType(ReferenceType referenceTypeNode) {
        System.out.println(SEP.repeat(ident) + "Reference Type {");
        referenceTypeNode.getReference().accept(new PrintVisitor(ident + 1));
        System.out.println(SEP.repeat(ident + 1) + "Type " + referenceTypeNode.getType());
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitReferenceTail(ReferenceTail referenceTail) {
        System.out.println(SEP.repeat(ident) + "Reference Tail {");
        System.out.println(SEP.repeat(ident + 1) + "Identifier " + referenceTail.getIdentifier());
        referenceTail.getTail().accept(new PrintVisitor(ident + 1));
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitEmptyTail(EmptyTail emptyTailNode) {
        System.out.println(SEP.repeat(ident) + "Empty Tail {");
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitExpression(Expression expression) {
        System.out.println(SEP.repeat(ident) + "Expression {");
        expression.accept(new PrintVisitor(ident + 1));
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }

    @Override
    public Object visitTupleList(TupleList tupleListNode) {
        System.out.println(SEP.repeat(ident) + "Tuple list {");
        int index = 0;
        for (var node : tupleListNode.getAllExpressions()) {
            if (tupleListNode.getNamedExpressions().containsValue(node)) {
                var entry = tupleListNode.getNamedExpressions().entrySet().stream()
                    .sorted(Comparator.comparing(o -> o.getKey().lexeme())).toList().get(index++); // temporarily

                System.out.println(SEP.repeat(ident + 1) + "Named element {");
                System.out.println(SEP.repeat(ident + 2) + entry.getKey());
                entry.getValue().accept(new PrintVisitor(ident + 2));
                System.out.println(SEP.repeat(ident + 1) + "}");
            } else {
                node.accept(new PrintVisitor(ident + 1));
            }
        }
        System.out.println(SEP.repeat(ident) + "}");
        return null;
    }
}
