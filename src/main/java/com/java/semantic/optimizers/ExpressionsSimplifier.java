package com.java.semantic.optimizers;

import com.java.lexer.Token;
import com.java.lexer.TokenType;
import com.java.parser.ast.ASTree;
import com.java.parser.ast.node.ephemeral.ASTNode;
import com.java.parser.ast.node.ephemeral.ExpressionEphemeral;
import com.java.parser.ast.node.ephemeral.Relation;
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
import com.java.semantic.errors.CorruptedOpException;
import com.java.semantic.errors.Error;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import lombok.Getter;

@SuppressWarnings("all")
@Getter
public class ExpressionsSimplifier implements ASTVisitor<Object> {
    private final List<Error> errors;

    public ExpressionsSimplifier() {
        this.errors = new ArrayList<>();
    }

    @Override
    public List<Error> visitAST(ASTree ast) {
        ast.getNodes().accept(this);
        return errors;
    }

    @Override
    public Object visitAccessTailList(AccessTailList accessTailList) {
        accessTailList.getTails().forEach(tail -> tail.accept(this));
        return accessTailList;
    }

    @Override
    public Object visitArrayAccess(ArrayAccess arrayAccessNode) {
        arrayAccessNode.setExpression(
            (ExpressionEphemeral) arrayAccessNode.getExpression().accept(this)
        );
        return arrayAccessNode;
    }

    @Override
    public Object visitArray(Array array) {
        array.getElements().accept(this);
        return array;
    }

    @Override
    public Object visitBinaryOperation(BinaryOp node) {
        var maybeLeft = fetchReducedValue(node.getLeft());
        var maybeRight = fetchReducedValue(node.getRight());

        if (Stream.of(maybeLeft, maybeRight).anyMatch(reduced -> reduced == null)) {
            return node;
        }

        var leftToken = maybeLeft.getValue();
        var rightToken = maybeRight.getValue();
        var op = node.getOperator();

        Integer integerValue = null;
        Double doubleValue = null;
        String stringValue = null;

        switch (leftToken.type()) {
            case IntLiteral -> {

                switch (rightToken.type()) {
                    case IntLiteral -> {
                        integerValue = (Integer) evalAlgebraic(
                            leftToken.lexeme(),
                            rightToken.lexeme(),
                            op.type(),
                            TokenType.IntLiteral
                        );
                        break;
                    }

                    case DoubleLiteral -> {
                        doubleValue = (Double) evalAlgebraic(
                            leftToken.lexeme(),
                            rightToken.lexeme(),
                            op.type(),
                            TokenType.DoubleLiteral
                        );
                        break;
                    }

                    case StringLiteral -> {
                        errors.add(new CorruptedOpException(
                            (String) leftToken.literal(),
                            (String) rightToken.literal(),
                            op.type(),
                            null
                        ));
                        break;
                    }
                }
            }

            case DoubleLiteral -> {

                switch (rightToken.type()) {
                    case IntLiteral, DoubleLiteral -> {
                        doubleValue = (Double) evalAlgebraic(
                            leftToken.lexeme(),
                            rightToken.lexeme(),
                            op.type(),
                            TokenType.DoubleLiteral
                        );
                        break;
                    }

                    case StringLiteral -> {
                        errors.add(new CorruptedOpException(
                            leftToken.lexeme(),
                            rightToken.lexeme(),
                            op.type(),
                            null
                        ));
                        break;
                    }
                }
            }

            case StringLiteral -> {

                switch (rightToken.type()) {
                    case IntLiteral, DoubleLiteral -> {
                        errors.add(new CorruptedOpException(
                            leftToken.lexeme(),
                            rightToken.lexeme(),
                            op.type(),
                            null
                        ));
                        break;
                    }

                    case StringLiteral -> {
                        stringValue = (String) evalAlgebraic(
                            (String) leftToken.literal(),
                            (String) rightToken.literal(),
                            op.type(),
                            TokenType.StringLiteral
                        );
                        break;
                    }
                }
            }

            default -> {
                errors.add(new CorruptedOpException(
                    leftToken.lexeme(),
                    rightToken.lexeme(),
                    op.type(),
                    null
                ));
                break;
            }
        }

        var line = op.line();
        if (integerValue != null) {
            return new TokenLiteral(new Token(TokenType.IntLiteral, String.valueOf(integerValue), integerValue, line));
        } else if (doubleValue != null) {
            return new TokenLiteral(new Token(TokenType.DoubleLiteral, String.valueOf(doubleValue), doubleValue, line));
        } else if (stringValue != null) {
            return new TokenLiteral(new Token(TokenType.StringLiteral, stringValue, stringValue, line));
        }

        return node;
    }

