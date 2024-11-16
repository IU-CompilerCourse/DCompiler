package com.java.evaluation;

import com.java.evaluation.evaluators.ArrayEval;
import com.java.evaluation.evaluators.BoolEval;
import com.java.evaluation.evaluators.NumericEval;
import com.java.evaluation.objects.ArrayObj;
import com.java.evaluation.objects.BoolObj;
import com.java.evaluation.objects.EmptyObj;
import com.java.evaluation.objects.FunctionObj;
import com.java.evaluation.objects.IntegerObj;
import com.java.evaluation.objects.Obj;
import com.java.evaluation.objects.RealObj;
import com.java.evaluation.objects.ReturnObj;
import com.java.evaluation.objects.StringObj;
import com.java.evaluation.objects.TupleObj;
import com.java.lexer.Token;
import com.java.parser.ast.ASTree;
import com.java.parser.ast.node.ephemeral.Tail;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@SuppressWarnings("all")
public class Evaluator implements ASTVisitor<Obj> {
    private final List<Map<String, Obj>> scopes;
    private boolean ReplMode = false;

    public Evaluator(boolean replMode) {
        this.ReplMode = true;
        scopes = new ArrayList<>();
    }

    public Evaluator() {
        this(new ArrayList<>());
    }

    public Evaluator(List<Map<String, Obj>> scopes) {
        this.scopes = scopes;
    }

    @Override
    public Obj visitAST(ASTree ast) {
        var stmts = ast.getNodes().getStatements();
        enterScope();
        Obj res = new EmptyObj();
        for (var stmt : stmts) {
            res = stmt.accept(this);
        }
        if (!ReplMode) {
            leaveScope();
        }
        return res;
    }

    @Override
    public Obj visitAccessTailList(AccessTailList accessTailList) {
        return new EmptyObj();
    }

    @Override
    public Obj visitArrayAccess(ArrayAccess arrayAccessNode) {
        return new EmptyObj();
    }

    @Override
    public Obj visitArray(Array array) {
        var items = new ArrayList<Obj>();
        for (var item : array.getElements().getExpressions()) {
            items.add(item.accept(this));
        }
        return new ArrayObj(items);
    }

    @Override
    public Obj visitBinaryOperation(BinaryOp node) {
        var left = node.getLeft().accept(this);
        var right = node.getRight().accept(this);

        switch (left) {
            case IntegerObj i -> {
                return NumericEval.binaryEval(left, right, node.getOperator().lexeme());
            }
            case RealObj i -> {
                return NumericEval.binaryEval(left, right, node.getOperator().lexeme());
            }
            case ArrayObj i -> {
                return ArrayEval.binaryEval(left, right, node.getOperator().lexeme());
            }
            default -> throw Errors.binaryOperationTypeMismatch(left.type(), right.type(), node.getOperator().lexeme());
        }
    }

    @Override
    public Obj visitComparisonOp(ComparisonOp node) {
        var left = node.getLeft().accept(this);
        var right = node.getRight().accept(this);

        if (left instanceof IntegerObj || left instanceof RealObj) {
            return NumericEval.binaryEval(left, right, node.getOperator().lexeme());
        }
        throw Errors.binaryOperationTypeMismatch(left.type(), right.type(), node.getOperator().lexeme());
    }

    @Override
    public Obj visitDeclarationsCommaList(DeclarationsCommaList declarationsCommaList) {
        for (var decl : declarationsCommaList.getDeclarations()) {
            decl.accept(this);
        }
        return new EmptyObj();
    }

    @Override
    public Obj visitEmptyTail(EmptyTail emptyTailNode) {
        return new EmptyObj();
    }

    @Override
    public Obj visitExpression(Expression expression) {
        return new EmptyObj();
    }

    @Override
    public Obj visitExpressionStatement(ExpressionStatement node) {
        return node.getExpression().accept(this);
    }

    @Override
    public Obj visitExpressionsCommaList(ExpressionsCommaList expressionsCommaList) {
        return new EmptyObj();
    }

    // TODO: очень нищий цикл, мб надо прокачать
    @Override
    public Obj visitForStatement(ForStatement forStmt) {
        var loopVarName = forStmt.getIdentifier().lexeme();
        var iterable = find(forStmt.getTypeIndicator().lexeme());

        if (!(iterable instanceof ArrayObj arr)) {
            throw Errors.improperIteration(iterable);
        }

        enterScope();
        for (var item : arr.getArray()) {
            declare(loopVarName, item);
            var ret = forStmt.getLoopBody().accept(this);
            if (ret instanceof ReturnObj) {
                leaveScope();
                return ret;
            }
        }
        leaveScope();
        return new EmptyObj();
    }

