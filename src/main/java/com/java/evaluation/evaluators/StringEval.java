package com.java.evaluation.evaluators;

import com.java.evaluation.Errors;
import com.java.evaluation.objects.Obj;
import com.java.evaluation.objects.StringObj;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringEval {
    public static Obj binaryEval(Obj a, Obj b, String op) {
        if (!(a instanceof StringObj str1 && b instanceof StringObj str2)) {
            throw Errors.binaryOperationTypeMismatch(a.type(), b.type(), op);
        }

        if (op.equals("+")) {
            return new StringObj(str1.getValue() + str2.getValue());
        }

        throw Errors.unexpectedBinaryOperation(a.type(), b.type(), op);
    }
}