    private Object evalAlgebraic(String left, String right, TokenType op, TokenType finalType) {
        switch (op) {
            case Plus -> {
                switch (finalType) {
                    case IntLiteral -> {
                        return Integer.parseInt(left) + Integer.parseInt(right);
                    }
                    case DoubleLiteral -> {
                        return Double.parseDouble(left) + Double.parseDouble(right);
                    }
                    case StringLiteral -> {
                        return left + right;
                    }
                    default -> {
                        errors.add(new CorruptedOpException(left, right, op, finalType));
                        break;
                    }
                }
            }

            case Minus -> {
                switch (finalType) {
                    case IntLiteral -> {
                        return Integer.parseInt(left) - Integer.parseInt(right);
                    }
                    case DoubleLiteral -> {
                        return Double.parseDouble(left) - Double.parseDouble(right);
                    }
                    default -> {
                        errors.add(new CorruptedOpException(left, right, op, finalType));
                        break;
                    }
                }
            }

            case Star -> {
                switch (finalType) {
                    case IntLiteral -> {
                        return Integer.parseInt(left) * Integer.parseInt(right);
                    }
                    case DoubleLiteral -> {
                        return Double.parseDouble(left) * Double.parseDouble(right);
                    }
                    default -> {
                        errors.add(new CorruptedOpException(left, right, op, finalType));
                        break;
                    }
                }
            }

            case Slash -> {
                switch (finalType) {
                    case IntLiteral -> {
                        return Integer.parseInt(left) / Integer.parseInt(right);
                    }
                    case DoubleLiteral -> {
                        return Double.parseDouble(left) / Double.parseDouble(right);
                    }
                    default -> {
                        errors.add(new CorruptedOpException(left, right, op, finalType));
                        break;
                    }
                }
            }

            default -> {
                errors.add(new CorruptedOpException(left, right, op, finalType));
                break;
            }
        }

        return null;
    }

    private Boolean evalComparison(String left, String right, TokenType op) {
        var leftValue = Double.parseDouble(left);
        var rightValue = Double.parseDouble(right);

        switch (op) {
            case Less -> {
                return Double.compare(leftValue, rightValue) < 0;
            }
            case LessEqual -> {
                return Double.compare(leftValue, rightValue) <= 0;
            }
            case Greater -> {
                return Double.compare(leftValue, rightValue) > 0;
            }
            case GreaterEqual -> {
                return Double.compare(leftValue, rightValue) >= 0;
            }
            case Equal -> {
                return Double.compare(leftValue, rightValue) == 0;
            }
            case NotEqual -> {
                return Double.compare(leftValue, rightValue) != 0;
            }
        }

        return null;
    }

    private Boolean evalLogical(String left, String right, TokenType op) {
        var leftValue = Boolean.parseBoolean(left);
        var rightValue = Boolean.parseBoolean(right);

        switch (op) {
            case And -> {
                return leftValue && rightValue;
            }
            case Or -> {
                return leftValue || rightValue;
            }
            case Xor -> {
                return Boolean.logicalXor(leftValue, rightValue);
            }
        }

        return null;
    }

    private Object evalUnary(String unary, TokenType op, TokenType finalType) {
        switch (op) {
            case Plus -> {

                switch (finalType) {
                    case IntLiteral -> {
                        return Integer.parseInt(unary);
                    }

                    case DoubleLiteral -> {
                        return Double.parseDouble(unary);
                    }

                    default -> {
                        errors.add(new CorruptedOpException(unary, null, op, finalType));
                        return null;
                    }
                }

            }

            case Minus -> {

                switch (finalType) {
                    case IntLiteral -> {
                        return Integer.parseInt(unary) * -1;
                    }

                    case DoubleLiteral -> {
                        return Double.parseDouble(unary) * -1;
                    }

                    default -> {
                        errors.add(new CorruptedOpException(unary, null, op, finalType));
                        return null;
                    }
                }

            }

            case Not -> {

                switch (finalType) {
                    case BooleanLiteral -> {
                        return !Boolean.parseBoolean(unary);
                    }

                    default -> {
                        errors.add(new CorruptedOpException(unary, null, op, finalType));
                        return null;
                    }
                }

            }
        }

        return null;
    }

