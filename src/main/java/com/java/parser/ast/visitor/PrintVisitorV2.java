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
public class PrintVisitorV2 implements ASTVisitor<Object> {
    private static final String SEP = "\t";
    private final int ident;

    public PrintVisitorV2(int ident) {
        this.ident = ident;
    }

    @Override
    public Object visitAST(ASTree ast) {
        ast.getNodes().accept(this);
        return null;
    }

    @Override
    public Object visitAccessTailList(AccessTailList accessTailList) {
        System.out.println(SEP.repeat(ident) + "TAIL");
        for (var node : accessTailList.getTails()) {
            node.accept(new PrintVisitorV2(ident + 1));
        }
        return null;
    }

    @Override
    public Object visitBinaryOperation(BinaryOp binaryOp) {
        System.out.println(SEP.repeat(ident) + "BINARY EXPRESSION");
        System.out.println(SEP.repeat(ident + 1) + "LEFT");
        binaryOp.getLeft().accept(new PrintVisitorV2(ident + 2));
        System.out.println(SEP.repeat(ident + 1) + binaryOp.getOperator());
        System.out.println(SEP.repeat(ident + 1) + "RIGHT");
        binaryOp.getRight().accept(new PrintVisitorV2(ident + 2));
        return null;
    }

    @Override
    public Object visitComparisonOp(ComparisonOp comparisonOp) {
        System.out.println(SEP.repeat(ident) + "COMPARISON");
        System.out.println(SEP.repeat(ident + 1) + "LEFT");
        comparisonOp.getLeft().accept(new PrintVisitorV2(ident + 2));
        System.out.println(SEP.repeat(ident + 1) + comparisonOp.getOperator());
        System.out.println(SEP.repeat(ident + 1) + "RIGHT");
        comparisonOp.getRight().accept(new PrintVisitorV2(ident + 2));
        return null;
    }

    @Override
    public Object visitDeclarationsCommaList(DeclarationsCommaList declarationsCommaList) {
        System.out.println(SEP.repeat(ident) + "DECLARATIONS");
        for (var node : declarationsCommaList.getDeclarations()) {
            node.accept(new PrintVisitorV2(ident + 1));
        }
        return null;
    }

    @Override
    public Object visitForStatement(ForStatement forStatementNode) {
        System.out.println(SEP.repeat(ident) + "FOR");
        System.out.println(SEP.repeat(ident + 1) + "IDENT " + forStatementNode.getIdentifier());
        System.out.println(SEP.repeat(ident + 1) + "TYPE ID" + forStatementNode.getTypeIndicator());
        forStatementNode.getLoopBody().accept(new PrintVisitorV2(ident + 1));
        return null;
    }

    @Override
    public Object visitFunctionCall(FunctionCall funCall) {
        System.out.println(SEP.repeat(ident) + "CALL");
        funCall.getExpressions().accept(new PrintVisitorV2(ident + 1));
        return null;
    }

    @Override
    public Object visitFunctionLiteral(FunctionLiteral funLiteral) {
        System.out.println(SEP.repeat(ident) + "FUNCTION LITERAL");
        funLiteral.getParameters().accept(new PrintVisitorV2(ident + 1));
        if (funLiteral.getFunBody() != null) {
            funLiteral.getFunBody().accept(new PrintVisitorV2(ident + 1));
        }
        return null;
    }

    @Override
    public Object visitIfStatement(IfStatement ifStatementNode) {
        System.out.println(SEP.repeat(ident) + "IF");
        if (ifStatementNode.getExpression() != null) {
            ifStatementNode.getExpression().accept(new PrintVisitorV2(ident + 1));
        }
        ifStatementNode.getTrueBody().accept(new PrintVisitorV2(ident + 1));
        if (ifStatementNode.getFalseBody() != null) {
            ifStatementNode.getFalseBody().accept(new PrintVisitorV2(ident + 1));
        }
        return null;
    }

    @Override
    public Object visitLogicalOp(LogicalOp logicalOp) {
        System.out.println(SEP.repeat(ident) + "LOGICAL EXPRESSION");
        System.out.println(SEP.repeat(ident + 1) + "LEFT");
        logicalOp.getLeft().accept(new PrintVisitorV2(ident + 2));
        System.out.println(SEP.repeat(ident + 1) + logicalOp.getOperator());
        System.out.println(SEP.repeat(ident + 1) + "RIGHT");
        logicalOp.getRight().accept(new PrintVisitorV2(ident + 2));
        return null;
    }

