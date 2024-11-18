package com.java.evaluation.evaluators;

import com.java.evaluation.Errors;
import com.java.evaluation.objects.BoolObj;
import com.java.evaluation.objects.Obj;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoolEval {
    public static Obj binaryEval(BoolObj left, BoolObj right, String op) {
        switch (op) {
            case "and" -> {
                return new BoolObj(left.isValue() && right.isValue());
            }
            case "or" -> {
                return new BoolObj(left.isValue() || right.isValue());
            }
            case "xor" -> {
                return new BoolObj(left.isValue() ^ right.isValue());
            }
            default -> {
                throw Errors.unexpectedBinaryOperation(left.type(), right.type(), op);
            }
        }
    }

    public static Obj unaryEval(BoolObj expr, String op) {
        if (op.equals("not")) {
            return new BoolObj(!expr.isValue());
        }
        throw Errors.unexpectedUnaryOperation(expr.type(), op);
    }
}
