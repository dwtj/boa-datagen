package jlssrc.types;

import java.util.List;

class Wildcards<T> {
    List<?> unboundedWildcard;
    List<? extends T> upperBoundedWildcard;
    List<? super T> lowerBoundedWildcard;
}