    @Override
    public Object visitLoopBody(LoopBody loopBodyNode) {
        System.out.println(SEP.repeat(ident) + "LOOP BODY");
        loopBodyNode.getLoopBody().accept(new PrintVisitorV2(ident + 1));
        return null;
    }

    @Override
    public Object visitOnlyIdentifiersDeclarationStatement(OnlyIdentifiersDeclarationStatement node) {
        System.out.println(SEP.repeat(ident) + "DECLARATIONS (only id)");
        node.getIdentifiers().accept(new PrintVisitorV2(ident + 1));
        return null;
    }

    @Override
    public Object visitPrintStatement(PrintStatement printStatementNode) {
        System.out.println(SEP.repeat(ident) + "PRINT");
        printStatementNode.getExpressions().accept(new PrintVisitorV2(ident + 1));
        return null;
    }

    @Override
    public Object visitReadStatement(ReadStatement readStatementNode) {
        System.out.println(SEP.repeat(ident) + "READ");
        System.out.println(SEP.repeat(ident + 1) + readStatementNode.getReadType());
        readStatementNode.getDest().accept(new PrintVisitorV2(ident + 1));
        return null;
    }

    @Override
    public Object visitReferenceAssignStatement(ReferenceAssignStatement referenceAssignStatementNode) {
        System.out.println(SEP.repeat(ident) + "REFERENCE ASSIGN");
        System.out.println(SEP.repeat(ident + 1) + "IDENT " + referenceAssignStatementNode.getIdentifier());
        referenceAssignStatementNode.getExpression().accept(new PrintVisitorV2(ident + 1));
        referenceAssignStatementNode.getTail().accept(new PrintVisitorV2(ident + 1));
        return null;
    }

    @Override
    public Object visitReturnStatement(ReturnStatement returnStatementNode) {
        System.out.println(SEP.repeat(ident) + "RETURN");
        if (returnStatementNode.getExpression() != null) {
            returnStatementNode.getExpression().accept(new PrintVisitorV2(ident + 1));
        }
        return null;
    }

    @Override
    public Object visitStatementsList(StatementsList statementsList) {
        System.out.println(SEP.repeat(ident) + "STATEMENTS");
        for (var node : statementsList.getStatements()) {
            node.accept(new PrintVisitorV2(ident + 1));
        }
        return null;
    }

    @Override
    public Object visitTokenLiteral(TokenLiteral node) {
        System.out.println(SEP.repeat(ident) + "LITERAL");
        System.out.println(SEP.repeat(ident + 1) + node.getValue());
        return null;
    }

    @Override
    public Object visitTuple(Tuple tuple) {
        System.out.println(SEP.repeat(ident) + "TUPLE");
        tuple.getElements().accept(new PrintVisitorV2(ident + 1));
        return null;
    }

    @Override
    public Object visitTupleAccess(TupleAccess tupleAccess) {
        System.out.println(SEP.repeat(ident) + "TUPLE ACCESS");
        System.out.println(SEP.repeat(ident + 1) + tupleAccess.getIdentifier());
        System.out.println(SEP.repeat(ident + 1) + tupleAccess.getLiteral());
        return null;
    }

    @Override
    public Object visitUnaryOp(UnaryOp unaryOpNode) {
        System.out.println(SEP.repeat(ident) + "UNARY EXPRESSION");

        if (unaryOpNode.getOperator() != null) {
            System.out.println(SEP.repeat(ident + 1) + unaryOpNode.getOperator());
        }

        unaryOpNode.getUnary().accept(new PrintVisitorV2(ident + 1));

        return null;
    }

    @Override
    public Object visitWhileStatement(WhileStatement whileStatementNode) {
        System.out.println(SEP.repeat(ident) + "WHILE");
        if (whileStatementNode.getExpression() != null) {
            whileStatementNode.getExpression().accept(new PrintVisitorV2(ident + 1));
        }
        whileStatementNode.getLoopBody().accept(new PrintVisitorV2(ident + 1));
        return null;
    }

    @Override
    public Object visitArrayAccess(ArrayAccess arrayAccessNode) {
        System.out.println(SEP.repeat(ident) + "ARRAY ACCESS");
        arrayAccessNode.getExpression().accept(new PrintVisitorV2(ident + 1));
        return null;
    }

