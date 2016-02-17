package expressions.invocations;

class Arguments {

    void nil() { }

    String identity(String str) {
        return str;
    }

    void expressions() {
        nil();
        identity("foo");
    }

    // TODO: More?
}