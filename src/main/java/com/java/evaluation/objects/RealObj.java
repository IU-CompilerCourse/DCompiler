package com.java.evaluation.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class RealObj implements Obj {
    private Double value;

    public RealObj(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public String type() {
        return "Real";
    }
}
