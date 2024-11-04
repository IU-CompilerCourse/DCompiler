package com.java.evaluation.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class IntegerObj implements Obj {
    private Integer value;
    public IntegerObj(Integer value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public String type() {
        return "Integer";
    }
}
