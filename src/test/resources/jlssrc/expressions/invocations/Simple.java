package jlssrc.expressions.invocations;

class Simple {
    static void staticMethod() { }
    void instanceMethod() { }

    void expressions() {
        staticMethod();
        instanceMethod();
    }
}
