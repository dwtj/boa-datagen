package jlssrc.types;

class Bounds {
    class A<T extends List> { }
    class B<T extends Iterable & List> { }
}