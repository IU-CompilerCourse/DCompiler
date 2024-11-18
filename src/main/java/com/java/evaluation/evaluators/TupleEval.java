package com.java.evaluation.evaluators;

import com.java.evaluation.Errors;
import com.java.evaluation.objects.Obj;
import com.java.evaluation.objects.TupleObj;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TupleEval {
    public static Obj binaryEval(Obj a, Obj b, String op) {
        if (!(a instanceof TupleObj tuple1 && b instanceof TupleObj tuple2)) {
            throw Errors.binaryOperationTypeMismatch(a.type(), b.type(), op);
        }

        if (op.equals("+")) {
            var duplicates = getNameDuplicates(tuple1, tuple2);
            if (!duplicates.isEmpty()) {
                throw Errors.ambiguousTupleNaming(duplicates);
            }

            var concatenatedTuple = new TupleObj();
            concatenatedTuple.addAll(tuple1.getItems(), tuple2.getItems());

            return concatenatedTuple;
        }
        throw Errors.unexpectedBinaryOperation(a.type(), b.type(), op);
    }

    private static List<String> getNameDuplicates(TupleObj tuple1, TupleObj tuple2) {
        return tuple1.getAllNames().stream()
            .filter(s -> tuple2.getAllNames().contains(s))
            .toList();
    }
}