    private boolean isReducableType(ASTNode node) {
        return node instanceof BinaryOp
               || node instanceof ComparisonOp
               || node instanceof LogicalOp
               || node instanceof UnaryOp;
    }

    private TokenLiteral fetchReducedValue(ASTNode node) {
        ExpressionEphemeral maybeReduced = null;

        if (isReducableType(node)) {
            maybeReduced = (ExpressionEphemeral) node.accept(this);
        } else if (node instanceof TokenLiteral) {
            maybeReduced = (TokenLiteral) node;
        }

        if (!(maybeReduced instanceof TokenLiteral)) {
            return null;
        }

        return (TokenLiteral) maybeReduced;
    }

    @Override
    public Object visitComparisonOp(ComparisonOp comparisonOp) {
        var maybeLeft = fetchReducedValue(comparisonOp.getLeft());
        var maybeRight = fetchReducedValue(comparisonOp.getRight());

        if (Stream.of(maybeLeft, maybeRight).anyMatch(reduced -> reduced == null)) {
            return comparisonOp;
        }

        var leftToken = maybeLeft.getValue();
        var rightToken = maybeRight.getValue();
        var op = comparisonOp.getOperator();

        Boolean booleanValue = null;

        switch (leftToken.type()) {
            case IntLiteral, DoubleLiteral -> {

                switch (rightToken.type()) {
                    case IntLiteral, DoubleLiteral -> {
                        booleanValue = evalComparison(
                            leftToken.lexeme(),
                            rightToken.lexeme(),
                            op.type()
                        );
                        break;
                    }

                    case StringLiteral -> {
                        errors.add(new CorruptedOpException(
                            (String) leftToken.literal(),
                            (String) rightToken.literal(),
                            op.type(),
                            null
                        ));
                        break;
                    }
                }
            }

            case StringLiteral -> {
                errors.add(new CorruptedOpException(
                    leftToken.lexeme(),
                    rightToken.lexeme(),
                    op.type(),
                    null
                ));
                break;
            }

            default -> {
                errors.add(new CorruptedOpException(
                    leftToken.lexeme(),
                    rightToken.lexeme(),
                    op.type(),
                    null
                ));
                break;
            }
        }

        var line = op.line();
        if (booleanValue != null) {
            return new TokenLiteral(new Token(TokenType.BooleanLiteral, booleanValue.toString(), booleanValue, line));
        }

        return comparisonOp;
    }

    @Override
    public Object visitDeclarationsCommaList(DeclarationsCommaList declarationsCommaList) {
        declarationsCommaList.getDeclarations()
            .forEach(identifierWithValue -> identifierWithValue.getValue().accept(this));
        return declarationsCommaList;
    }

    @Override
    public Object visitEmptyTail(EmptyTail emptyTailNode) {
        return emptyTailNode;
    }

    @Override
    public Object visitExpression(Expression expression) {
        expression.setBody(
            (Relation) expression.getBody().accept(this)
        );
        return expression;
    }

    @Override
    public Object visitExpressionStatement(ExpressionStatement expressionStatementNode) {
        expressionStatementNode.setExpression(
            (ExpressionEphemeral) expressionStatementNode.getExpression().accept(this)
        );
        return expressionStatementNode;
    }

    @Override
    public Object visitExpressionsCommaList(ExpressionsCommaList expressionsCommaList) {
        var expressions = expressionsCommaList.getExpressions();

        for (int i = 0; i < expressions.size(); i++) {
            var maybeReduced = expressions.get(i).accept(this);

            if (maybeReduced == null) {
                continue;
            }

            expressions.set(i, (ExpressionEphemeral) maybeReduced);
        }

        return expressionsCommaList;
    }

    @Override
    public Object visitForStatement(ForStatement forStatementNode) {
        forStatementNode.getLoopBody().accept(this);
        return forStatementNode;
    }