    @Override
    public Obj visitFunctionCall(FunctionCall funCall) {
        return new EmptyObj();
    }

    @Override
    public Obj visitFunctionLiteral(FunctionLiteral funLiteral) {
        var body = funLiteral.getFunBody();
        var args = funLiteral.getParameters().getTokens().stream().map(Token::lexeme).toList();
        return new FunctionObj(scopes, args, body);
    }

    @Override
    public Obj visitIdentifierAssign(IdentifierAssign node) {
        var variable = node.getIdentifier().lexeme();
        var val = node.getExpression().accept(this);
        assign(variable, val);
        return new EmptyObj();
    }

    @Override
    public Obj visitIdentifierWithValue(IdentifierWithValue identifierWithValue) {
        var name = identifierWithValue.getIdentifier().lexeme();
        var value = identifierWithValue.getValue().accept(this);
        declare(name, value);
        return new EmptyObj();
    }

    @Override
    public Obj visitIdentifiersCommaList(IdentifiersCommaList tokenListNode) {
        return new EmptyObj();
    }

    @Override
    public Obj visitIdentifiersWithValueDeclarationStatement(IdentifiersWithValueDeclarationStatement stmt) {
        return stmt.getDeclarations().accept(this);
    }

    @Override
    public Obj visitIfStatement(IfStatement stmt) {
        var cond = stmt.getExpression().accept(this);
        if (!(cond instanceof BoolObj)) {
            throw Errors.notBoolCond("if", cond.type());
        }
        Obj ret = new EmptyObj();
        if (((BoolObj) cond).isValue()) {
            ret = stmt.getTrueBody().accept(this);
        } else if (stmt.getFalseBody() != null) {
            ret = stmt.getFalseBody().accept(this);
        }
        if (ret instanceof ReturnObj) {
            return ret;
        }
        return new EmptyObj();
    }

    @Override
    public Obj visitLogicalOp(LogicalOp logicalOp) {
        var left = logicalOp.getLeft().accept(this);
        var right = logicalOp.getRight().accept(this);
        if (!(left instanceof BoolObj) || !(right instanceof BoolObj)) {
            throw Errors.binaryOperationTypeMismatch(left.type(), right.type(), logicalOp.getOperator().lexeme());
        }
        return BoolEval.binaryEval((BoolObj) left, (BoolObj) right, logicalOp.getOperator().lexeme());
    }

    @Override
    public Obj visitLoopBody(LoopBody loopBodyNode) {
        return loopBodyNode.getLoopBody().accept(this);
    }

    @Override
    public Obj visitOnlyIdentifiersDeclarationStatement(OnlyIdentifiersDeclarationStatement node) {
        for (var decl : node.getIdentifiers().getTokens()) {
            declare(decl.lexeme(), new EmptyObj());
        }
        return new EmptyObj();
    }

    @Override
    public Obj visitPrintStatement(PrintStatement print) {
        var exprs = evaluateExpressions(print);
        printExpressions(exprs);
        return new EmptyObj();
    }

    private List<String> evaluateExpressions(PrintStatement print) {
        var exprs = new ArrayList<String>();
        for (var expr : print.getExpressions().getExpressions()) {
            var val = expr.accept(this);
            exprs.add(val.toString());
        }
        return exprs;
    }

    private void printExpressions(List<String> exprs) {
        for (var expr : exprs) {
            System.out.print(expr + " ");
        }
        System.out.println();
    }

    @Override
    public Obj visitReadStatement(ReadStatement read) {
        var scan = new Scanner(System.in);
        Obj value = readValue(scan, read.getReadType().type());

        var tail = read.getDest();
        var ident = find(tail.getIdentifier().lexeme());

        if (tail.getTail() instanceof EmptyTail) {
            assignValueToIdentifier(tail.getIdentifier().lexeme(), value);
            return new EmptyObj();
        }

        var tails = ((AccessTailList) tail.getTail()).getTails();
        ident = processIntermediateTails(ident, tails);

        processFinalTail(tails.getLast(), ident, value);
        return new EmptyObj();
    }

