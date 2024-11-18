package com.java.evaluation;

import com.java.evaluation.objects.Obj;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE) public final class Errors {
    private static final String PREFIX = "Runtime error: ";

    public static RuntimeException undeclaredName(String name) {
        return new RuntimeException(PREFIX + String.format("name '%s' is undeclared", name));
    }

    public static RuntimeException binaryOperationTypeMismatch(String t1, String t2, String op) {
        return new RuntimeException(
            PREFIX + String.format("type mismatch in operation '%s' for types '%s' and '%s'", op, t1, t2));
    }

    public static RuntimeException unaryOperationTypeMismatch(String t1, String op) {
        return new RuntimeException(PREFIX + String.format("type mismatch in operation '%s' for type '%s'", op, t1));
    }

    public static RuntimeException divisionByZero() {
        return new RuntimeException(PREFIX + "division by zero");
    }

    public static RuntimeException unexpectedBinaryOperation(String type, String type1, String op) {
        return new RuntimeException(
            PREFIX + String.format("unexpected binary operation '%s' for types '%s' and '%s'", op, type, type1));
    }

    public static RuntimeException unexpectedUnaryOperation(String type, String op) {
        return new RuntimeException(PREFIX + String.format("unexpected unary operation '%s' for type '%s'", op, type));
    }

    public static RuntimeException notBoolCond(String expr, String type) {
        return new RuntimeException(
            PREFIX + String.format("'Bool' type expression is expected in '%s', but got '%s'", expr, type));
    }

    public static RuntimeException invalidFunctionArgsCount(int size, int expectedSize) {
        return new RuntimeException(
            PREFIX + String.format("in function expected %d arguments, got %d", expectedSize, size));
    }

    public static RuntimeException notCallableObject(String name) {
        return new RuntimeException(
            PREFIX + String.format("object with name '%s' is not function, but was an attempt to call it", name));
    }

    public static RuntimeException indexOutOfBounds(String type, int idx, int size) {
        return new RuntimeException(
            PREFIX + String.format("index %d is out of %s bounds with size %d", idx, type, size));
    }

    public static RuntimeException indexAccessToNotArray(String value) {
        return new RuntimeException(PREFIX + String.format("index access attempt to non-array object '%s'", value));
    }

    public static RuntimeException notIntegerArrayIndex(Obj index) {
        return new RuntimeException(
            PREFIX + String.format("can not perform index access by value of type '%s'", index.type()));
    }

    public static RuntimeException improperIteration(Obj object) {
        return new RuntimeException(PREFIX + String.format("can not iterate over object of type '%s'", object.type()));
    }

    public static RuntimeException noTupleNamedItem(String name) {
        return new RuntimeException(PREFIX + String.format("tuple does not have item with name '%s'", name));
    }

    public static RuntimeException namedAccessToNoTuple(String name, String type) {
        return new RuntimeException(
            PREFIX + String.format("can not perform member access to value '%s' of type '%s'", name, type));
    }

    public static RuntimeException literalAccessError() {
        return new RuntimeException(PREFIX + "attempt to perform tuple member access with invalid type");
    }

    public static RuntimeException redeclarationInScope(String name) {
        return new RuntimeException(
            PREFIX + String.format("the variable '%s' is already declared in this scope", name));
    }

    public static RuntimeException ambiguousTupleNaming(List<String> names) {
        return new RuntimeException(
            PREFIX + String.format("ambiguous naming '%s' during tuple concatenation", String.join(",", names)));
    }
}
