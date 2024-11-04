package com.java.evaluation.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class BoolObj implements Obj {
    private boolean value;
    public BoolObj(boolean v) {
        this.value = v;
    }

    @Override
    public String toString() {
        if (value) {
            return "true";
        }
        return "false";
    }

    @Override
    public String type() {
        return "Bool";
    }
}
