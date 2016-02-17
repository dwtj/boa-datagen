package jlssrc.annotations;

class ArrayElement {
    @interface A {
        String[] value();
    }

    @A({})
    Object valueHasLengthZero;

    @A({"foo", "bar"})
    Object valueIsInitializerWithLengthTwo;

    // The following two examples are invalid Java:
    // @A(null) Object valueIsNull;
    // @A(new String[2]) Object valueIsCreatedWithLengthTwo;
}