package com.java.evaluation.objects;

import java.util.List;
import com.java.evaluation.Errors;
import org.apache.logging.log4j.util.Strings;

public final class ArrayObj implements Obj {
    private final List<Obj> array;
    public ArrayObj(List<Obj> items) {
        array = items;
    }

    public Obj get(int idx) {
        if (idx < 0 || idx > array.size()) {
            throw Errors.arrayIndexOutOfBounds(idx, array.size());
        }
        return array.get(idx);
    }

    public void set(int idx, Obj val) {
        if (idx < 0 || idx > array.size()) {
            throw Errors.arrayIndexOutOfBounds(idx, array.size());
        }
        array.set(idx, val);
    }

    public List<Obj> getArray() {
        return array;
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
