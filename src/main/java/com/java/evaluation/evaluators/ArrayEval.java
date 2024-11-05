package com.java.evaluation.evaluators;

import com.java.evaluation.Errors;
import com.java.evaluation.objects.ArrayObj;
import com.java.evaluation.objects.Obj;
import java.util.ArrayList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArrayEval {
    public static Obj binaryEval(Obj a, Obj b, String op) {
        if (!(a instanceof ArrayObj arr1)) {
            throw Errors.binaryOperationTypeMismatch(a.type(), b.type(), op);
        }
        if (op.equals("+")) {
            var arr = new ArrayList<>(arr1.getArray());
            if (b instanceof ArrayObj arr2) {
                arr.addAll(arr2.getArray());
            } else {
                arr.add(b);
            }
            return new ArrayObj(arr);
        }
        throw Errors.unexpectedBinaryOperation(a.type(), b.type(), op);
    }
}