    private Obj readValue(Scanner scan, Object type) {
        if ("ReadInt".equals(type)) {
            return new IntegerObj(scan.nextInt());
        } else if ("ReadReal".equals(type)) {
            return new RealObj(scan.nextDouble());
        } else if ("ReadString".equals(type)) {
            return new StringObj(scan.nextLine());
        } else {
            throw new RuntimeException("Unexpected read type: " + type);
        }
    }

    private void assignValueToIdentifier(String identifier, Obj value) {
        assign(identifier, value);
    }

    private Obj processIntermediateTails(Obj ident, List<Tail> tails) {
        for (int idx = 0; idx < tails.size() - 1; idx++) {
            var tail = tails.get(idx);
            if (tail instanceof FunctionCall f) {
                ident = processFunctionCall(ident, f);
            } else if (tail instanceof ArrayAccess access) {
                ident = processArrayAccess(ident, access);
            } else if (tail instanceof TupleAccess access) {
                ident = processTupleAccess(ident, access);
            } else {
                throw new IllegalStateException("Unexpected tail type: " + tail);
            }
        }
        return ident;
    }

    private void processFinalTail(Object finalTail, Obj ident, Obj value) {
        if (finalTail instanceof TupleAccess access) {
            processFinalTupleAccess(ident, access, value);
        } else if (finalTail instanceof ArrayAccess access) {
            processFinalArrayAccess(ident, access, value);
        } else {
            throw new IllegalStateException("Unexpected tail type: " + finalTail);
        }
    }

    private Obj processFunctionCall(Obj ident, FunctionCall call) {
        if (!(ident instanceof FunctionObj func)) {
            throw Errors.notCallableObject(ident.toString());
        }
        List<Obj> evaluatedArgs = new LinkedList<>();
        for (var expr : call.getExpressions().getExpressions()) {
            evaluatedArgs.add(expr.accept(this));
        }
        ident = func.eval(evaluatedArgs);
        if (ident instanceof ReturnObj ro) {
            return ro.ret();
        }
        return ident;
    }

    private Obj processArrayAccess(Obj ident, ArrayAccess access) {
        if (!(ident instanceof ArrayObj arr)) {
            throw Errors.indexAccessToNotArray(ident.toString());
        }
        var index = access.getExpression().accept(this);
        if (!(index instanceof IntegerObj intIdx)) {
            throw Errors.notIntegerArrayIndex(index);
        }
        return arr.get(intIdx.getValue());
    }

    private Obj processTupleAccess(Obj ident, TupleAccess access) {
        if (!(ident instanceof TupleObj tuple)) {
            throw Errors.namedAccessToNoTuple(ident.toString(), ident.type());
        }
        if (access.getIdentifier() != null) {
            return tuple.getByName(access.getIdentifier().lexeme());
        } else {
            var ind = access.getLiteral().literal();
            if (!(ind instanceof Integer i)) {
                throw Errors.literalAccessError();
            }
            return tuple.getByInd(i);
        }
    }

    private void processFinalTupleAccess(Obj ident, TupleAccess access, Obj value) {
        if (!(ident instanceof TupleObj tuple)) {
            throw Errors.namedAccessToNoTuple(ident.toString(), ident.type());
        }
        if (access.getIdentifier() != null) {
            tuple.setByName(access.getIdentifier().lexeme(), value);
        } else {
            var ind = access.getLiteral().literal();
            if (!(ind instanceof Integer i)) {
                throw Errors.literalAccessError();
            }
            tuple.setByInd(i, value);
        }
    }

    private void processFinalArrayAccess(Obj ident, ArrayAccess access, Obj value) {
        if (!(ident instanceof ArrayObj arr)) {
            throw Errors.indexAccessToNotArray(ident.toString());
        }
        var index = access.getExpression().accept(this);
        if (!(index instanceof IntegerObj intIdx)) {
            throw Errors.notIntegerArrayIndex(index);
        }
        arr.set(intIdx.getValue(), value);
    }

    @Override
    public Obj visitReferenceAssignStatement(ReferenceAssignStatement node) {
        var value = node.getExpression().accept(this);
        var tails = node.getTail().getTails();
        var ident = find(node.getIdentifier().lexeme());

        if (tails.isEmpty()) {
            throw new RuntimeException("Empty tail case is unexpected here; this case has a separate handler.");
        }

        for (int idx = 0; idx < tails.size() - 1; idx++) {
            ident = processIntermediateTail(ident, tails.get(idx));
        }

        processFinalTail(tails.get(tails.size() - 1), ident, value);
        return new EmptyObj();
    }

