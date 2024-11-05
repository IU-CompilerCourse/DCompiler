package com.java.evaluation.evaluators;

import com.java.evaluation.Errors;
import com.java.evaluation.objects.BoolObj;
import com.java.evaluation.objects.IntegerObj;
import com.java.evaluation.objects.Obj;
import com.java.evaluation.objects.RealObj;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@SuppressWarnings("checkstyle:ReturnCount")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NumericEval {
    public static Obj binaryEval(Obj a, Obj b, String op) {
        switch (a) {
            case IntegerObj i1 when b instanceof IntegerObj i2 -> {
                return evalIntInt(i1, i2, op);
            }
            case IntegerObj i1 when b instanceof RealObj f2 -> {
                return evalIntReal(i1, f2, op);
            }
            case RealObj f1 when b instanceof RealObj f2 -> {
                return evalRealReal(f1, f2, op);
            }
            case RealObj f1 when b instanceof IntegerObj i2 -> {
                return evalRealInt(f1, i2, op);
            }
            default -> {
                throw Errors.binaryOperationTypeMismatch(a.type(), b.type(), op);
            }
        }
    }

    private static Obj evalRealInt(RealObj a, IntegerObj b, String op) {
        switch (op) {
            case "+" -> {
                return new RealObj(a.getValue() + b.getValue());
            }
            case "-" -> {
                return new RealObj(a.getValue() - b.getValue());
            }
            case "/" -> {
                if (b.getValue() == 0) {
                    throw Errors.divisionByZero();
                }
                return new RealObj(a.getValue() / b.getValue());
            }
            case "*" -> {
                return new RealObj(a.getValue() * b.getValue());
            }

            case "<" -> {
                return new BoolObj(a.getValue() < b.getValue());
            }
            case "<=" -> {
                return new BoolObj(a.getValue() <= b.getValue());
            }
            case "=" -> {
                return new BoolObj(a.getValue() == b.getValue().doubleValue());
            }
            case "/=" -> {
                return new BoolObj(a.getValue() != b.getValue().doubleValue());
            }
            case ">=" -> {
                return new BoolObj(a.getValue() >= b.getValue());
            }
            case ">" -> {
                return new BoolObj(a.getValue() > b.getValue());
            }
            default -> {
                throw Errors.unexpectedBinaryOperation(a.type(), b.type(), op);
            }
        }
    }

    private static Obj evalRealReal(RealObj a, RealObj b, String op) {
        switch (op) {
            case "+" -> {
                return new RealObj(a.getValue() + b.getValue());
            }
            case "-" -> {
                return new RealObj(a.getValue() - b.getValue());
            }
            case "/" -> {
                if (b.getValue() == 0) {
                    throw Errors.divisionByZero();
                }
                return new RealObj(a.getValue() / b.getValue());
            }
            case "*" -> {
                return new RealObj(a.getValue() * b.getValue());
            }

            case "<" -> {
                return new BoolObj(a.getValue() < b.getValue());
            }
            case "<=" -> {
                return new BoolObj(a.getValue() <= b.getValue());
            }
            case "=" -> {
                return new BoolObj(a.getValue() == b.getValue().doubleValue());
            }
            case "/=" -> {
                return new BoolObj(!Objects.equals(a.getValue(), b.getValue()));
            }
            case ">=" -> {
                return new BoolObj(a.getValue() >= b.getValue());
            }
            case ">" -> {
                return new BoolObj(a.getValue() > b.getValue());
            }
            default -> {
                throw Errors.unexpectedBinaryOperation(a.type(), b.type(), op);
            }
        }
    }

    private static Obj evalIntReal(IntegerObj a, RealObj b, String op) {
        switch (op) {
            case "+" -> {
                return new RealObj(a.getValue() + b.getValue());
            }
            case "-" -> {
                return new RealObj(a.getValue() - b.getValue());
            }
            case "/" -> {
                if (b.getValue() == 0) {
                    throw Errors.divisionByZero();
                }
                return new RealObj(a.getValue() / b.getValue());
            }
            case "*" -> {
                return new RealObj(a.getValue() * b.getValue());
            }

            case "<" -> {
                return new BoolObj(a.getValue() < b.getValue());
            }
            case "<=" -> {
                return new BoolObj(a.getValue() <= b.getValue());
            }
            case "=" -> {
                return new BoolObj(a.getValue().doubleValue() == b.getValue());
            }
            case "/=" -> {
                return new BoolObj(a.getValue() != b.getValue().doubleValue());
            }
            case ">=" -> {
                return new BoolObj(a.getValue() >= b.getValue());
            }
            case ">" -> {
                return new BoolObj(a.getValue() > b.getValue());
            }
            default -> {
                throw Errors.unexpectedBinaryOperation(a.type(), b.type(), op);
            }
        }
    }

    private static Obj evalIntInt(IntegerObj a, IntegerObj b, String op) {
        switch (op) {
            case "+" -> {
                return new IntegerObj(a.getValue() + b.getValue());
            }
            case "-" -> {
                return new IntegerObj(a.getValue() - b.getValue());
            }
            case "/" -> {
                if (b.getValue() == 0) {
                    throw Errors.divisionByZero();
                }
                return new IntegerObj(a.getValue() / b.getValue());
            }
            case "*" -> {
                return new IntegerObj(a.getValue() * b.getValue());
            }
            case "<" -> {
                return new BoolObj(a.getValue() < b.getValue());
            }
            case "<=" -> {
                return new BoolObj(a.getValue() <= b.getValue());
            }
            case "=" -> {
                return new BoolObj(Objects.equals(a.getValue(), b.getValue()));
            }
            case "/=" -> {
                return new BoolObj(a.getValue() != b.getValue().doubleValue());
            }
            case ">=" -> {
                return new BoolObj(a.getValue() >= b.getValue());
            }
            case ">" -> {
                return new BoolObj(a.getValue() > b.getValue());
            }
            default -> {
                throw Errors.unexpectedBinaryOperation(a.type(), b.type(), op);
            }
        }
    }

    public static Obj unaryEval(Obj expr, String op) {
        switch (op) {
            case "+" -> {
                return expr;
            }
            case "-" -> {
                switch (expr) {
                    case RealObj r -> {
                        return new RealObj(r.getValue() * (-1));
                    }
                    case IntegerObj i -> {
                        return new IntegerObj(i.getValue() * (-1));
                    }
                    default -> throw Errors.unaryOperationTypeMismatch(expr.type(), op);
                }
            }
            default -> throw Errors.unexpectedUnaryOperation(expr.type(), op);
        }
    }
}
