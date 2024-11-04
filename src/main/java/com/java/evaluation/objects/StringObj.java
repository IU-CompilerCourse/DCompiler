package com.java.evaluation.objects;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public final class StringObj implements Obj {
    private String value;
    public StringObj(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public String type() {
        return "String";
    }
}