    @Override
    public Object visitArray(Array array) {
        System.out.println(SEP.repeat(ident) + "ARRAY");
        array.getElements().accept(new PrintVisitorV2(ident + 1));
        return null;
    }

    @Override
    public Object visitIdentifierAssign(IdentifierAssign identifierAssignNode) {
        System.out.println(SEP.repeat(ident) + "IDENT ASSIGNMENT");
        System.out.println(SEP.repeat(ident + 1) + "IDENT " + identifierAssignNode.getIdentifier());
        if (identifierAssignNode.getExpression() != null) {
            identifierAssignNode.getExpression().accept(new PrintVisitorV2(ident + 1));
        }
        return null;
    }

    @Override
    public Object visitIdentifierWithValue(IdentifierWithValue identifierWithValue) {
        System.out.println(SEP.repeat(ident) + "IDENT (with value)");
        System.out.println(SEP.repeat(ident + 1) + "IDENT " + identifierWithValue.getIdentifier());
        identifierWithValue.getValue().accept(new PrintVisitorV2(ident + 1));
        return null;
    }

    @Override
    public Object visitIdentifiersCommaList(IdentifiersCommaList tokenListNode) {
        System.out.println(SEP.repeat(ident) + "IDENT");
        for (var identifier : tokenListNode.getTokens()) {
            System.out.println(SEP.repeat(ident + 1) + identifier);
        }
        return null;
    }

    @Override
    public Object visitIdentifiersWithValueDeclarationStatement(
        IdentifiersWithValueDeclarationStatement multipleDeclarationsStatement
    ) {
        System.out.println(SEP.repeat(ident) + "DECLARATIONS (with values)");
        multipleDeclarationsStatement.getDeclarations().accept(new PrintVisitorV2(ident + 1));
        return null;
    }

    @Override
    public Object visitExpressionStatement(ExpressionStatement expressionStatementNode) {
        System.out.println(SEP.repeat(ident) + "EXPRESSION STMT");
        expressionStatementNode.getExpression().accept(new PrintVisitorV2(ident + 1));
        return null;
    }

    @Override
    public Object visitExpressionsCommaList(ExpressionsCommaList expressionsCommaList) {
        System.out.println(SEP.repeat(ident) + "EXPRESSIONS");
        for (var node : expressionsCommaList.getExpressions()) {
            node.accept(new PrintVisitorV2(ident + 1));
        }
        return null;
    }

    @Override
    public Object visitReferenceType(ReferenceType referenceTypeNode) {
        System.out.println(SEP.repeat(ident) + "REFERENCE TYPE");
        referenceTypeNode.getReference().accept(new PrintVisitorV2(ident + 1));
        System.out.println(SEP.repeat(ident + 1) + "TYPE " + referenceTypeNode.getType());
        return null;
    }

    @Override
    public Object visitReferenceTail(ReferenceTail referenceTail) {
        System.out.println(SEP.repeat(ident) + "REFERENCE TAIL");
        System.out.println(SEP.repeat(ident + 1) + "IDENT " + referenceTail.getIdentifier());
        referenceTail.getTail().accept(new PrintVisitorV2(ident + 1));
        return null;
    }

    @Override
    public Object visitEmptyTail(EmptyTail emptyTailNode) {
        System.out.println(SEP.repeat(ident) + "EMPTY TAIL");
        return null;
    }

    @Override
    public Object visitExpression(Expression expression) {
        System.out.println(SEP.repeat(ident) + "EXPRESSION");
        expression.accept(new PrintVisitorV2(ident + 1));
        System.out.println(SEP.repeat(ident));
        return null;
    }

    @Override
    public Object visitTupleList(TupleList tupleListNode) {
        System.out.println(SEP.repeat(ident) + "TUPLE LIST");
        int index = 0;
        for (var node : tupleListNode.getAllExpressions()) {
            if (tupleListNode.getNamedExpressions().containsValue(node)) {
                var entry = tupleListNode.getNamedExpressions().entrySet().stream()
                    .sorted(Comparator.comparing(o -> o.getKey().lexeme())).toList().get(index++); // temporarily

                System.out.println(SEP.repeat(ident + 1) + "NAMED ITEM");
                System.out.println(SEP.repeat(ident + 2) + entry.getKey());
                entry.getValue().accept(new PrintVisitorV2(ident + 2));
            } else {
                node.accept(new PrintVisitorV2(ident + 1));
            }
        }
        return null;
    }
}
