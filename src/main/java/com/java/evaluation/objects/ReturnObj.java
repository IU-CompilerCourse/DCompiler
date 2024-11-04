package com.java.evaluation.objects;

import lombok.Getter;

public record ReturnObj(Obj ret) implements Obj {
    @Override
    public String type() {
        return "ReturnValue";
    }
}
