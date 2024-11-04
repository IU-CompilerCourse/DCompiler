package com.java.evaluation.objects;

import com.java.evaluation.Errors;
import com.java.evaluation.Evaluator;
import com.java.parser.ast.node.ephemeral.ASTNode;
import com.java.parser.ast.node.real.Expression;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FunctionObj implements Obj {
    private final List<Map<String, Obj>> ctx;
    private final List<String> args;
    private final ASTNode body;
    public FunctionObj(List<Map<String, Obj>> ctx, List<String> args, ASTNode body) {
        this.ctx = ctx;
        this.args = args;
        this.body = body;
    }
    @Override
    public String type() {
        return "Function";
    }

    @Override
    public String toString() {
        var b = new StringBuilder();
        b.append("Function(");
        for (int i = 0; i < args.size(); i++) {
            b.append(args.get(i));
            if (i != args.size() - 1) {
                b.append(", ");
            }
        }
        b.append(")");
        return b.toString();
    }

    public Obj eval(List<Obj> values) {
        if (values.size() != this.args.size()) {
            throw Errors.invalidFunctionArgsCount(values.size(), this.args.size());
        }
        var ctxCopy = copyCtx();
        ctxCopy.add(initArgs(args, values));
        if (body instanceof Expression e) {
            return new ReturnObj(e.accept(new Evaluator(ctxCopy)));
        }
        return body.accept(new Evaluator(ctxCopy));
    }

    private List<Map<String, Obj>> copyCtx() {
        return new ArrayList<>(ctx);
    }

    private Map<String, Obj> initArgs(List<String> args, List<Obj> values) {
        var mp = new HashMap<String, Obj>();
        for (int i = 0; i < args.size(); i++) {
            mp.put(args.get(i), values.get(i));
        }
        return mp;
    }
}
