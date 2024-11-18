package com.java.evaluation.objects;

public final class EmptyObj implements Obj {
    private static final String EMPTY = "empty";

    @Override
    public String toString() {
        return EMPTY;
    }

    @Override
    public String type() {
        return EMPTY;
    }
}