    @Override
    public Object visitFunctionCall(FunctionCall funCall) {
        funCall.getExpressions().accept(this);
        return funCall;
    }

    @Override
    public Object visitFunctionLiteral(FunctionLiteral funLiteral) {
        funLiteral.setFunBody(
            (ASTNode) funLiteral.getFunBody().accept(this)
        );
        return funLiteral;
    }

    @Override
    public Object visitIdentifierAssign(IdentifierAssign identifierAssignNode) {
        identifierAssignNode.setExpression(
            (ExpressionEphemeral) identifierAssignNode.getExpression().accept(this)
        );
        return identifierAssignNode;
    }

    @Override
    public Object visitIdentifierWithValue(IdentifierWithValue identifierWithValue) {
        identifierWithValue.setValue(
            (ExpressionEphemeral) identifierWithValue.getValue().accept(this)
        );
        return identifierWithValue;
    }

    @Override
    public Object visitIdentifiersCommaList(IdentifiersCommaList tokenListNode) {
        return tokenListNode;
    }

    @Override
    public Object visitIdentifiersWithValueDeclarationStatement(IdentifiersWithValueDeclarationStatement multipleDeclarationsStatement) {
        multipleDeclarationsStatement.getDeclarations().accept(this);
        return multipleDeclarationsStatement;
    }

    @Override
    public Object visitIfStatement(IfStatement ifStatementNode) {
        ifStatementNode.setExpression(
            (ExpressionEphemeral) ifStatementNode.getExpression().accept(this)
        );

        ifStatementNode.getTrueBody().accept(this);

        if (ifStatementNode.getFalseBody() != null) {
            ifStatementNode.getFalseBody().accept(this);
        }

        return ifStatementNode;
    }

    @Override
    public Object visitLogicalOp(LogicalOp logicalOp) {
        var maybeLeft = fetchReducedValue(logicalOp.getLeft());
        var maybeRight = fetchReducedValue(logicalOp.getRight());

        if (Stream.of(maybeLeft, maybeRight).anyMatch(reduced -> reduced == null)) {
            return logicalOp;
        }

        var leftToken = maybeLeft.getValue();
        var rightToken = maybeRight.getValue();
        var op = logicalOp.getOperator();

        if (!Stream.of(leftToken, rightToken).allMatch(t -> t.type().equals(TokenType.BooleanLiteral))) {
            errors.add(new CorruptedOpException(
                leftToken.lexeme(),
                rightToken.lexeme(),
                op.type(),
                null
            ));

            return logicalOp;
        } else {
            var booleanValue = evalLogical(leftToken.lexeme(), rightToken.lexeme(), op.type());
            var line = op.line();

            return new TokenLiteral(new Token(
                TokenType.BooleanLiteral,
                booleanValue.toString(),
                booleanValue,
                line
            ));
        }
    }

    @Override
    public Object visitLoopBody(LoopBody loopBodyNode) {
        loopBodyNode.getLoopBody().accept(this);
        return loopBodyNode;
    }

    @Override
    public Object visitOnlyIdentifiersDeclarationStatement(OnlyIdentifiersDeclarationStatement node) {
        node.getIdentifiers().accept(this);
        return node;
    }

    @Override
    public Object visitPrintStatement(PrintStatement printStatementNode) {
        printStatementNode.getExpressions().accept(this);
        return printStatementNode;
    }

    @Override
    public Object visitReadStatement(ReadStatement readNode) {
        readNode.setDest(
            (ReferenceTail) readNode.getDest().accept(this)
        );
        return readNode;
    }

    @Override
    public Object visitReferenceAssignStatement(ReferenceAssignStatement referenceAssignStatementNode) {
        referenceAssignStatementNode.getTail().accept(this);
        referenceAssignStatementNode.setExpression(
            (ExpressionEphemeral) referenceAssignStatementNode.getExpression().accept(this)
        );
        return referenceAssignStatementNode;
    }

    @Override
    public Object visitReferenceTail(ReferenceTail referenceTail) {
        referenceTail.getTail().accept(this);
        return referenceTail;
    }

    @Override
    public Object visitReferenceType(ReferenceType referenceTypeNode) {
        referenceTypeNode.getReference().accept(this);
        return referenceTypeNode;
    }

