package com.java.evaluation.objects;

sealed public interface Obj permits ArrayObj, BoolObj, EmptyObj, FunctionObj, IntegerObj, RealObj, ReturnObj, StringObj {
    String toString();
    String type();
}