    private Obj processIntermediateTail(Obj ident, Object tail) {
        if (tail instanceof FunctionCall f) {
            return handleFunctionCall(ident, f);
        } else if (tail instanceof ArrayAccess access) {
            return handleArrayAccess(ident, access);
        } else if (tail instanceof TupleAccess access) {
            return handleTupleAccess(ident, access);
        } else {
            throw new IllegalStateException("Unexpected tail type: " + tail);
        }
    }


    private Obj handleFunctionCall(Obj ident, FunctionCall call) {
        if (!(ident instanceof FunctionObj func)) {
            throw Errors.notCallableObject(ident.toString());
        }
        List<Obj> evaluatedArgs = new ArrayList<>();
        for (var expr : call.getExpressions().getExpressions()) {
            evaluatedArgs.add(expr.accept(this));
        }
        ident = func.eval(evaluatedArgs);
        return ident instanceof ReturnObj ro ? ro.ret() : ident;
    }

    private Obj handleArrayAccess(Obj ident, ArrayAccess access) {
        if (!(ident instanceof ArrayObj arr)) {
            throw Errors.indexAccessToNotArray(ident.toString());
        }
        var index = access.getExpression().accept(this);
        if (!(index instanceof IntegerObj intIdx)) {
            throw Errors.notIntegerArrayIndex(index);
        }
        return arr.get(intIdx.getValue());
    }

    private Obj handleTupleAccess(Obj ident, TupleAccess access) {
        if (!(ident instanceof TupleObj tuple)) {
            throw Errors.namedAccessToNoTuple(ident.toString(), ident.type());
        }
        if (access.getIdentifier() != null) {
            return tuple.getByName(access.getIdentifier().lexeme());
        } else {
            var ind = access.getLiteral().literal();
            if (!(ind instanceof Integer i)) {
                throw Errors.literalAccessError();
            }
            return tuple.getByInd(i);
        }
    }

    private void handleFinalTupleAccess(Obj ident, TupleAccess access, Obj value) {
        if (!(ident instanceof TupleObj tuple)) {
            throw Errors.namedAccessToNoTuple(ident.toString(), ident.type());
        }
        if (access.getIdentifier() != null) {
            tuple.setByName(access.getIdentifier().lexeme(), value);
        } else {
            var ind = access.getLiteral().literal();
            if (!(ind instanceof Integer i)) {
                throw Errors.literalAccessError();
            }
            tuple.setByInd(i, value);
        }
    }

    private void handleFinalArrayAccess(Obj ident, ArrayAccess access, Obj value) {
        if (!(ident instanceof ArrayObj arr)) {
            throw Errors.indexAccessToNotArray(ident.toString());
        }
        var index = access.getExpression().accept(this);
        if (!(index instanceof IntegerObj intIdx)) {
            throw Errors.notIntegerArrayIndex(index);
        }
        arr.set(intIdx.getValue(), value);
    }


    @Override
    public Obj visitReferenceTail(ReferenceTail ref) {
        var ident = find(ref.getIdentifier().lexeme());
        if (ref.getTail() == null || ref.getTail() instanceof EmptyTail) {
            return ident;
        }
        var tail = (AccessTailList) ref.getTail();
        for (var t : tail.getTails()) {
            switch (t) {
                case FunctionCall f -> {
                    if (!(ident instanceof FunctionObj func)) {
                        throw Errors.notCallableObject(ident.toString());
                    }
                    List<Obj> evaluatedArgs = new LinkedList<>();
                    for (var expr : f.getExpressions().getExpressions()) {
                        evaluatedArgs.add(expr.accept(this));
                    }
                    ident = func.eval(evaluatedArgs);
                    if (ident instanceof ReturnObj ro) {
                        ident = ro.ret();
                    }
                }
                case ArrayAccess access -> {
                    if (!(ident instanceof ArrayObj arr)) {
                        throw Errors.indexAccessToNotArray(ident.toString());
                    }
                    var index = access.getExpression().accept(this);
                    if (!(index instanceof IntegerObj intIdx)) {
                        throw Errors.notIntegerArrayIndex(index);
                    }
                    ident = arr.get(intIdx.getValue());
                }
                case TupleAccess access -> {
                    if (!(ident instanceof TupleObj tuple)) {
                        throw Errors.namedAccessToNoTuple(ident.toString(), ident.type());
                    }
                    if (access.getIdentifier() != null) {
                        ident = tuple.getByName(access.getIdentifier().lexeme());
                    } else {
                        var idx = access.getLiteral().literal();
                        if (!(idx instanceof Integer i)) {
                            throw Errors.literalAccessError();
                        }
                        ident = tuple.getByInd(i);
                    }
                }
                default -> throw new IllegalStateException("Unexpected value: " + t);
            }
        }
        return ident;
    }