    @Override
    public Object visitReturnStatement(ReturnStatement returnStatementNode) {
        if (returnStatementNode.getExpression() == null) {
            return returnStatementNode;
        }
        returnStatementNode.setExpression(
            (ExpressionEphemeral) returnStatementNode.getExpression().accept(this)
        );
        return returnStatementNode;
    }

    @Override
    public Object visitStatementsList(StatementsList statementsList) {
        statementsList.getStatements().forEach(statement -> statement.accept(this));
        return statementsList;
    }

    @Override
    public Object visitTokenLiteral(TokenLiteral node) {
        return node;
    }

    @Override
    public Object visitTuple(Tuple tuple) {
        tuple.getElements().accept(this);
        return tuple;
    }

    @Override
    public Object visitTupleAccess(TupleAccess tupleAccess) {
        return tupleAccess;
    }

    @Override
    public Object visitTupleList(TupleList tupleListNode) {
        var allExpressions = tupleListNode.getAllExpressions();
        var entrySet = tupleListNode.getNamedExpressions().entrySet();

        for (int i = 0; i < allExpressions.size(); i++) {
            var node = allExpressions.get(i);
            var renewedNode = node.accept(this);

            allExpressions.set(i, (ExpressionEphemeral) renewedNode);

            for (var entry : entrySet) {
                if (entry.getValue().equals(node)) {
                    tupleListNode.getNamedExpressions().put(entry.getKey(), (ExpressionEphemeral) renewedNode);
                }
            }
        }

        return tupleListNode;
    }

    @Override
    public Object visitUnaryOp(UnaryOp unaryOpNode) {
        var maybeOperator = unaryOpNode.getOperator();
        var maybeNode = fetchReducedValue(unaryOpNode.getUnary());

        if (maybeNode == null) {
            return unaryOpNode;
        }

        if (maybeOperator == null) {
            return maybeNode;
        }

        var unaryToken = maybeNode.getValue();
        var operator = maybeOperator.type();
        var line = maybeOperator.line();

        switch (unaryToken.type()) {
            case IntLiteral -> {

                switch (operator) {
                    case Plus, Minus -> {
                        var evaluated = (Integer) evalUnary(unaryToken.lexeme(), operator, TokenType.IntLiteral);

                        return new TokenLiteral(new Token(
                            TokenType.IntLiteral,
                            String.valueOf(evaluated),
                            evaluated,
                            line
                        ));
                    }

                    default -> {
                        errors.add(new CorruptedOpException(unaryToken.lexeme(), null, operator, TokenType.IntLiteral));
                        break;
                    }
                }

            }

            case DoubleLiteral -> {

                switch (operator) {
                    case Plus, Minus -> {
                        var evaluated = (Double) evalUnary(unaryToken.lexeme(), operator, TokenType.DoubleLiteral);

                        return new TokenLiteral(new Token(
                            TokenType.DoubleLiteral,
                            String.valueOf(evaluated),
                            evaluated,
                            line
                        ));
                    }

                    default -> {
                        errors.add(new CorruptedOpException(
                            unaryToken.lexeme(),
                            null,
                            operator,
                            TokenType.DoubleLiteral
                        ));
                        break;
                    }
                }

            }

            case BooleanLiteral -> {

                if (operator.equals(TokenType.Not)) {
                    var evaluated = (Boolean) evalUnary(unaryToken.lexeme(), operator, TokenType.BooleanLiteral);

                    return new TokenLiteral(new Token(
                        TokenType.BooleanLiteral,
                        String.valueOf(evaluated),
                        evaluated,
                        line
                    ));
                }

                errors.add(new CorruptedOpException(
                    unaryToken.lexeme(),
                    null,
                    operator,
                    TokenType.DoubleLiteral
                ));
                break;

            }

            default -> {
                errors.add(new CorruptedOpException(
                    unaryToken.lexeme(),
                    null,
                    operator,
                    TokenType.DoubleLiteral
                ));
                break;
            }
        }

        return unaryOpNode;
    }

    @Override
    public Object visitWhileStatement(WhileStatement whileStatementNode) {
        whileStatementNode.setExpression(
            (ExpressionEphemeral) whileStatementNode.getExpression().accept(this)
        );
        whileStatementNode.getLoopBody().getLoopBody().accept(this);
        return whileStatementNode;
    }
}
