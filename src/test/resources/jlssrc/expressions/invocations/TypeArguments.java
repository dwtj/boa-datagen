package jlssrc.expressions.invocations;

class TypeArguments {
    <T> T identity(T t) {
        return t;
    }

    void expressions() {
        this.identity("foo");
        this.<String>identity("foo");
    }

    // TODO: Add more (e.g. bounds).
}
