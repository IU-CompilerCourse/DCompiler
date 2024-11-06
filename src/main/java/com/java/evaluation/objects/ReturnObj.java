package com.java.evaluation.objects;

public record ReturnObj(Obj ret) implements Obj {
    @Override
    public String type() {
        return "ReturnValue";
    }
}
