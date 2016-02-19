package jlssrc.types;

import java.util.List;

class Bounds {
    class A<T extends List> { }
    class B<T extends Iterable & List> { }
}