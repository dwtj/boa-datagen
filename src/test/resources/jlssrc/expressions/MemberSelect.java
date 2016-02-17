package jlssrc.expressions.invocations;

class MemberSelect {

    Node field = null;
    Node method() {
        return null;
    }

    void expressions() {
        Node n;
        n = field;
        n = method();
        n = this.field;
        n = this.method();
        n = field.link.link;
        n = field.linkMethod();
        n = field.linkMethod().link;
        n = field.linkMethod().linkMethod();
    }
}

class Node {
    Node link;
    Node linkMethod() {
        return null;
    }
}