package com.java.evaluation;

import com.java.evaluation.objects.Obj;

public final class Errors {
    private static final String prefix = "Runtime error: ";
    public static RuntimeException undeclaredName(String name) {
        return new RuntimeException(prefix + String.format("name '%s' is undeclared", name));
    }

    public static RuntimeException binaryOperationTypeMismatch(String t1, String t2, String op) {
        return new RuntimeException(prefix + String.format("type mismatch in operation '%s' for types '%s' and '%s'", op, t1, t2));
    }

    public static RuntimeException unaryOperationTypeMismatch(String t1, String op) {
        return new RuntimeException(prefix + String.format("type mismatch in operation '%s' for type '%s'", op, t1));
    }

    public static RuntimeException divisionByZero() {
        return new RuntimeException(prefix + "division by zero");
    }

    public static RuntimeException unexpectedBinaryOperation(String type, String type1, String op) {
        return new RuntimeException(prefix +
            String.format("unexpected binary operation '%s' for types '%s' and '%s'", op, type, type1));
    }

    public static RuntimeException unexpectedUnaryOperation(String type, String op) {
        return new RuntimeException(prefix + String.format("unexpected unary operation '%s' for type '%s'", op, type));
    }

    public static RuntimeException notBoolCond(String expr, String type) {
        return new RuntimeException(prefix + String.format("'Bool' type expression is expected in '%s', but got '%s'", expr, type));
    }

    public static RuntimeException invalidFunctionArgsCount(int size, int expectedSize) {
        return new RuntimeException(prefix + String.format("in function expected %d arguments, got %d", expectedSize, size));
    }

    public static RuntimeException notCallableObject(String name) {
        return new RuntimeException(prefix + String.format("object with name '%s' is not function, but was an attempt to call it", name));
    }

    public static RuntimeException arrayIndexOutOfBounds(int idx, int size) {
        return new RuntimeException(prefix + String.format("index %d is out of bounds of array with size %d", idx, size));
    }

    public static RuntimeException indexAccessToNotArray(String value) {
        return new RuntimeException(prefix + String.format("index access attempt to non-array object '%s'", value));
    }

    public static RuntimeException notIntegerArrayIndex(Obj index) {
        return new RuntimeException(prefix + String.format("can not perform index access by value of type '%s'", index.type()));
    }

    public static RuntimeException improperIteration(Obj object) {
        return new RuntimeException(prefix + String.format("can not iterate over object of type '%s'", object.type()));
    }
}
