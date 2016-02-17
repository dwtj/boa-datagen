package jlssrc.annotations;

class OneElement {

    @interface A {
        String value();
    }

    @A("foo")
    Object annotated1;

    @A(value = "foo")
    Object annotated2;

    @A(true ? "foo" : "bar")
    Object valueIsConditionalExpression;
}