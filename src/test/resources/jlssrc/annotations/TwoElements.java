package jlssrc.annotations;

class TwoElements {

    @interface A {
        String first();
        String second();
    }

    @A(first="foo", second="bar")
    Object annotated1;

    @A(second="bar", first="foo")
    Object annotated2;
}