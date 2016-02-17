package jlssrc.expressions.invocations;

class SuperKeyword extends S {

    static void staticA() { }
    void instanceA() { }

    class T extends S {

        void instanceT() { }

        // These invocations are all on static methods.
        void invokeStaticMethods() {

            // Three equivalent invocations:
            staticA();
            SuperKeyword.staticA();

            // Four equivalent invocations:
            staticS();
            S.staticS();
            super.staticS();
            SuperKeyword.super.staticS();  // TODO: Fails to compile. Do we need another example?
        }

        // These invocations use the <em>inner</em> class's instance as the reciever parameter.
        void invokeInstanceMethodsOnInner() {

            // Two equivalent invocations:
            instanceT();
            this.instanceT();

            // Two equivalent invocations:
            instanceS();
            super.instanceS();
        }

        // These invocations use the <em>outer</em> class's instance as the reciever parameter.
        void invokeInstanceMethodsOnOuter() {
            instanceA();
            SuperKeyword.super.instanceS();  // TODO: Fails to compile.
        }
    }
}

// A supertype for both the root class and the inner class.
class S {
    static void staticS() { }
    void instanceS() { }
}