    @Override
    public Obj visitReferenceType(ReferenceType node) {
        var item = node.getReference().accept(this);
        switch (node.getType().type()) {
            case Int -> {
                return new BoolObj(item instanceof IntegerObj);
            }
            case Real -> {
                return new BoolObj(item instanceof RealObj);
            }
            case Bool -> {
                return new BoolObj(item instanceof BoolObj);
            }
            case String -> {
                return new BoolObj(item instanceof StringObj);
            }
            case Func -> {
                return new BoolObj(item instanceof FunctionObj);
            }
            case Empty -> {
                return new BoolObj(item instanceof EmptyObj);
            }
            // TODO: ну тут не хватает массива и тупла
            default -> throw new RuntimeException("NOT IMPLEMENTED TYPE MATCH");
        }
    }

    @Override
    public Obj visitReturnStatement(ReturnStatement node) {
        return new ReturnObj(node.getExpression().accept(this));
    }

    @Override
    public Obj visitStatementsList(StatementsList statementsList) {
        enterScope();
        for (var stmt : statementsList.getStatements()) {
            var ret = stmt.accept(this);
            if (ret instanceof ReturnObj) {
                return ret;
            }
        }
        leaveScope();
        return new EmptyObj();
    }

    @Override
    public Obj visitTokenLiteral(TokenLiteral node) {
        var token = node.getValue();
        switch (token.type()) {
            case BooleanLiteral -> {
                return new BoolObj((Boolean) token.literal());
            }
            case IntLiteral -> {
                return new IntegerObj((Integer) token.literal());
            }
            case StringLiteral -> {
                return new StringObj((String) token.literal());
            }
            case DoubleLiteral -> {
                return new RealObj((Double) token.literal());
            }
            default -> {
                return new EmptyObj();
            }
        }
    }

    @Override
    public Obj visitTuple(Tuple tuple) {
        return new TupleObj(tuple, this);
    }

    @Override
    public Obj visitTupleAccess(TupleAccess tupleAccess) {
        return new EmptyObj();
    }

    @Override
    public Obj visitTupleList(TupleList tupleListNode) {
        return new EmptyObj();
    }

    @Override
    public Obj visitUnaryOp(UnaryOp node) {
        var expr = node.getUnary().accept(this);
        if (node.getOperator() == null) {
            return expr;
        }
        if (expr instanceof IntegerObj || expr instanceof RealObj) {
            return NumericEval.unaryEval(expr, node.getOperator().lexeme());
        } else if (expr instanceof BoolObj) {
            return BoolEval.unaryEval((BoolObj) expr, node.getOperator().lexeme());
        }
        return new EmptyObj();
    }

    @Override
    public Obj visitWhileStatement(WhileStatement stmt) {
        while (true) {
            var cond = stmt.getExpression().accept(this);
            if (!(cond instanceof BoolObj)) {
                throw Errors.notBoolCond("while", cond.type());
            }
            if (!((BoolObj) cond).isValue()) {
                break;
            }
            var ret = stmt.getLoopBody().accept(this);
            if (ret instanceof ReturnObj) {
                return ret;
            }
        }
        return new EmptyObj();
    }

    private void enterScope() {
        scopes.add(new HashMap<>());
    }

    private void leaveScope() {
        scopes.removeLast();
    }

    private void declare(String name, Obj object) {
        if (scopes.getLast().containsKey(name)) {
            throw Errors.redeclarationInScope(name);
        }
        scopes.getLast().put(name, object);
    }

    private void assign(String name, Obj val) {
        for (var scope : scopes.reversed()) {
            if (scope.containsKey(name)) {
                scope.put(name, val);
                return;
            }
        }
        throw Errors.undeclaredName(name);
    }

    private Obj find(String name) {
        for (var scope : scopes.reversed()) {
            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }
        throw Errors.undeclaredName(name);
    }
}
