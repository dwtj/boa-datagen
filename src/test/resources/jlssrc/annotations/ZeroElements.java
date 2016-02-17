package jlssrc.annotations;

class ZeroElements {

    @interface A {}

    @A
    Object annotated1;

    @A()
    Object annotated2;
}