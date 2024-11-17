package com.java.evaluation.objects;

import com.java.evaluation.Errors;
import java.util.List;
import lombok.Getter;

@Getter
public final class ArrayObj implements Obj {
    private final List<Obj> array;

    public ArrayObj(List<Obj> items) {
        array = items;
    }

    public Obj get(int idx) {
        if (idx < 1 || idx > array.size()) {
            throw Errors.indexOutOfBounds(type(), idx, array.size());
        }
        return array.get(idx - 1);
    }

    public void set(int idx, Obj val) {
        if (idx < 1) {
            throw Errors.indexOutOfBounds(type(), idx, array.size());
        }

        if (idx > array.size()) {
            for (int i = array.size(); i < idx; i++) {
                array.add(new EmptyObj());
            }
        }

        array.set(idx - 1, val);
    }

    @Override
    public String type() {
        return "Array";
    }

    @Override
    public String toString() {
        var b = new StringBuilder();
        b.append("[");
        for (int i = 0; i < array.size(); i++) {
            b.append(array.get(i).toString());
            if (i != array.size() - 1) {
                b.append(", ");
            }
        }
        b.append("]");
        return b.toString();
    }
}
