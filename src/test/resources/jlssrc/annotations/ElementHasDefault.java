package jlssrc.annotations;

class ElementHasDefault {
    @interface A {
        String value() default "[default-value]";
    }

    @A
    Object markerAnnotationUsingDefault;

    @A()
    Object usingDefault;

    @A("[custom-value]")
    Object notUsingDefault;

    @A(value = "[custom-value]")
    Object notUsingDefaultWithPair;
}